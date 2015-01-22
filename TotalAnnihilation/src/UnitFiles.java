import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Reads Unit files into groups for access based on group, rather than from the Unit itself.
 * @author Bryce Sandlund
 *
 */
public class UnitFiles {

	protected File[] listOfFiles;
	protected List<Unit> weapons = new ArrayList<Unit>();
	protected List<Unit> weaponsAndTurrets = new ArrayList<Unit>();
	protected List<Unit> spdUnits = new ArrayList<Unit>();
	protected List<Unit> aircraft = new ArrayList<Unit>();
	protected List<Unit> allUnits = new ArrayList<Unit>();
	protected List<Unit> exceptions = new ArrayList<Unit>();
	protected List<Unit> combatUnits = new ArrayList<Unit>();
	protected List<Unit> combatFrauds = new ArrayList<Unit>();
	protected List<Unit> workingUnit = new ArrayList<Unit>();
	protected List<Unit> turrets = new ArrayList<Unit>();
	protected List<Unit> movingNotPlane = new ArrayList<Unit>();
	protected List<Unit> automaticCostUnit = new ArrayList<Unit>();
	protected TabaWeapons tabaWeapons;
	
	/**
	 * If you do not need reference to weapons, you need not supply a TabaWeapons type. Keep in mind this will make any weapon operations on more than one
	 * unit very inefficient. Actually nevermind, since I made it a static variable in Unit
	 * @throws FileNotFoundException
	 */
	public UnitFiles() throws FileNotFoundException
	{
		File units = new File(FileEditer.TABA_DIRECTORY + "Units");
	    listOfFiles = units.listFiles();
	    getUnitFiles();
	}
	
	public UnitFiles(TabaWeapons inWeapons) throws FileNotFoundException
	{
		tabaWeapons = inWeapons;
		File units = new File(FileEditer.TABA_DIRECTORY + "Units");
	    listOfFiles = units.listFiles();
	    getUnitFiles();
	}
	
	/**
	 * Places units into more complex groups
	 * @throws FileNotFoundException
	 */
	private void getUnitFiles() throws FileNotFoundException
	{
	    File currentFile;

	    for (int i = 0; i < listOfFiles.length; i++)
	    {
	    	currentFile = listOfFiles[i];
	    	if (!currentFile.toString().toUpperCase().endsWith(".FBI"))
	    	{
	    		continue;
	    	}
	    	Unit unitFile;
	    	if (tabaWeapons != null)
	    	{
	    		unitFile = new Unit(currentFile, tabaWeapons);
	    	}
	    	else
	    	{
	    		unitFile = new Unit(currentFile);
	    	}
	    	allUnits.add(unitFile);
			if (unitFile.constr)
			{
				exceptions.add(unitFile);
			}
			if (unitFile.isPlane)
			{
				aircraft.add(unitFile);
			}
			if ((((unitFile.moves || unitFile.isWorkingUnit)  && unitFile.hasWeapon) || unitFile.isTurret) && !unitFile.isWeapon && !unitFile.isUpgrade)
			{				
				spdUnits.add(unitFile);
			}
			if (unitFile.isWeapon)
			{
				weapons.add(unitFile);
				weaponsAndTurrets.add(unitFile);
			}
			if (((!unitFile.constr && (unitFile.moves || unitFile.isWorkingUnit)  && unitFile.hasWeapon) || unitFile.isTurret) && !unitFile.isWeapon && !unitFile.isUpgrade)
			{
				combatUnits.add(unitFile);
			}
			if (!unitFile.constr && unitFile.moves && !unitFile.isWeapon && !unitFile.isUpgrade)
			{
				combatFrauds.add(unitFile);
				for (int j = 0; j < combatUnits.size(); ++j)
				{
					combatFrauds.remove(combatUnits.get(j));
				}
			}
			if (unitFile.isWorkingUnit)
			{
				workingUnit.add(unitFile);
			}
			if (unitFile.isTurret)
			{
				turrets.add(unitFile);
				weaponsAndTurrets.add(unitFile);
			}
			if (unitFile.moves && !unitFile.isPlane)
			{
				movingNotPlane.add(unitFile);
			}
			if (unitFile.isWeapon || unitFile.isWorkingUnit || unitFile.isTurret)
			{
				automaticCostUnit.add(unitFile);
			}
	    }
	}
}
