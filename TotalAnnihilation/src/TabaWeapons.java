import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;


public class TabaWeapons {
	
	public static final String[] weaponClasses = {"Very Light", "Light", "Medium", "Heavy", "Very Heavy", "Extremely Heavy", "Ultra Heavy", "Krogoth Unit"};
	public static final String[] weaponSClasses = {"VL", "L", "M", "H", "VH", "EH", "UH", "K"};
	public static final int[] weaponCutoffs = {50, 100, 200, 400, 800, 1600, 3200, 1000000};
	public static final String[] weaponRanges = {"Close-Ranged", "Mid-Ranged", "Long-Ranged"};
	public static final int[] rangeCutoffs = {500, 850, 32000};
	public static final int minRange = 250;
	private static final double unitToWeaponV = 45/1.6; //conversion factor
	
	private static final File weaponsFile = new File(FileEditer.TABA_DIRECTORY + "Weapons/TabaWeapons.tdf");
	private HashMap<String, Weapon> weapons = new HashMap<String, Weapon>();
	private List<Weapon> orderedWeapons = new ArrayList<Weapon>();
	private List<String> oldWeaponLines = new ArrayList<String>();
	private SpecialDamages specialDamages = new SpecialDamages();
	private int nUpdated = 0;
	public SpecialDamages spD;
	public UnitFiles unitFiles;
	
	//Really have the need for an Indexed Binary Tree here. I would like to know the number of elements less
	//than a certain number. Unfortunately, Java Libraries don't have such a structure in log(n) time. Maybe
	//I'll implement one later...
	//Actually since I'm not adding or removing, I can just sort the collection!
	private List<Double> velocities;
	
	public TabaWeapons() throws FileNotFoundException
	{
		spD = new SpecialDamages(new UnitClasses(new UnitFiles(this)));	//unitfiles needs a reference to tabaweapons for updates
		unitFiles = spD.classes.files;
		getAllWeapons();
	}
	
//	public TabaWeapons(SpecialDamages existing) throws FileNotFoundException
//	{
//		spD = existing;
//		spD.classes.files = new UnitFiles(this);
//		unitFiles = spD.classes.files;
//		getAllWeapons();
//	}
	
	private void getAllWeapons() throws FileNotFoundException
	{
		oldWeaponLines = FileEditer.getLines(weaponsFile);
		String name = "";
		boolean on = false, specialDamages = false;
		List<String> weaponLines = new ArrayList<String>();
		List<String> specialDamageLines = new ArrayList<String>();
		int bracketCount = 0;
		for (int i = 0; i < oldWeaponLines.size(); ++i)
		{
			String line1 = oldWeaponLines.get(i);
			String line2 = line1.trim().toLowerCase();
			
			//Integrity check
			if (line2.startsWith("{"))
			{
				++bracketCount;
			}
			if (line2.startsWith("}"))
			{
				--bracketCount;
			}
			if (bracketCount > 2 || bracketCount < 0)
			{
				throw new RuntimeException("BracketCount = " + bracketCount + " at " + (i + 1));
			}
			
			if (line2.startsWith("[") && !line2.contains("[damage]"))
			{
				name = FileParsing.parse("\\[(.*)\\]", line1);
				on = true;
			}
			else if (line2.startsWith("}"))
			{
				if (specialDamages)
				{
					specialDamages = false;
				}
				else
				{
					if (name.isEmpty())
					{
						System.out.println("empty");
					}
					weaponLines.add(line1);
					on = false;
					Weapon cur = new Weapon(name, weaponLines, specialDamageLines);
					orderedWeapons.add(cur);
					weapons.put(name.toLowerCase(), cur);
					name = "";
					weaponLines = new ArrayList<String>();
					specialDamageLines = new ArrayList<String>();
				}
			}
			
			if (specialDamages)
			{
				specialDamageLines.add(line1);
			}
			else if (on)
			{
				weaponLines.add(line1);
			}
			
			if (line2.startsWith("default"))
			{
				specialDamages = true;
			}
		}
	}
	
	public void updateAllWeapons() throws FileNotFoundException
	{
		for (int i = 0; i < orderedWeapons.size(); ++i)
		{
			orderedWeapons.get(i).updateSpecialDamages();
		}
		updateWeaponsFile();
	}
	
	public Weapon getWeapon(String weaponName)
	{
		return weapons.get(weaponName.toLowerCase());
	}
	
	public void updateWeaponsFile() throws FileNotFoundException
	{
		List<String> newTotalLines = new ArrayList<String>();
		for (int i = 0; i < orderedWeapons.size(); ++i)
		{
			newTotalLines.addAll(orderedWeapons.get(i).getFullLines());
			newTotalLines.add("");
		}
		FileEditer.updateFile(oldWeaponLines, newTotalLines, weaponsFile);
	}
	
	public List<Double> getSortedVelocities()
	{
		List<Double> velocities = new ArrayList<Double>();
	    for (int i = 0; i < spD.classes.files.movingNotPlane.size(); ++i)
	    {
	    	velocities.add(spD.classes.files.movingNotPlane.get(i).maxVelocity);
	    }
	    Collections.sort(velocities);
	    return velocities;
	}
	
	public double hitPercentage(int X, double secTillHit) throws FileNotFoundException
	{
		if (velocities == null)
		{
			velocities = getSortedVelocities();
		}
		int nLower = Collections.binarySearch(velocities, (((double)X * Math.sqrt(2)) / secTillHit) / unitToWeaponV);
		if (nLower < 0)
		{
			++nLower;
			nLower *= -1;
		}
//		System.out.println(nLower);
//		System.out.println(velocities.size());
		return (double)nLower / velocities.size();
	}

	/**
	 * Weapon class. Used to help with transfer of Weapon data.
	 * 
	 * @author Bryce Sandlund
	 *
	 */
	public class Weapon {
		
		private String name;
		private List<String> weaponLines;
		private List<String> specialDamages = new ArrayList<String>();
		private int classOrdinal;
		private int rangeOrdinal;
		
		//for SpecialDamages
		public double AA = .5;
		public boolean noDamage = false;
		public boolean special = false;
		public int damage;
		public String Class, sClass;
		
		//for WeaponCostUpdater
		boolean costUpdated = false;
		
		int range;
		double effectiveness;
		double reload;
		double overrideReload;
		double DPS;
		double DPSUP;
		int bursts = 1;
		//aoe
		int AOE = 0;
		double AOEcalc = 1;
		double AOEcalc1 = .5;
		double AOEcalc2 = .5;
		double AOEconstant = 0;
		double edgeEffectiveness = 1;
		int X = 20;
		double EDGEcalc = 1;
		//accuracy
		int spray = 0;
		double ACCURACYcalc = 1;
		//how many times do I hit my target
		double accuracyP = 90;
		//how many times do I hit other targets when I miss, sum should not exceed 100
		double missedHitsP = 2;
		//hitP
		double HITPcalc = 1;
		int switchRange = 0;
		double switchSec = 0;
		int weapV = 0;
		int weapA = 0;
		int startV = 0;
		double firstSec = 0;
		double secSec = 0;
		double secTillHit = 0;
		double otherUnits = 0;
		double Y = 0;
		int metalCost, energyCost, buildTime;
		double effectM = 1.0;
		
		public String getWeaponClass()
		{
			return weaponClasses[classOrdinal];
		}
		
		public String getSWeaponClass()
		{
			return weaponSClasses[classOrdinal];
		}
		
		public int getClassOrdinal()
		{
			return classOrdinal;
		}
		
		private void setWeaponClass(int damage)
		{
			for (int i = 0; i < weaponCutoffs.length; ++i)
			{
				if (damage < weaponCutoffs[i])
				{
					classOrdinal = i;
					Class = getWeaponClass();
					sClass = getSWeaponClass();
					break;
				}
			}
		}
		
		private int getRangeOrdinal(int range)
		{
			int ordinal = -1;
			for (int i = 0; i < rangeCutoffs.length; ++i)
			{
				if (range < rangeCutoffs[i])
				{
					ordinal = i;
					break;
				}
			}
			return ordinal;
		}

		private Weapon(String name, List<String> weaponLines, List<String> specialDamages)
		{
			if (name == null || name.isEmpty())
			{
				throw new RuntimeException("Weapon initialized without name");
			}
			this.name = name;
			this.weaponLines = weaponLines;
			this.specialDamages = specialDamages;
			updateVariables();
		}
		
		private void updateVariables()
		{
			for (int i = 0; i < weaponLines.size(); ++i)
			{
				String line1 = weaponLines.get(i);
				String line2 = line1.toLowerCase().trim();
				if (line2.startsWith("//anti-aircraft"))
				{
					AA = FileParsing.parseDouble(line2);
				}
				if (line2.startsWith("//nodamage=1;"))
				{
					noDamage = true;
				}
				if (line2.startsWith("//special=1;"))
				{
					special = true;
				}
				if (line2.startsWith("default="))
				{
					damage = FileParsing.parseInt(line2);
				}
			}
			setWeaponClass(damage);
		}
		
		//I am sacrificing efficiency for the guarantee that the lists will only be updated inside this class
		public List<String> getWeaponLines()
		{
			return new ArrayList<String>(weaponLines);
		}
		
		public List<String> getSpecialDamageLines()
		{
			return new ArrayList<String>(specialDamages);
		}
		
		public List<String> getFullLines()
		{
			List<String> fullLines = new ArrayList<String>();
			for (int i = 0; i < weaponLines.size(); ++i)
			{
				if (weaponLines.get(i).trim().toLowerCase().startsWith("default"))
				{
					fullLines.add(weaponLines.get(i));
					fullLines.addAll(specialDamages);
				}
				else
				{
					fullLines.add(weaponLines.get(i));
				}
			}
			return fullLines;
		}
		
		public void updateSpecialDamages()
		{
			List<String> newLines = spD.getSpecialDamages(this);
			specialDamages = FileEditer.updateList(specialDamages, newLines, name, "Weapon");
		}
		
		public String getName()
		{
			return name;
		}
		
		public int getMetal() throws FileNotFoundException
		{
			if (!costUpdated)
			{
				updateCost();
			}
			return metalCost;
		}
		
		public int getEnergy() throws FileNotFoundException
		{
			if (!costUpdated)
			{
				updateCost();
			}
			return energyCost;
		}
		
		public int getBuildTime() throws FileNotFoundException
		{
			if (!costUpdated)
			{
				updateCost();
			}
			return buildTime;
		}
		
		public String getRangeClass()
		{
			return weaponRanges[rangeOrdinal];
		}
		
		public boolean isUpgradeable()
		{
			return DPS == DPSUP;
		}
		
		/**
		 * Used to clarify range rings and mock weapon names
		 * @return
		 */
		public boolean isRealWeapon()
		{
			return !name.toLowerCase().contains("range") && !name.toLowerCase().startsWith("null");
		}
		
		public void updateCost() throws FileNotFoundException
		{
			for (int i = 0; i < weaponLines.size(); ++i)
			{
				String line1 = weaponLines.get(i);
				String line2 = line1.trim().toLowerCase();
				if (line2.startsWith("range"))
				{
					range = FileParsing.parseInt(line2);
				}
				if (line2.startsWith("//override reloadtime"))
				{
					overrideReload = FileParsing.parseDouble(line2);
				}
				if (line2.startsWith("reloadtime"))
				{
					reload = FileParsing.parseDouble(line2);
					overrideReload = reload;
				}
				if (line2.startsWith("//isdoubleshot"))
				{
					reload = FileParsing.parseDouble(line2);
					overrideReload = reload;
				}
				if (line2.startsWith("weaponvelocity"))
				{
					weapV = FileParsing.parseInt(line2);
				}
				if (line2.startsWith("startvelocity"))
				{
					startV = FileParsing.parseInt(line2);
				}
				if (line2.startsWith("weaponacceleration"))
				{
					weapA = FileParsing.parseInt(line2);
				}
				if (line2.startsWith("areaofeffect"))
				{
					AOE = FileParsing.parseInt(line2);
				}
				if (line2.startsWith("edgeeffectiveness"))
				{
					edgeEffectiveness = FileParsing.parseDouble(line2);
				}
				if (line2.startsWith("//missedhitsp"))
				{
					missedHitsP = FileParsing.parseDouble(line2);
				//	System.out.println(weaponName + missedHitsP);
				}
				if (line2.startsWith("//accuracyp"))
				{
					accuracyP = FileParsing.parseDouble(line2);
				//	System.out.println(weaponName + accuracyP);
				}
				if (line2.startsWith("burst="))
				{
					bursts = FileParsing.parseInt(line2);
				}
				if (line2.startsWith("guidance=1;"))
				{
					accuracyP = 100;
					missedHitsP = 0;
				}
				if (line2.startsWith("//multiply effectiveness"))
				{
					effectM = FileParsing.parseDouble(line2);
				}
			}
			rangeOrdinal = getRangeOrdinal(range);
			DPS = (double)damage / overrideReload * bursts;
			DPSUP = (double)damage / reload * bursts;
			
			//aoe
			//	AOEconstant = .000013636 * AOE * AOE + .000636 * AOE + 1;	//number of units hit with AOE
				AOEconstant = .00001 * Math.PI * AOE * AOE + .004 * AOE + 1;
				EDGEcalc = (1 - edgeEffectiveness) * (1 - Math.sqrt(2)/2) + edgeEffectiveness;	//average effect of damage based on edgeeffectiveness and an average of units being located at sqrt(2)/2 of blast radius
				AOEcalc1 = 1 + (AOEconstant - 1) * EDGEcalc;	
				AOEcalc1 = AOEcalc1 * (accuracyP / 100);				//when a hit, one unit is hit with full effect, the rest with edgeeffectiveness calculation
				AOEcalc2 = AOEconstant * EDGEcalc;				
				AOEcalc2 = AOEcalc2 * (missedHitsP / 100);				//when a miss, all units are hit with edgeeffectiveness calculation
				AOEcalc = AOEcalc1 + AOEcalc2;	//new formula encompasses accuracy, separate accuracy calculation unnecessary
				
				//accuracy unnecessary
				
				//hit%
				if (weapA == 0)
				{
					if (startV != 0)
					{
						secSec = ((double)(2.0/3) * (double)range - switchRange) / (double)startV;
					}
					else
					{
						secSec = ((double)(2.0/3) * (double)range - switchRange) / (double)weapV;
					}
				}
				else
				{
					switchSec = (double)(weapV - startV) / weapA;
					switchRange = (int)((startV + weapA * switchSec) * switchSec);
					firstSec = (-startV + Math.sqrt(startV * startV + 4 * weapA * (double)switchRange)) / (2.0 * weapA);
					secSec = ((double)(2.0/3) * range - switchRange) / weapV;
				}
				secTillHit = firstSec + secSec;
				X = 20 + AOE;
//				HITPcalc = WeaponCostUpdater.hitPercentage(X, secTillHit);
				HITPcalc = hitPercentage(X, secTillHit);
				
				
//				System.out.println("seconds elapsed till V achieved: " + firstSec);
//				System.out.println("seconds elapsed while V achieved: " + secSec);
//				System.out.println("seconds till hit at 2/3 range: " + secTillHit);
//				System.out.println("Hit percentage: " + HITPcalc);
//				System.out.println("AOE calculation:" + AOEcalc);				
//				System.out.println("accuracyP:" + accuracyP);
//				System.out.println("missedHitsP:" + missedHitsP);
//				System.out.println("AOE:" + AOE);
//				System.out.println("edgeEffectiveness:" + edgeEffectiveness);
//				System.out.println("weapV:" + weapV);
//				System.out.println("startV:" + startV);
//				System.out.println("weapA:" + weapA);
				
				effectiveness = AOEcalc * HITPcalc * effectM;
				
				double F = .8 * Math.pow(DPS, 1.15);
				double C = .00000017 * Math.pow(range, 2.38) + .0015*range + .4341;
				double B = .000003512 * range * range + .001375 * range + .3341;
				
//				System.out.println("Range Metal: " + B + " Range Energy: " + C);
//				System.out.println("DPS Multiplier: " + F);
//				System.out.println("Effectiveness M: " + effectiveness);
				
				metalCost = (int)Math.round(.6 * F * B * effectiveness);
				energyCost = (int)Math.round(.7 * 4 * F * C * effectiveness);
				buildTime = (int)Math.round(10*Math.pow(DPS, 1.1) * B * effectiveness);
				costUpdated = true;
		}
	}
}
