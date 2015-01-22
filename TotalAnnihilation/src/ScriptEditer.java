
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Need to add while(!active) in AimPrimary and eliminate StartMoving and StopMoving
 * @author Owner
 *
 */
public class ScriptEditer extends AbstractEditer{
	
	public ScriptEditer(String input, String output) throws IOException
	{
		super(input, output, "scripts/", ".bos");
	}
	
	/*public void convertToUnitTurretScript() throws FileNotFoundException
	{
			PrintWriter out = new PrintWriter(super.inputFile);
			boolean doNotChange = false;
			boolean staticVar = false;
			for (int i = 0; i < super.inputLines.size(); ++i)
			{
				String line1 = super.inputLines.get(i);
				String line2 = line1.toLowerCase().trim();
				
				
				if (line2.startsWith("piece") && !line2.startsWith("piecenum"))
				{
					if (line2.contains("attachpt"))
					{
						doNotChange = true;
						System.out.println("Script File suspected to already be updated.");
					}
				}
				if (!doNotChange)
				{
					if (line2.startsWith("piece") && !line2.startsWith("piecenum"))
					{
						line1 = line1.substring(0, line1.length() - 1) + ", attachpt;";
					}
					if (line2.startsWith("static-var"))
					{
						staticVar = true;
						line1 = line1.substring(0, line1.length() - 1) + ", min_id_of_team, max_id_of_team, unitteam, unitsperside;";
						out.println(line1);
						while (!line1.isEmpty())
						{
							++i;
							line1 = super.inputLines.get(i);
							line2 = line1.toLowerCase().trim();
							out.println(line1);
						}
						line1 = "#include \"STAT_TURRET_LOADING.bos\"\n";
					}
					if (line2.startsWith("create"))
					{
						if (!staticVar)
						{
							out.println("static-var min_id_of_team, max_id_of_team, unitteam, unitsperside;");
							out.println("");
							out.println("#include \"STAT_TURRET_LOADING.bos\"\n");
							out.println("");
						}
						out.println(line1);
						++i;
						line1 = super.inputLines.get(i);
						line2 = line1.toLowerCase().trim();
						out.println(line1);
						line1 = "\tstart-script Initialization();";
					/*	int bracketCount = 0;
						boolean initWritten = false;
						while (!initWritten)
						{
							++i;
							line1 = super.inputLines.get(i);
							line2 = line1.toLowerCase().trim();
							if (line2.startsWith("{"))
							{
								++bracketCount;
							}
							if (line2.startsWith("}"))
							{
								--bracketCount;
							}
							if (bracketCount == 0)
							{
								line1 = "\tINITIALIZATION;";
								initWritten = true;
							}
							out.println(line1);
						}
						line1 = "}";*/
					/*}
					if (line2.startsWith("aimprimary") || line2.startsWith("fireprimary") || line2.startsWith("queryprimary") || line2.startsWith("aimfromprimary") || line2.startsWith("aimsecondary") || line2.startsWith("firesecondary") || line2.startsWith("querysecondary") || line2.startsWith("aimfromsecondary") || line2.startsWith("aimtertiary") || line2.startsWith("firetertiary") || line2.startsWith("querytertiary") || line2.startsWith("aimfromtertiary"))
					{
						++i;
						line1 = super.inputLines.get(i);
						line2 = line1.toLowerCase().trim();
						int bracketCount = 0;
						if (line2.startsWith("{"))
						{
							++bracketCount;
						}
						if (line2.startsWith("}"))
						{
							--bracketCount;
						}
						while (bracketCount != 0)
						{
							++i;
							line1 = super.inputLines.get(i);
							line2 = line1.toLowerCase().trim();
							if (line2.startsWith("{"))
							{
								++bracketCount;
							}
							if (line2.startsWith("}"))
							{
								--bracketCount;
							}
						}
						++i;
						line1 = super.inputLines.get(i);
						line2 = line1.toLowerCase().trim();
					}
				}
				out.println(line1);
			}
			if (!staticVar)
			{
//				System.out.println("WARNING, static-var not found in file " + super.inputFile + ".");
			}
			out.close();
	}*/
	
	public void copyToWeaponScript() throws IOException
	{
		List<String> newOutputLines = addInInclude("#include \"TURRET.bos\"", inputLines);
		newOutputLines = addInMethod("\tstart-script Detect();", "Create", newOutputLines, false);
		// the \n are ignored for some reason in the line below...
		newOutputLines = addInMethod("\twhile (!active)\t{\tsleep 200;\t}", "AimPrimary", newOutputLines, true);
		newOutputLines = FileEditer.removeLine(newOutputLines, "start-script MotionControl();");
		newOutputLines = removeMethods(new String[]{"StartMoving", "StopMoving"}, newOutputLines);
		FileEditer.updateFile(outputLines, newOutputLines, outputFile);
	}
	
	public static List<String> addInInclude(String includeLine, List<String> scriptLines)
	{
		boolean firstDefine = true;
		ArrayList<String> outputLines = new ArrayList<String>();
		for (int i = 0; i < scriptLines.size(); ++i)
		{
			String line1 = scriptLines.get(i);
			String line2 = line1.trim().toLowerCase();
			Pattern p = Pattern.compile("#define\\s*sig_aim");
			Matcher m = p.matcher(line2);
			if (m.find() && firstDefine)
			{
				firstDefine = false;
				outputLines.add(line1);
				outputLines.add("");
				line1 = includeLine;
			}
			outputLines.add(line1);
		}
		return outputLines;
	}
	
	/**
	 * Is buggy for adding at end, need to do a bracket count thing or else it will add it in an if in Create() - I think it should work now
	 * @param lineToAdd
	 * @param scriptLines
	 * @param start  Add at the start or end of Create()
	 * @return
	 */
	public static List<String> addInMethod(String lineToAdd, String methodName, List<String> scriptLines, boolean start)
	{
		boolean inMethod = false;
		ArrayList<String> outputLines = new ArrayList<String>();
		int bracketCount = 0;
		for (int i = 0; i < scriptLines.size(); ++i)
		{
			String line1 = scriptLines.get(i);
			String line2 = line1.trim().toLowerCase();
			if (line2.startsWith("{"))
			{
				++bracketCount;
				if (inMethod && start && bracketCount == 1)
				{
					outputLines.add("{");
					line1 = lineToAdd;
				}
			}
			else if (line2.startsWith("}"))
			{
				--bracketCount;
				if (bracketCount == 0 && inMethod)
				{
					if (!start)
					{
						outputLines.add(lineToAdd);
					}
					inMethod = false;
				}
			}
			if (line2.startsWith(methodName.toLowerCase().trim()))
			{
				inMethod = true;
			}
			outputLines.add(line1);
		}
		return outputLines;
	}
	
	/**
	 * 
	 * @param methodName - not case sensitive
	 * @param scriptLines
	 * @return
	 */
	public static List<String> removeMethod(String methodName, List<String> scriptLines)
	{
		List<String> newLines = new ArrayList<String>();
		int bracketCount = 0;
		boolean skip = false, turnOn = false;
		for (int i = 0; i < scriptLines.size(); ++i)
		{
			String line1 = scriptLines.get(i);
			String line2 = line1.toLowerCase().trim();
			if (line2.startsWith("{"))
			{
				++bracketCount;
			}
			else if (line2.startsWith("}"))
			{
				--bracketCount;
				if (skip && bracketCount == 0)
				{
					turnOn = true;
				}
			}
			else if (line2.startsWith(methodName.toLowerCase().trim()))
			{
				skip = true;
			}
			if (!skip)
			{
				newLines.add(line1);
			}
			if (turnOn)
			{
				skip = false;
				turnOn = false;
			}
		}
		return newLines;
	}
	
	public static List<String> removeMethods(String[] methods, List<String> scriptLines)
	{
		List<String> newLines = new ArrayList<String>(scriptLines);
		for (int i = 0; i < methods.length; ++i)
		{
			newLines = removeMethod(methods[i], newLines);
		}
		return newLines;
	}
}
