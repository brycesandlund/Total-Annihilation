import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Owner
 *
 */
public class UnitEditer extends AbstractEditer{
	private String name, weaponName;
	
	/**
	 * Used to create weapon
	 * @param input
	 * @param output
	 * @param model
	 * @param name
	 * @throws IOException 
	 */
	public UnitEditer(String input, String output, String model, String name) throws IOException
	{
		super(input, output, model, "units/", ".fbi");
		this.name = name;
		weaponName = FileParsing.getTag(inputLines, "weapon1");
	}
	
	/**
	 * Used to create Tank
	 * @param input
	 * @param range
	 * @throws IOException 
	 */
	public UnitEditer(String input, char range) throws IOException
	{
		super(input, input, "units/", ".fbi");
		if (Character.toUpperCase(range) == 'C')
		{
			weaponName = "NULL_WEAPON";
		}
		else if (Character.toUpperCase(range) == 'I')
		{
			weaponName = "NULL_WEAPON_INT";
		}
		else if (Character.toUpperCase(range) == 'L')
		{
			weaponName = "NULL_WEAPON_FAR";
		}
		else
		{
			throw new RuntimeException("Character not recognized.");
		}
	}
	
	public void copyToWeaponFile() throws IOException
	{
		List<String> newOutputLines = new ArrayList<String>(modelLines);
		//not that efficient. More efficient is possible, but I don't think necessary.
		if (weaponName != null)
		{
			newOutputLines = FileParsing.replaceTag(newOutputLines, "weapon1", weaponName);
		}
		newOutputLines = FileParsing.replaceTag(newOutputLines, "unitname", output);
		newOutputLines = FileParsing.replaceTag(newOutputLines, "objectname", output);
		newOutputLines = FileParsing.replaceTag(newOutputLines, "name", name);
		
		FileEditer.updateFile(outputLines, newOutputLines, outputFile);
	}
	
	public void convertToUnitFile() throws FileNotFoundException
	{
//			PrintWriter out = new PrintWriter(super.inputFile);
//			for (int i = 0; i < super.inputLines.size(); ++i)
//			{
//				String line1 = super.inputLines.get(i);
//				String line2 = line1.toLowerCase().trim();
//				
//				if (line2.startsWith("weapon"))
//				{
//					line1 = "\tWeapon1=" + weaponName + ";";
//				}
//				
//				out.println(line1);
//			}	
//			out.close();
	}
	
//	public void convertToUnitTurretFile() throws FileNotFoundException	//add canattack=0;
//	{
//			PrintWriter out = new PrintWriter(super.inputFile);
//			boolean workerTimeFound = false;
//			boolean isAirBase = false;
//			int yardX = 0, yardY = 0;
//			for (int i = 0; i < super.inputLines.size(); ++i)
//			{
//				String line1 = super.inputLines.get(i);
//				String line2 = line1.toLowerCase().trim();
//				if (line2.startsWith("weapon") || line2.startsWith("canattack"))
//				{
//					++i;
//					line1 = super.inputLines.get(i);
//					line2 = line1.toLowerCase().trim();
//				}
//				if (line2.startsWith("workertime"))
//				{
//					line1 = "\tWorkerTime=120;";
//					workerTimeFound = true;
//				}
//				if (line2.startsWith("isairbase"))
//				{
//					isAirBase = true;
//				}
//				if (line2.startsWith("}") && !isAirBase)
//				{
//					out.println("\tisairbase=1;");
//				}
//				if (line2.startsWith("footprintx"))
//				{
//					yardX = FileParsing.parseInt(line2);
////					System.out.println("yardX = " + yardX);
//				}
//				if (line2.startsWith("footprintz"))
//				{
//					yardY = FileParsing.parseInt(line2);
////					System.out.println("yardY = " + yardY);
//				}
//				if (line2.startsWith("yardmap"))
//				{
//					line1 = "\tYardMap=";
//					int x1 = -1, x2 = -1, y1 = -1, y2 = -1;
//					if (yardX % 2 == 1)
//					{
//						x1 = yardX / 2;
//					}
//					else
//					{
//						x1 = yardX / 2;
//						x2 = yardX / 2 - 1;
//					}
//					if (yardY % 2 == 1)
//					{
//						y1 = yardY / 2;
//					}
//					else
//					{
//						y1 = yardY / 2;
//						y2 = yardY / 2 - 1;
//					}
//					for (int j = 0; j < yardY; ++j)
//					{
//						for (int k = 0; k < yardX; ++k)
//						{
//							if ((k == x1 || k == x2) && (j == y1 || j == y2))
//							{
//								line1 = line1 + "c";
//							}
//							else
//							{
//								line1 = line1 + "o";
//							}
//						}
//						if (j != yardY - 1)
//							line1 = line1 + " ";
//					}
//					line1 = line1 + ";";
//				/*	if (yardX == 3 && yardY == 3)
//					{
//						line1 = "\tYardMap=ooo oco ooo";
//					}
//					if (yardX == 3 && yardY == 4)
//					{
//						line1 = "\tYardMap=ooo oco ooo";
//					}
//					for (int j = 0; j < yardY; ++j)
//					{
//						for (int k = 0; k < yardX; ++k)
//						{
//							if (yardX == 2 || yardX == 1)
//							{
//								line1 = line1 + "c";
//							}
//							else
//							{
//								if (k == 0 || k == yardX
//								{
//									line1 = line1 + "o";
//								}
//							}
//						}
//					}*/
//					
//				}
//				
//				out.println(line1);
//			}	
//			if (!workerTimeFound)
//			{
//				out.println("\tWorkerTime=120");
//			}
//			out.close();
//	}
}
