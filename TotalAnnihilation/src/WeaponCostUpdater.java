import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Updates weapon files.  Must be run a single time after UnitCostUpdater for turrets to have proper cost.
 * @author Bryce Sandlund
 *
 */
public class WeaponCostUpdater {
	final int avgUSize = 20; //if changed, reupdate AOEcalc function
	private static final double unitToWeaponV = 45/1.6; //conversion factor
	public static void main(String[] args) throws FileNotFoundException
	{
	/*	File weaponTdfs = new File("C:\\Users\\Owner\\Documents\\TABA\\Rev31.gp3 - TABA Unlimited\\Weapons\\TabaWeapons.tdf");
		Scanner weaponIn = new Scanner(weaponTdfs);

		ArrayList<String> weaponLines = new ArrayList<String>();
		while (weaponIn.hasNextLine())
		{
			weaponLines.add(weaponIn.nextLine());
		}
		weaponIn.close();*/
		
		/*File currentFile = new File("C:/Users/Owner/Documents/TABA/Rev31.gp3 - TABA Unlimited/Units/");
		Scanner currentIn = new Scanner(currentFile);*/
		
		File weapons = new File(FileEditer.TABA_DIRECTORY + "Weapons/TabaWeapons.tdf");
		Scanner weaponIn = new Scanner(weapons);
		ArrayList<String> weaponLines = new ArrayList<String>();
		String line;
		while (weaponIn.hasNextLine())
		{
			line = weaponIn.nextLine().trim();
			weaponLines.add(line);
		}
		
		
		UnitFiles files = new UnitFiles();
		UnitClasses classes = new UnitClasses(files);
	    ArrayList<String> unitLines = new ArrayList<String>();
	    ArrayList<String> newUnitLines = new ArrayList<String>();
	    String line1, line2;
	    File currentFile;
	    Scanner currentIn;
	    int n, m = 0;
		String weaponName;
		n = 0;
		int k = 0;
		int nModified = 0;
		
		int range;
		double effectiveness;
		int damage;
		double reload;
		double overrideReload;
		double DPS;
		double DPSUP;
		int bursts;
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
		//description variables
		String rangeClass = "";
		String weaponClass = "";
		String sWeaponClass = "";
		//for nice sysout messages
		boolean updates = false;
		
		
		
		
		
		int unitMetal = 0, unitEnergy = 0, unitBT = 0;
		
		boolean weaponFound;

	/*    for (int i = 0; i < listOfFiles.length; i++)
	    {
	    	currentFile = listOfFiles[i];
			currentIn = new Scanner(currentFile);
			while (currentIn.hasNextLine())
			{
				line1 = currentIn.nextLine();
				line2 = line1.trim();
			//	System.out.println(line2);
				if (line2.startsWith("//weapon=1;") && listOfFiles[i].getName().toUpperCase().endsWith(".FBI"))
				{
					Units.add(listOfFiles[i].getName().toUpperCase().replace(".FBI", ""));
				}
			}
			currentIn.close();
	    }*/
		
	    for (int i = 0; i < files.weaponsAndTurrets.size(); ++i)
		//	for (int i = 0; i < 3; ++i)
			{
	    		weaponName = new String();
	    		range = 0;
	    		reload = 0;
	    		overrideReload = 0;
	    		damage = 0;
	    		DPS = 0;
	    		DPSUP = 0;
	    		effectiveness = 0;
				bursts = 1;
				//aoe
				AOE = 0;
				AOEcalc = 1;
				AOEcalc1 = .5;
				AOEcalc2 = .5;
				AOEconstant = 0;
				edgeEffectiveness = 1;
				X = 20;
				EDGEcalc = 1;
				//accuracy
				spray = 0;
				ACCURACYcalc = 1;
				accuracyP = 90;
				missedHitsP = 2;
				//hitP
				HITPcalc = 1;
				switchRange = 0;
				switchSec = 0;
				weapV = 0;
				weapA = 0;
				startV = 0;
				firstSec = 0;
				secSec = 0;
				secTillHit = 0;
				otherUnits = 0;
				Y = 0;
				boolean isTurret = false;


				currentFile = files.weaponsAndTurrets.get(i).fileOnDisk;
				currentIn = new Scanner(currentFile);
				
				while (currentIn.hasNextLine())
				{
					unitLines.add(currentIn.nextLine());
				}
				currentIn.close();
								

				for(int j = 0; j < unitLines.size(); ++j)
				{
					line1 = unitLines.get(j);
					line2 = unitLines.get(j).trim().toLowerCase();
					if (line2.startsWith("weapon1="))
					{
//						for(int l = 0; l < line2.length(); ++l)
//						{
//							if(line2.charAt(l) == '=')
//							{
//								n = l+1;
//							}
//							if(line2.charAt(l) == ';')
//							{
//								m = l;
//							}
//						}
//						weaponName = (line2.substring(n, m).trim());
						weaponName = FileParsing.parseString(line2);
					//	System.out.println("[" + weaponName + "]");
//						break;
					}
					if (line2.startsWith("TEDClass=FORT;".toLowerCase()))
					{
						isTurret = true;
					}
					if (line2.startsWith("buildcostenergy"))
					{
						unitEnergy = FileParsing.parseInt(line2);
					}
					if (line2.startsWith("buildcostmetal"))
					{
						unitMetal = FileParsing.parseInt(line2);
					}
					if (line2.startsWith("buildtime"))
					{
						unitBT = FileParsing.parseInt(line2);
					}
				}
				if (!isTurret)
				{
					unitEnergy = 0;
					unitMetal = 0;
					unitBT = 0;
				}
				
				weaponFound = false;
				for (int j = 0; j < weaponLines.size(); ++j)
				{
					line1 = weaponLines.get(j);
					line2 = weaponLines.get(j).trim().toLowerCase();
					if (line2.startsWith("[" + weaponName + "]"))
					{
					//	System.out.println(weaponName + " found");
						weaponFound = true;
					}
					if (weaponFound)
					{
					//	System.out.println(weaponName + " found");
						if (weaponName.equals("ARMTRUCK_MISSILE"))
						{
							System.out.println("here");
						}
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
						if (line2.startsWith("default"))
						{
							damage = FileParsing.parseInt(line2);
							DPS = (double)damage / overrideReload * bursts;
							DPSUP = (double)damage / reload * bursts;

						//	System.out.println("DPS:" + DPS);
							break;
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
						
//						if (line2.startsWith("//override"))
//						{
//							Scanner override = new Scanner(line1);
//							override.next()
//						}
					}
					
				}
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
				
				effectiveness = AOEcalc * HITPcalc;
				
				double F = .8 * Math.pow(DPS, 1.15);
				double C = .00000017 * Math.pow(range, 2.38) + .0015*range + .4341;
				double B = .000003512 * range * range + .001375 * range + .3341;
				
//				System.out.println("Range Metal: " + B + " Range Energy: " + C);
//				System.out.println("DPS Multiplier: " + F);
//				System.out.println("Effectiveness M: " + effectiveness);
				
				if (!weaponName.isEmpty())
				{
					unitMetal += (int)Math.round(.6 * F * B * effectiveness);
					unitEnergy += (int)Math.round(.7 * 4 * F * C * effectiveness);
					unitBT += (int)Math.round(10*Math.pow(DPS, 1.1) * B * effectiveness);
				}
				
//				//description information
//				if (range < 520)
//					rangeClass = "Close-Ranged ";
//				else if (range < 870)
//					rangeClass = "Mid-Ranged ";
//				else
//					rangeClass = "Long-Ranged ";
//			//	System.out.println(rangeClass + range);
//				
//				if (damage < SpecialDamages.weaponClasses[0])
//				{
//					weaponClass = "Very Light ";
//					sWeaponClass = "VL ";
//				}
//				else if (damage < SpecialDamages.weaponClasses[1])
//				{
//					weaponClass = "Light ";
//					sWeaponClass = "L ";
//				}
//				else if (damage < SpecialDamages.weaponClasses[2])
//				{
//					weaponClass = "Medium ";
//					sWeaponClass = "M ";
//				}
//				else if (damage < SpecialDamages.weaponClasses[3])
//				{
//					weaponClass = "Heavy ";
//					sWeaponClass = "H ";
//				}
//				else if (damage < SpecialDamages.weaponClasses[4])
//				{
//					weaponClass = "Very Heavy ";
//					sWeaponClass = "VH ";
//				}
//				else if (damage < SpecialDamages.weaponClasses[5])
//				{
//					weaponClass = "Extremely Heavy ";
//					sWeaponClass = "EH ";
//				}
//				else if (damage < SpecialDamages.weaponClasses[6])
//				{
//					weaponClass = "Ultra Heavy ";
//					sWeaponClass = "UH ";
//				}
//				else if (damage < 64000)
//				{
//					weaponClass = "D-Gun ";
//					sWeaponClass = "DG ";
//				}
				
				
//				for(int j = 0; j < unitLines.size(); ++j)
//				{
//					line1 = unitLines.get(j);
//					line2 = unitLines.get(j).trim().toLowerCase();
//					if (line2.startsWith("buildcostenergy"))
//					{
//						line1 = "\tBuildCostEnergy=" + unitEnergy + ";";
//						line2 = line1.trim().toLowerCase();
//					}
//					if (line2.startsWith("buildcostmetal"))
//					{
//						line1 = "\tBuildCostMetal=" + unitMetal + ";";
//						line2 = line1.trim().toLowerCase();
//					}
//					if (line2.startsWith("buildtime"))
//					{
//						line1 = "\tBuildTime=" + unitBT + ";";
//						line2 = line1.trim().toLowerCase();
//					}
//					if (line2.startsWith("description"))
//					{
//					//	unitOut.println("\tDescription=" + rangeClass + weaponClass);// "DPS=" + DPS + " " + "R=" + range + " " + "E=" + effectiveness;
//					//	unitOut.printf("\tDescription=" + rangeClass + weaponClass + "Weapon, DPS=%.0f, R=%d, E=%.2f;\n", DPS, range, effectiveness);
//						if (!isTurret)
//						{
//							if (overrideReload == reload)
//							{
//								line1 = String.format("\tDescription=" + rangeClass + weaponClass + "Weapon, DPS=%.0f, R=%d, E=%.2f;", DPS, range, effectiveness);
//								line2 = line1.trim().toLowerCase();
//							}
//							else
//							{
//								line1 = String.format("\tDescription=" + rangeClass + weaponClass + "Weapon, DPS=%.0f, R=%d, E=%.2f, UPDPS=%.0f;", DPS, range, effectiveness, DPSUP);
//								line2 = line1.trim().toLowerCase();
//							}
//						}
//						else
//						{
//							String oldDescription = FileParsing.parseString(line2);
////							System.out.println(oldDescription);
//							int maxDamage = FileParsing.parseNextInt(oldDescription);
//							String Class = classes.getClass(currentFile);
//							String sClass = classes.getsClass(currentFile);
//							if (overrideReload == reload)
//							{
//								line1 = String.format("\tDescription=" + Class + "Turret, " + weaponClass + "Weapon," + " A=%d, DPS=%.0f, R=%d, E=%.2f;", maxDamage, DPS, range, effectiveness);
//								line2 = line1.trim().toLowerCase();
//							}
//							else
//							{
//								line1 = String.format("\tDescription=" + Class + "Turret, " + weaponClass + "Weapon," + " A=%d, DPS=%.0f, R=%d, E=%.2f, UPDPS=%.0f;", maxDamage, DPS, range, effectiveness, DPSUP);
//								line2 = line1.trim().toLowerCase();
//							}
////							if (line1.length() >= UnitCostUpdater.dMaxLength)	//Descriptions are cut off at a certain length.  If this length is achieved, shorten words and update description.
////							{
////								Class = sClass;
////							}
//							if (overrideReload == reload)
//							{
//								line1 = String.format("\tDescription=" + Class + "Turret, " + weaponClass + "Weapon," + " A=%d, DPS=%.0f, R=%d, E=%.2f;", maxDamage, DPS, range, effectiveness);
//								line2 = line1.trim().toLowerCase();
//							}
//							else
//							{
//								line1 = String.format("\tDescription=" + Class + "Turret, " + weaponClass + "Weapon," + " A=%d, DPS=%.0f, R=%d, E=%.2f, UPDPS=%.0f;", maxDamage, DPS, range, effectiveness, DPSUP);
//								line2 = line1.trim().toLowerCase();
//							}
//						}
//						if (line2.length() >= UnitCostUpdater.dMaxLength)	//Descriptions are cut off at a certain length.  If this length is achieved, shorten words and update description.
//						{
//							weaponClass = sWeaponClass;
//						}
//						if (!isTurret)
//						{
//							if (overrideReload == reload)
//							{
//								line1 = String.format("\tDescription=" + rangeClass + weaponClass + "Weapon, DPS=%.0f, R=%d, E=%.2f;", DPS, range, effectiveness);
//								line2 = line1.trim().toLowerCase();
//							}
//							else
//							{
//								line1 = String.format("\tDescription=" + rangeClass + weaponClass + "Weapon, DPS=%.0f, R=%d, E=%.2f, UPDPS=%.0f;", DPS, range, effectiveness, DPSUP);
//								line2 = line1.trim().toLowerCase();
//							}
//						}
//						else
//						{
//							String oldDescription = FileParsing.parseString(line2);
////							System.out.println(oldDescription);
//							int maxDamage = FileParsing.parseNextInt(oldDescription);
//							String Class = classes.getClass(currentFile);
//							String sClass = classes.getsClass(currentFile);
//							if (line2.length() >= UnitCostUpdater.dMaxLength)	//Descriptions are cut off at a certain length.  If this length is achieved, shorten words and update description.
//							{
//								Class = sClass;
//							}
//							if (overrideReload == reload)
//							{
//								line1 = String.format("\tDescription=" + Class + "Turret, " + weaponClass + "Weapon," + " A=%d, DPS=%.0f, R=%d, E=%.2f;", maxDamage, DPS, range, effectiveness);
//								line2 = line1.trim().toLowerCase();
//							}
//							else
//							{
//								line1 = String.format("\tDescription=" + Class + "Turret, " + weaponClass + "Weapon," + " A=%d, DPS=%.0f, R=%d, E=%.2f, UPDPS=%.0f;", maxDamage, DPS, range, effectiveness, DPSUP);
//								line2 = line1.trim().toLowerCase();
//							}
//							if (overrideReload == reload)
//							{
//								line1 = String.format("\tDescription=" + Class + "Turret, " + weaponClass + "Weapon," + " A=%d, DPS=%.0f, R=%d, E=%.2f;", maxDamage, DPS, range, effectiveness);
//								line2 = line1.trim().toLowerCase();
//							}
//							else
//							{
//								line1 = String.format("\tDescription=" + Class + "Turret, " + weaponClass + "Weapon," + " A=%d, DPS=%.0f, R=%d, E=%.2f, UPDPS=%.0f;", maxDamage, DPS, range, effectiveness, DPSUP);
//								line2 = line1.trim().toLowerCase();
//							}
//						}
//					}
//					//unitOut.println(line1);
//		//			System.out.println(line1);
//					newUnitLines.add(line1);
//				}
//				if (!newUnitLines.equals(unitLines))
//				{
//					updates = true;
//					PrintWriter unitOut = new PrintWriter(currentFile);
//					try
//					{
//						for(k = 0; k < newUnitLines.size(); ++k)
//						{
//							unitOut.println(newUnitLines.get(k));
//						}
//					}
//					finally
//					{
//						for(; k < unitLines.size(); ++k)
//						{
//							unitOut.println(unitLines.get(k));
//						}
//						unitOut.close();
//					}
//					++nModified;
//					System.out.println(nModified + ": Weapon File " + files.weaponsAndTurrets.get(i) + " Updated.");
//				}
//				unitLines.clear();
//				newUnitLines.clear();
			}
			if (updates)
				System.out.println("Weapon Files Updated.");
			if (!updates)
				System.out.println("No Weapon Files Updated");
		}
		
		/**
		 * An attempt at determining the hitPercentage, based on some fairly inadequate parameters
		 * @param X
		 * @param secTillHit
		 * @return
		 * @throws FileNotFoundException
		 */
		public static double hitPercentage(int X, double secTillHit) throws FileNotFoundException
		{
			File units = new File("C:/Users/Owner/Documents/TABA/Rev31.gp3 - TABA Unlimited/Units/");
		    File[] listOfFiles = units.listFiles();
			int totalVelocities = 0;
			int favVelocities = 0;
			int n = 0, m = 0;
			double maxVelocity = 0;
			ArrayList<Double> velocities = new ArrayList<Double>();
		    for (int i = 0; i < listOfFiles.length; i++)
		    {
		    	boolean isPlane = false;
		    	boolean moves = false;
		    	File currentFile = listOfFiles[i];
				Scanner currentIn = new Scanner(currentFile);
				while (currentIn.hasNextLine())
				{
					String line1 = currentIn.nextLine();
					String line2 = line1.trim();
					if (line2.startsWith("MaxVelocity"))
					{
						//System.out.println(listOfFiles[i]);
						moves = true;
						for(int l = 0; l < line2.length(); ++l)
						{
							if(line2.charAt(l) == '=')
							{
								n = l+1;
							}
							if(line2.charAt(l) == ';')
							{
								m = l;
							}
						}
						maxVelocity = Double.parseDouble(line2.substring(n, m).trim());
					}
					if (line2.startsWith("Category=") && line2.contains(" VTOL"))
					{
						//System.out.println(listOfFiles[i]);
						isPlane = true;
					}
				}
				if (moves && !isPlane && maxVelocity > .01)
				{
				//	System.out.println(maxVelocity);
					velocities.add(maxVelocity * (unitToWeaponV));
				}
		    }
		    for (int i = 0; i < velocities.size(); ++i)
		    {
		    	++totalVelocities;
		    	if (velocities.get(i) <= ((double)X * Math.sqrt(2)) / secTillHit)
		    	{
		    		++favVelocities;
		    	}
		    }
		    System.out.println(favVelocities);
		    System.out.println(totalVelocities);
		    return (double)favVelocities / (double)totalVelocities;
		  //  return .5;
		}
	}
