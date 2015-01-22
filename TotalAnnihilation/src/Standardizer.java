import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class used for weapon standardization
 * @author Bryce Sandlund
 *
 */
public abstract class Standardizer {
	
	protected String name = "";
	private ArrayList<String> newWeaponLines = new ArrayList<String>();
	protected boolean changed = false;
	private int nUpdated = 1;
	
	protected void changeLine(int lineNumber, String newLine)
	{
		if (!newWeaponLines.get(lineNumber).equals(newLine))
		{
			changed = true;
			newWeaponLines.set(lineNumber, newLine);
		}
	}

	public void standardize()
	{
		try
		{
			File weaponsFile = new File(FileEditer.TABA_DIRECTORY + "Weapons\\TabaWeapons.tdf");
			ArrayList<String> oldWeaponLines = FileEditer.getLines(weaponsFile);
			for (int i = 0; i < oldWeaponLines.size(); ++i)
			{
				String line1 = oldWeaponLines.get(i);
				String line2 = line1.trim().toLowerCase();
				
				if (line2.startsWith("[") && !line2.contains("damage"))
				{
					if (changed)
					{
						System.out.println(nUpdated++ + ": " + name + " updated.");
					}
					name = FileParsing.parse("\\[(.*)\\]", line1);
					changed = false;
				}
				String newLine = processLine(line1, line2, i);
				if (!newLine.equals(line1))
				{
					changed = true;
					line1 = newLine;
				}
				newWeaponLines.add(line1);
			}
			postProcess();
			FileEditer.updateFile(oldWeaponLines, newWeaponLines, weaponsFile);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	protected void postProcess()
	{
		
	}
	
	/**
	 * Processes a line
	 * @param line1  The unmodified line
	 * @param line2  The line after .toLowerCase().trim()
	 * @param i  The line number
	 * @return  The line after processing
	 */
	protected abstract String processLine(String line1, String line2, int i);
}
