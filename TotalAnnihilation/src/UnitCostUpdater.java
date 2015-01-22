import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class UnitCostUpdater {
	
	public static final int dMaxLength = 77;
	
	public static void main(String[] args) throws FileNotFoundException
	{
		UnitFiles files = new UnitFiles();
		UnitClasses classes = new UnitClasses(files);
	/*	File unitClasses = new File("C:\\Users\\Owner\\Documents\\TABA\\Rev31.gp3 - TABA Unlimited\\Weapons\\UnitClasses.txt");
		Scanner classesIn = new Scanner(unitClasses);
		String line;
		int k = 0;
		
		ArrayList<String> files.combatUnits = new ArrayList<String>();
		ArrayList<String> Exceptions = new ArrayList<String>();
		boolean exceptionsReached = false;
		boolean end = false;

		while (classesIn.hasNextLine() && !end)
		{
			line = classesIn.nextLine().trim();
			while (line.startsWith("//"))
			{
				if (line.startsWith("//exceptions"))
				{
					exceptionsReached = true;
					break;
				}
				line = classesIn.nextLine().trim();
			}
			while (exceptionsReached)
			{
				line = classesIn.nextLine().trim();
				if (line.startsWith("//all files.combatUnits"))
				{
					end = true;
					break;
				}
				if (!line.isEmpty())
				{
					files.combatUnits.remove(line);
				//	System.out.println(line);
				}
			}
			if (!exceptionsReached)
			{				
				if (!line.isEmpty())
				{
					files.combatUnits.add(line);
				//	System.out.println(line);
				}
			}
		}*/
		ArrayList<String> unitLines = new ArrayList<String>();
		ArrayList<String> newUnitLines = new ArrayList<String>();
		String line1, line2;
		int n, m = 0, maxDamage = 0;
		double modDamage = 0;
		double maxVelocity = 0;
		n = 0;
		int j = 0;
		int unitMetal = 0, unitEnergy = 0, unitBT = 0;
		String unitDescription = "";
		String Range = "", Class = "", sClass = "", Type = "";
		boolean noUpgrade = false;
		boolean overrideMaxDamage = false;
		//for nice sysout messages
		boolean updates = false;
		int nModified = 0;

		
		for (int i = 0; i < files.combatUnits.size(); ++i)
//		for (int i = 0; i < 10; ++i)
		{
			Range = "";
			Class = "";
			sClass = "";
			Type = "";
			noUpgrade = false;
			overrideMaxDamage = false;
			maxVelocity = 0;
			maxDamage = 0;
			
			File currentFile = files.combatUnits.get(i);
			Scanner currentIn = new Scanner(currentFile);
			
			while (currentIn.hasNextLine())
			{
				unitLines.add(currentIn.nextLine());
			}
			currentIn.close();
			
		//	PrintWriter unitOut = new PrintWriter(currentFile);
		//	try
			{
				for(int k = 0; k < unitLines.size(); ++k)
				{
					line1 = unitLines.get(k);
					line2 = unitLines.get(k).trim().toLowerCase();
					if (line2.startsWith("maxdamage"))
					{
						maxDamage = FileParsing.parseInt(line2);
						modDamage = maxDamage;
					/*	if (modDamage < 500)
							Class = "Very Light ";
						else if (modDamage < 1000)
							Class = "Light ";
						else if (modDamage < 2000)
							Class = "Medium ";
						else if (modDamage < 4000)
							Class = "Heavy ";
						else if (modDamage < 8000)
							Class = "Very Heavy ";
						else if (modDamage < 16000)
							Class = "Extremely Heavy ";
						else if (modDamage < 32000)
							Class = "Ultra Heavy ";
						else if (modDamage < 64000)
							Class = "Krogoth Unit ";*/
//						if (classes.VeryLightUnits.contains(UnitClasses.unitFileToString(currentFile)))
//							Class = "Very Light ";
//						else if (classes.LightUnits.contains(UnitClasses.unitFileToString(currentFile)))
//							Class = "Light ";
//						else if (classes.MediumUnits.contains(UnitClasses.unitFileToString(currentFile)))
//							Class = "Medium ";
//						else if (classes.HeavyUnits.contains(UnitClasses.unitFileToString(currentFile)))
//							Class = "Heavy ";
//						else if (classes.VeryHeavyUnits.contains(UnitClasses.unitFileToString(currentFile)))
//							Class = "Very Heavy ";
//						else if (classes.ExtremelyHeavyUnits.contains(UnitClasses.unitFileToString(currentFile)))
//							Class = "Extremely Heavy ";
//						else if (classes.UltraHeavyUnits.contains(UnitClasses.unitFileToString(currentFile)))
//							Class = "Ultra Heavy ";
//						else if (classes.Krogoth.contains(UnitClasses.unitFileToString(currentFile)))
//							Class = "Krogoth Unit ";
						Class = classes.getClass(currentFile);
						sClass = classes.getsClass(currentFile);
						
						
					}
					if (line2.startsWith("//override maxdamage=1;"))	//without this line, program will assume DamageModifier is only for an upgrade, this lets it know it is part of the units overall armor and should be calculated as MaxDamage
					{
						noUpgrade = true;
						overrideMaxDamage = true;
					}
					if (line2.startsWith("//override maxdamage=2;"))	//this setting ignores DamageModifier, for units that change armor state
					{
						noUpgrade = true;
						overrideMaxDamage = false;
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
				}
				unitMetal = MetalCalculation(maxDamage, maxVelocity);
				unitEnergy = EnergyCalculation(maxDamage, maxVelocity);
				unitBT = BTCalculation(maxDamage, maxVelocity);
				
				for(j = 0; j < unitLines.size(); ++j)
				{
					line1 = unitLines.get(j);
					line2 = unitLines.get(j).trim().toLowerCase();
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
							if (maxVelocity == 0)
								line1 = "\tDescription=" + Range + Class + Type + ", A=" + maxDamage + ", UpA=" + (int)modDamage + ";";
							else
								line1 = "\tDescription=" + Range + Class + Type + ", A=" + maxDamage + ", UpA=" + (int)modDamage + ", V=" + maxVelocity + ";";
						}
						else
						{
							if (maxVelocity == 0)
								line1 = "\tDescription=" + Range + Class + Type + ", A=" + maxDamage + ";";
							else
								line1 = "\tDescription=" + Range + Class + Type + ", A=" + maxDamage + ", V=" + maxVelocity + ";";
						}
						if (line1.trim().length() >= dMaxLength)
						{
							Class = sClass;
						}
						if ((int)modDamage != maxDamage)
						{
							if (maxVelocity == 0)
								line1 = "\tDescription=" + Range + Class + Type + ", A=" + maxDamage + ", UpA=" + (int)modDamage + ";";
							else
								line1 = "\tDescription=" + Range + Class + Type + ", A=" + maxDamage + ", UpA=" + (int)modDamage + ", V=" + maxVelocity + ";";
						}
						else
						{
							if (maxVelocity == 0)
								line1 = "\tDescription=" + Range + Class + Type + ", A=" + maxDamage + ";";
							else
								line1 = "\tDescription=" + Range + Class + Type + ", A=" + maxDamage + ", V=" + maxVelocity + ";";
						}
						line2 = line1.trim().toLowerCase();
					}
					newUnitLines.add(line1);
		//			System.out.println(line1);
				}
			}
			if (!newUnitLines.equals(unitLines))
			{
				updates = true;
				PrintWriter unitOut = new PrintWriter(currentFile);
				try
				{
					for(j = 0; j < newUnitLines.size(); ++j)
					{
						unitOut.println(newUnitLines.get(j));
					}
				}
				finally
				{
					for(; j < unitLines.size(); ++j)
					{
						unitOut.println(unitLines.get(j));
					}
					unitOut.close();
				}
				++nModified;
				System.out.println(nModified + ": Unit File " + files.combatUnits.get(i) + " Updated.");
			}
			unitLines.clear();
			newUnitLines.clear();
		}
		if (updates)
			System.out.println("Unit Files Updated.");
		if (!updates)
			System.out.println("No Unit Files Updated");
	}
	
	public static int MetalCalculation(int maxDamage, double maxVelocity)
	{
		double C, M, T;
		C = .039 * Math.pow(maxDamage, 1.2);
		M = .22 * Math.pow(maxDamage, .1) * (2 + Math.pow(maxVelocity, 1.0));
		T = .9 * M * C;
		return AnswerRounded(.6 * .65 * T);
	}
	
	public static int EnergyCalculation(int maxDamage, double maxVelocity)
	{
		double S;
		S = .22 * Math.pow(maxDamage, .1) * (.5 + Math.pow(maxVelocity, 1.4));
		return AnswerRounded(.7 * .6 * Math.pow(maxDamage, 1.09) * S);
	}
	
	public static int BTCalculation(int maxDamage, double maxVelocity)
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
}
