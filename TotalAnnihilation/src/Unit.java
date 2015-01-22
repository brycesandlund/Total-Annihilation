import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to denote Unit .fbi file. Not used in UnitEditer, but should provide a solid framework for UnitFiles,
 * CostUpdater, and any other universal editing systems. Can only be constructed on an existing Unit file.
 * @author Owner
 *
 */
public class Unit {
	
	public static final String[] unitClasses = {"Very Light", "Light", "Medium", "Heavy", "Very Heavy", "Extremely Heavy", "Ultra Heavy", "Krogoth Unit"};
	public static final String[] unitClassesS = {"VL", "L", "M", "H", "VH", "EH", "UH", "K"};
	public static final int[] unitCutoffs = {500, 1000, 2000, 4000, 8000, 16000, 32000, 10000000};
	public static final int dMaxLength = 63;
	
	/**
	 * Internal variables
	 */
	private int classOrdinal;
	private String unitName;
	public File fileOnDisk;
	private List<String> unitLines = new ArrayList<String>();
	private List<String> newUnitLines = new ArrayList<String>();
	/**
	 * Making this static should help efficiency, with no problems, right?..
	 */
	private static TabaWeapons tabaWeapons;
	
	/**
	 * Unit FBI primitive booleans. See UnitFiles.java for grouping logic.
	 */
	public boolean hasWeapon, unit, constr, isWeapon, isUpgrade, isPlane, isTurret, moves, isWorkingUnit;
	
	/**
	 * Unit FBI traits used from UnitCostUpdater
	 */
	public int maxDamage = 0;
	public double modDamage = 0;
	public double maxVelocity = 0;
	public int overallDamage = 0;
	public int unitMetal = 0, unitEnergy = 0, unitBT = 0;
	public String Range = "", Class = "", sClass = "", Type = "";
	public boolean noUpgrade = false;
	public boolean overrideMaxDamage = false;
	
	/*
	 * For cost
	 */
	private String weapon1, weapon2, weapon3;
	private double armoredState;
	
	public Unit(String unitName) throws FileNotFoundException
	{
		this.unitName = unitName;
		construct(new File(FileEditer.TABA_DIRECTORY + "units\\" + unitName + ".FBI"));
	}
	
	public Unit(File fileName) throws FileNotFoundException
	{
		unitName = unitFileToString(fileName);
		construct(fileName);
	}
	
	public Unit(String unitName, TabaWeapons tabaWeapons) throws FileNotFoundException
	{
		this.unitName = unitName;
		this.tabaWeapons = tabaWeapons;
		construct(new File(FileEditer.TABA_DIRECTORY + "units\\" + unitName + ".FBI"));
	}
	
	public Unit(File fileName, TabaWeapons tabaWeapons) throws FileNotFoundException
	{
		unitName = unitFileToString(fileName);
		this.tabaWeapons = tabaWeapons;
		construct(fileName);
	}
	
	public static String unitFileToString(File input)
	{
		return input.toString().replace(".FBI", "").replace(".fbi", "").replace(FileEditer.TABA_DIRECTORY + "Units\\", "");
	}
	
	private void setUnitClass(int maxDamage)
	{
		for (int i = 0; i < unitCutoffs.length; ++i)
		{
			if (maxDamage < unitCutoffs[i])
			{
				classOrdinal = i;
				Class = getArmorClass();
				sClass = getSArmorClass();
				break;
			}
		}
	}
	
	public String getUnitName()
	{
		return unitName;
	}
	
	public boolean isUpgradeableTank()
	{
		return !noUpgrade && modDamage != maxDamage;
	}
	
	/**
	 * Code taken from old UnitFiles or old UnitCostUpdater, not optimized or convenient.
	 * @param filepath
	 * @throws FileNotFoundException 
	 */
	private void construct(File filepath) throws FileNotFoundException
	{
		fileOnDisk = filepath;
		unitLines = FileEditer.getLines(filepath);
		for (int i = 0; i < unitLines.size(); ++i)
		{
			String line1 = unitLines.get(i);
			String line2 = line1.trim().toLowerCase();
			
			//UnitCostUpdater file parsing
			if (line2.startsWith("maxdamage"))
			{
				maxDamage = FileParsing.parseInt(line2);
				modDamage = maxDamage;
			}
			if (line2.startsWith("//override maxdamage=1;"))	//without this line, program will assume DamageModifier is only for an upgrade, this lets it know it is part of the units overall armor and should be calculated as MaxDamage
			{
				noUpgrade = true;
				overrideMaxDamage = true;
			}
			if (line2.startsWith("//override maxdamage=2;"))	//this setting ignores DamageModifier for units with armor state.
			{
				noUpgrade = true;
				overrideMaxDamage = false;
			}
			if (line2.startsWith("//armoredstate"))
			{
				armoredState = FileParsing.parseDouble(line2);	//this setting describes the percentage of time in armor state. 1.0 = 100% of the time, 0.0 = 0%. If not set, 0% is assumed.
				if (armoredState < -.00001 || armoredState > 1.00001)
				{
					throw new RuntimeException("Improper armoredstate in " + getUnitName());
				}
			}
			if (line2.startsWith("maxvelocity"))
			{
				maxVelocity = FileParsing.parseDouble(line2);
			}
			if (line2.equals("TEDClass=KBOT;".toLowerCase()))
			{
				Type = "Kbot";
			}
			if (line2.equals("TEDClass=TANK;".toLowerCase()))
			{
				Type = "Tank";
			}
			if (line2.equals("TEDClass=VTOL;".toLowerCase()))
			{
				Type = "Aircraft";
			}
			if (line2.equals("TEDClass=SHIP;".toLowerCase()))
			{
				Type = "Ship";
			}
			if (line2.equals("TEDClass=FORT;".toLowerCase()))
			{
				Type = "Turret";
			}
			
			if (line2.startsWith("damagemodifier"))
			{
				if (overrideMaxDamage)
				{
					modDamage = (double)maxDamage / FileParsing.parseDouble(line2);
					maxDamage = (int)(maxDamage / FileParsing.parseDouble(line2));
				}
				if (!noUpgrade)
				{
					modDamage = (double)maxDamage / FileParsing.parseDouble(line2);
				}
			}
			
			if (line2.equals("weapon1=null_weapon;") || line2.equals("weapon1=null_weapon_gunship;"))
			{
				Range = "Close-Ranged ";
			}
			if (line2.equals("weapon1=null_weapon_int;"))
			{
				Range = "Mid-Ranged ";
			}
			if (line2.equals("weapon1=null_weapon_far;"))
			{
				Range = "Long-Ranged ";
			}
			if (line2.equals("hoverattack=1;"))
			{
				Type = "Gunship";
			}
			if (Type == "Turret")
			{
				Range = "";
			}
			
			//UnitClasses file Parsing
			if (line2.startsWith("//weapon=1;"))
			{
				isWeapon = true;
			}
			/*if (line2.startsWith("//turret=1;".toLowerCase()))
			{
				isTurret = true;
			}*/
			if (line2.startsWith("//"))
			{
				continue;
			}
			if (line2.contains(" kbot") || line2.contains(" tank") || line2.contains(" ship"))
			{
				unit = true;
			}
			if (line2.contains("constr") || line2.startsWith("canrepair=1;".toLowerCase()) || line2.startsWith("canreclamate=1;".toLowerCase()))
			{
				constr = true;
			}
			if (line2.startsWith("weapon"))
			{
				hasWeapon = true;
			}
			if (line2.startsWith("weapon1=NULL_".toLowerCase()))
			{
				isWorkingUnit = true;
			}
			if (line2.startsWith("UnitName=ARMUP".toLowerCase()) || line2.startsWith("UnitName=CORUP".toLowerCase()) || (line2.startsWith("UnitName".toLowerCase()) && line2.contains("upgrade")))
			{
				isUpgrade = true;
			}
			if (line2.startsWith("Category=".toLowerCase()) && line2.contains(" vtol") || line2.startsWith("TEDClass=VTOL;".toLowerCase()))
			{
				isPlane = true;
			}
			if (line2.startsWith("TEDClass=FORT;".toLowerCase()))
			{
				isTurret = true;
			}
			
			//costs
			if (line2.startsWith("weapon1"))
			{
				weapon1 = FileParsing.parseString(line2);
			}
			else if (line2.startsWith("weapon2"))
			{
				weapon2 = FileParsing.parseString(line2);
			}
			else if (line2.startsWith("weapon3"))
			{
				weapon3 = FileParsing.parseString(line2);
			}
		}
		overallDamage = (int)Math.round(maxDamage * (1 - armoredState) + modDamage * armoredState);
		setUnitClass(overallDamage);
		if (maxVelocity > .1)
		{
			moves = true;
		}
	}

	public int getClassOrdinal()
	{
		return classOrdinal;
	}
	
	public String getArmorClass()
	{
		return unitClasses[classOrdinal];
	}
	
	public String getSArmorClass()
	{
		return unitClassesS[classOrdinal];
	}
	
	public int getOverallDamage()
	{
		return overallDamage;
	}
	
	public void updateOnDisk() throws FileNotFoundException
	{
		if (isWorkingUnit)	//will update some units that haven't been redone yet, but it's interesting to see their cost regardless
		{
			tankCost();
		}
		else if (isWeapon)
		{
			if (tabaWeapons == null)
			{
				tabaWeapons = new TabaWeapons();
			}
			weaponCost();
		}
		else if (isTurret)
		{
			if (tabaWeapons == null)
			{
				tabaWeapons = new TabaWeapons();
			}
			turretCost();
		}
		FileEditer.updateFile(unitLines, newUnitLines, fileOnDisk);
	}
	
	private void tankCost()
	{
		unitMetal = tankMetalCalculation(overallDamage, maxVelocity);
		unitEnergy = tankEnergyCalculation(overallDamage, maxVelocity);
		unitBT = tankBTCalculation(overallDamage, maxVelocity);
		
		for(int j = 0; j < unitLines.size(); ++j)
		{
			String line1 = unitLines.get(j);
			String line2 = unitLines.get(j).trim().toLowerCase();
			if (line2.startsWith("buildcostenergy"))
			{
				line1 = "\tBuildCostEnergy=" + unitEnergy + ";";
				line2 = line1.trim().toLowerCase();
			}
			if (line2.startsWith("buildcostmetal"))
			{
				line1 = "\tBuildCostMetal=" + unitMetal + ";";
				line2 = line1.trim().toLowerCase();
			}
			if (line2.startsWith("buildtime"))
			{
				line1 = "\tBuildTime=" + unitBT + ";";
				line2 = line1.trim().toLowerCase();
			}
			if (line2.startsWith("description"))
			{
				if ((int)modDamage != maxDamage)
				{
					line1 = "\tDescription=" + Range + Class + " " + Type + ", A=" + maxDamage + ", UpA=" + (int)modDamage + ", V=" + maxVelocity + ";";
				}
				else
				{
					line1 = "\tDescription=" + Range + Class + " " + Type + ", A=" + maxDamage + ", V=" + maxVelocity + ";";
				}
				//if description is too long, use sClass and repeat above lines
				if (line1.trim().length() > dMaxLength)
				{
					Class = sClass;
				}
				if ((int)modDamage != maxDamage)
				{
					line1 = "\tDescription=" + Range + Class + " " + Type + ", A=" + maxDamage + ", UpA=" + (int)modDamage + ", V=" + maxVelocity + ";";
				}
				else
				{
					line1 = "\tDescription=" + Range + Class + " " + Type + ", A=" + maxDamage + ", V=" + maxVelocity + ";";
				}
				line2 = line1.trim().toLowerCase();
			}
			newUnitLines.add(line1);
		}
	}
	
	public static int tankMetalCalculation(int maxDamage, double maxVelocity)
	{
		double C, M, T;
		C = .039 * Math.pow(maxDamage, 1.2);
		M = .22 * Math.pow(maxDamage, .1) * (2 + Math.pow(maxVelocity, 1.0));
		T = .9 * M * C;
		return AnswerRounded(.6 * .65 * T);
	}
	
	public static int tankEnergyCalculation(int maxDamage, double maxVelocity)
	{
		double S;
		S = .22 * Math.pow(maxDamage, .1) * (.5 + Math.pow(maxVelocity, 1.4));
		return AnswerRounded(.7 * .6 * Math.pow(maxDamage, 1.09) * S);
	}
	
	public static int tankBTCalculation(int maxDamage, double maxVelocity)
	{
		double T;
//		T = .22 * Math.pow(maxDamage, .1) * (1 + Math.pow(maxVelocity, 1.5));
		T = .22 * Math.pow(maxDamage, .1) * (1 + Math.pow(maxVelocity, 1.3));
		return AnswerRounded(.7 * Math.pow(maxDamage, 1.1) * T);
	}
	
	public static int AnswerRounded(double answer)
	{
		return (int)((double)answer + .5);
	}
	
	private void weaponCost() throws FileNotFoundException
	{
		//some weapons are not primary
		if (weapon1 == null)
		{
			if (weapon2 == null)
			{
				if (weapon3 == null)
				{
					throw new RuntimeException("Weapon " + unitName + " has no weapon.");
				}
				else
				{
					weapon1 = weapon3;
				}
			}
			else
			{
				weapon1 = weapon2;
			}
		}
		TabaWeapons.Weapon weapon = tabaWeapons.getWeapon(weapon1);
		if (weapon == null)
		{
			System.out.println("null weapon");
		}
		unitMetal = weapon.getMetal();
		unitEnergy = weapon.getEnergy();
		unitBT = weapon.getBuildTime();
		String description;
		if (weapon.isUpgradeable())
		{
			description = String.format(weapon.getRangeClass() + " " + weapon.getWeaponClass() + " Weapon, DPS=%.0f, R=%d, E=%.2f", weapon.DPS, weapon.range, weapon.effectiveness);
		}
		else
		{
			description = String.format(weapon.getRangeClass() + " " + weapon.getWeaponClass() + " Weapon, DPS=%.0f, R=%d, E=%.2f, UPDPS=%.0f", weapon.DPS, weapon.range, weapon.effectiveness, weapon.DPSUP);
		}
		
		if (description.length() >= Unit.dMaxLength)
		{
			if (weapon.isUpgradeable())
			{
				description = String.format(weapon.getRangeClass() + " " + weapon.getSWeaponClass() + " Weapon, DPS=%.0f, R=%d, E=%.2f", weapon.DPS, weapon.range, weapon.effectiveness);
			}
			else
			{
				description = String.format(weapon.getRangeClass() + " " + weapon.getSWeaponClass() + " Weapon, DPS=%.0f, R=%d, E=%.2f, UPDPS=%.0f", weapon.DPS, weapon.range, weapon.effectiveness, weapon.DPSUP);
			}
		}
		newUnitLines = FileParsing.replaceTags(unitLines, new String[]{"buildcostmetal", "buildcostenergy", "buildtime", "description"}, new String[]{unitMetal + "", unitEnergy + "", unitBT + "", description});
	}
	
	private void turretCost() throws FileNotFoundException
	{
		List<TabaWeapons.Weapon> weapons = new ArrayList<TabaWeapons.Weapon>();
		if (weapon1 != null)
		{
			TabaWeapons.Weapon weapon = tabaWeapons.getWeapon(weapon1);
			if (weapon.isRealWeapon())
			{
				weapons.add(weapon);
			}
		}
		if (weapon2 != null)
		{
			TabaWeapons.Weapon weapon = tabaWeapons.getWeapon(weapon2);
			if (weapon.isRealWeapon())
			{
				weapons.add(weapon);
			}
		}
		if (weapon3 != null)
		{
			TabaWeapons.Weapon weapon = tabaWeapons.getWeapon(weapon3);
			if (weapon.isRealWeapon())
			{
				weapons.add(weapon);
			}
		}
		
		for (int i = 0; i < weapons.size(); ++i)
		{
			unitMetal += weapons.get(i).getMetal();
			unitEnergy += weapons.get(i).getEnergy();
			unitBT += weapons.get(i).getBuildTime();
		}
		
		unitMetal += tankMetalCalculation(overallDamage, 0);
		unitEnergy += tankEnergyCalculation(overallDamage, 0);
		unitBT += tankBTCalculation(overallDamage, 0);
		
		String description = "";
		
		if (weapons.size() == 3)
		{
			description = String.format(getArmorClass() + " Turret, " + weapons.get(0).getWeaponClass() + ", " + weapons.get(1).getWeaponClass() + ", " + weapons.get(2).getWeaponClass() + " Weapon, A=%d, DPS=%.0f, %.0f, %.0f, R=%d, %d, %d, E=%.2f, %.2f, %.2f", maxDamage, weapons.get(0).DPS, weapons.get(1).DPS, weapons.get(2).DPS, weapons.get(0).range, weapons.get(1).range, weapons.get(2).range, weapons.get(0).effectiveness, weapons.get(1).effectiveness, weapons.get(2).effectiveness);
			if (description.length() > dMaxLength)
			{
				description = String.format(getSArmorClass() + " Turret, " + weapons.get(0).getSWeaponClass() + ", " + weapons.get(1).getSWeaponClass() + ", " + weapons.get(2).getSWeaponClass() + " Weapon, A=%d, DPS=%.0f, %.0f, %.0f, R=%d, %d, %d, E=%.2f, %.2f, %.2f", maxDamage, weapons.get(0).DPS, weapons.get(1).DPS, weapons.get(2).DPS, weapons.get(0).range, weapons.get(1).range, weapons.get(2).range, weapons.get(0).effectiveness, weapons.get(1).effectiveness, weapons.get(2).effectiveness);
			}
		}
		if (weapons.size() == 2 || description.length() > dMaxLength)
		{
			description = String.format(getArmorClass() + " Turret, " + weapons.get(0).getWeaponClass() + ", " + weapons.get(1).getWeaponClass() + " Weapon, A=%d, DPS=%.0f, %.0f, R=%d, %d, E=%.2f, %.2f", maxDamage, weapons.get(0).DPS, weapons.get(1).DPS, weapons.get(0).range, weapons.get(1).range, weapons.get(0).effectiveness, weapons.get(1).effectiveness);
			if (description.length() > dMaxLength)
			{
				description = String.format(getSArmorClass() + " Turret, " + weapons.get(0).getSWeaponClass() + ", " + weapons.get(1).getSWeaponClass() + " Weapon, A=%d, DPS=%.0f, %.0f, R=%d, %d, E=%.2f, %.2f", maxDamage, weapons.get(0).DPS, weapons.get(1).DPS, weapons.get(0).range, weapons.get(1).range, weapons.get(0).effectiveness, weapons.get(1).effectiveness);
			}
		}
		if (weapons.size() == 1 || description.length() > dMaxLength)
		{
			description = String.format(getArmorClass() + " Turret, " + weapons.get(0).getWeaponClass() + " Weapon, A=%d, DPS=%.0f, R=%d, E=%.2f", maxDamage, weapons.get(0).DPS, weapons.get(0).range, weapons.get(0).effectiveness);
			if (description.length() > dMaxLength)
			{
				description = String.format(getSArmorClass() + " Turret, " + weapons.get(0).getSWeaponClass() + " Weapon, A=%d, DPS=%.0f, R=%d, E=%.2f", maxDamage, weapons.get(0).DPS, weapons.get(0).range, weapons.get(0).effectiveness);
			}
		}
		if (weapons.size() == 0)	//I'm assuming above description with Sclasses will be short enough, if not, we will cut off stuff, that's better than acting like we have no weapon
		{
			description = getArmorClass() + " Fortification, A=" + getOverallDamage();	//this should never be too big for description
		}
		newUnitLines = FileParsing.replaceTags(unitLines, new String[]{"buildcostmetal", "buildcostenergy", "buildtime", "description"}, new String[]{unitMetal + "", unitEnergy + "", unitBT + "", description});
	}
	
	/**
	 * Use getUnitName() when looking for specifically UnitName. This will be used only for human eyes.
	 */
	public String toString()
	{
		return getUnitName();
	}
}
