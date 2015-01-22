import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class ClassGenerator {

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
		ArrayList<String> VeryLightUnits = new ArrayList<String>();
		ArrayList<String> LightUnits = new ArrayList<String>();
		ArrayList<String> MediumUnits = new ArrayList<String>();
		ArrayList<String> HeavyUnits = new ArrayList<String>();
		ArrayList<String> VeryHeavyUnits = new ArrayList<String>();
		ArrayList<String> ExtremelyHeavyUnits = new ArrayList<String>();
		ArrayList<String> UltraHeavyUnits = new ArrayList<String>();
		ArrayList<String> Krogoth = new ArrayList<String>();
		ArrayList<String> Aircraft = new ArrayList<String>();
		ArrayList<String> Exceptions = new ArrayList<String>();
		ArrayList<String> AllUnits = new ArrayList<String>();

		int armor = 0;
		
		File units = new File("C:/Users/Owner/Documents/TABA/Rev31.gp3 - TABA Unlimited/Units/");
	    File[] listOfFiles = units.listFiles();
	    ArrayList<String> combatUnits = new ArrayList<String>();


	    String line1, line2;
	    boolean hasWeapon, unit, constr, isWeapon, isUpgrade, isPlane, isTurret, moves;
	    File currentFile;
	    Scanner currentIn;

	    for (int i = 0; i < listOfFiles.length; i++)
	    {
	    	hasWeapon = false;
	    	unit = false;
	    	constr = false;
	    	isWeapon = false;
	    	isUpgrade = false;
	    	isPlane = false;
	    	isTurret = false;
	    	moves = false;
	    	currentFile = listOfFiles[i];
			currentIn = new Scanner(currentFile);
			while (currentIn.hasNextLine())
			{
				line1 = currentIn.nextLine();
				line2 = line1.trim();
				if (line2.startsWith("//weapon=1;"))
				{
					isWeapon = true;
				}
				if (line2.startsWith("//"))
				{
					continue;
				}
			//	System.out.println(line2);
				if (line2.contains(" KBOT") || line2.contains(" TANK") || line2.contains(" SHIP"))
				{
					//System.out.println(listOfFiles[i]);
					unit = true;
				}
				if (line2.contains("CONSTR") || line2.toLowerCase().startsWith("canrepair=1;") || line2.toLowerCase().startsWith("canreclamate=1;"))
				{
					//System.out.println(listOfFiles[i]);
					constr = true;
				}
				if (line2.startsWith("weapon") || line2.startsWith("Weapon"))
				{
					//System.out.println(listOfFiles[i]);
					hasWeapon = true;
				}
				if (line2.startsWith("UnitName=ARMUP") || line2.startsWith("UnitName=CORUP"))
				{
					//System.out.println(listOfFiles[i]);
					isUpgrade = true;
				}
				if (line2.startsWith("Category=") && line2.contains(" VTOL"))
				{
					//System.out.println(listOfFiles[i]);
					isPlane = true;
				}
				if (line2.startsWith("TEDClass=FORT;"))
				{
					//System.out.println(listOfFiles[i]);
					isTurret = true;
				}
				if (line2.startsWith("MaxVelocity"))
				{
					//System.out.println(listOfFiles[i]);
					moves = true;
				}
			}
			if (constr && listOfFiles[i].getName().toUpperCase().endsWith(".FBI"))
			{
				Exceptions.add(listOfFiles[i].getName().toUpperCase().replace(".FBI", ""));
			}
			if (isPlane && listOfFiles[i].getName().toUpperCase().endsWith(".FBI"))
			{
				Aircraft.add(listOfFiles[i].getName().toUpperCase().replace(".FBI", ""));
			}
			if ((moves && !isPlane) /*&& !constr && hasWeapon*/ && !isWeapon && listOfFiles[i].getName().toUpperCase().endsWith(".FBI") && !isUpgrade)
			{
				combatUnits.add(listOfFiles[i].getName().toUpperCase().replace(".FBI", ""));
//				System.out.println(listOfFiles[i].getName());
			}
			currentIn.close();
			if (listOfFiles[i].getName().toUpperCase().endsWith(".FBI"))
			{
				AllUnits.add(listOfFiles[i].getName().toUpperCase().replace(".FBI", ""));
			}
	    }
	    int n = 0, m = 0;
	    
	    for (int i = 0; i < combatUnits.size(); i++)
	    {
	    	//System.out.println(combatUnits.get(i));
	    	
	    	currentFile = new File("C:\\Users\\Owner\\Documents\\TABA\\Rev31.gp3 - TABA Unlimited\\Units\\" + combatUnits.get(i) + ".fbi");
			currentIn = new Scanner(currentFile);
			
			while (currentIn.hasNextLine())
			{
				line1 = currentIn.nextLine().trim().toLowerCase();
				if (line1.startsWith("maxdamage"))
				{
					for(int l = 0; l < line1.length(); ++l)
					{
						if(line1.charAt(l) == '=')
						{
							n = l+1;
						}
						if(line1.charAt(l) == ';')
						{
							m = l;
						}
					}
					armor  = Integer.parseInt(line1.substring(n, m).trim());	
				}
				
				if (line1.startsWith("damagemodifier"))
				{
					for(int l = 0; l < line1.length(); ++l)
					{
						if(line1.charAt(l) == '=')
						{
							n = l+1;
						}
						if(line1.charAt(l) == ';')
						{
							m = l;
						}
					}
					armor  /= Double.parseDouble(line1.substring(n, m).trim());	
				}
			}
			
			if (armor < 500)
				VeryLightUnits.add(combatUnits.get(i));
			else if (armor < 1000)
				LightUnits.add(combatUnits.get(i));
			else if (armor < 2000)
				MediumUnits.add(combatUnits.get(i));
			else if (armor < 4000)
				HeavyUnits.add(combatUnits.get(i));
			else if (armor < 8000)
				VeryHeavyUnits.add(combatUnits.get(i));
			else if (armor < 16000)
				ExtremelyHeavyUnits.add(combatUnits.get(i));
			else if (armor < 32000)
				UltraHeavyUnits.add(combatUnits.get(i));
			else if (armor < 64000)
				Krogoth.add(combatUnits.get(i));
			
			currentIn.close();
	    }
	/*    String[] classes = {"//very light units", "//light"};
	    for (int i = 0; i < classes.length; ++i)
	    {
	    	for (int j = 0; j < VeryLightUnits.size(); ++j)
		    {
		    	System.out.println(VeryLightUnits.get(j));
		    }
	    }*/
	    PrintWriter unitClassOut = new PrintWriter("C:/Users/Owner/Documents/TABA/Rev31.gp3 - TABA Unlimited/Weapons/UnitClasses.txt");
	    
	    
	    unitClassOut.println("//very light units");
	    for (int i = 0; i < VeryLightUnits.size(); ++i)
	    {
	    	unitClassOut.println(VeryLightUnits.get(i));
	    }
	    unitClassOut.println("//light units");
	    for (int i = 0; i < LightUnits.size(); ++i)
	    {
	    	unitClassOut.println(LightUnits.get(i));
	    }
	    unitClassOut.println("//medium units");
	    for (int i = 0; i < MediumUnits.size(); ++i)
	    {
	    	unitClassOut.println(MediumUnits.get(i));
	    }
	    unitClassOut.println("//heavy units");
	    for (int i = 0; i < HeavyUnits.size(); ++i)
	    {
	    	unitClassOut.println(HeavyUnits.get(i));
	    }
	    unitClassOut.println("//very heavy units");
	    for (int i = 0; i < VeryHeavyUnits.size(); ++i)
	    {
	    	unitClassOut.println(VeryHeavyUnits.get(i));
	    }
	    unitClassOut.println("//extremely heavy units");
	    for (int i = 0; i < ExtremelyHeavyUnits.size(); ++i)
	    {
	    	unitClassOut.println(ExtremelyHeavyUnits.get(i));
	    }
	    unitClassOut.println("//ultra heavy units");
	    for (int i = 0; i < UltraHeavyUnits.size(); ++i)
	    {
	    	unitClassOut.println(UltraHeavyUnits.get(i));
	    }
	    unitClassOut.println("//krogoth units");
	    for (int i = 0; i < Krogoth.size(); ++i)
	    {
	    	unitClassOut.println(Krogoth.get(i));
	    }
	    unitClassOut.println("//aircraft");
	    for (int i = 0; i < Aircraft.size(); ++i)
	    {
	    	unitClassOut.println(Aircraft.get(i));
	    }
	    unitClassOut.println("//exceptions");
	    for (int i = 0; i < Exceptions.size(); ++i)
	    {
	    	unitClassOut.println(Exceptions.get(i));
	    }
	    unitClassOut.println("//all units");
	    for (int i = 0; i < AllUnits.size(); ++i)
	    {
	    	unitClassOut.println(AllUnits.get(i));
	    }
	    
	    unitClassOut.close();
	    System.out.println("UnitClasses.txt Updated.");
	}
}
