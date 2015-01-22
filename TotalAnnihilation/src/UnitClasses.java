import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class UnitClasses {
	
	protected List<Unit>[] unitClasses = (ArrayList<Unit>[]) new ArrayList[Unit.unitClasses.length];
	public UnitFiles files;
	
	public UnitClasses(UnitFiles givenFiles)
	{
		files = givenFiles;
		for (int i = 0; i < unitClasses.length; ++i)
		{
			unitClasses[i] = new ArrayList<Unit>();
		}
		getUnitClasses();
	}
	
	public UnitClasses() throws FileNotFoundException
	{
		files = new UnitFiles();
		for (int i = 0; i < unitClasses.length; ++i)
		{
			unitClasses[i] = new ArrayList<Unit>();
		}
		getUnitClasses();
	}
	
	/**
	 * Places units into easily accessible groups. This makes SpecialDamages calculations much more efficient.
	 */
	private void getUnitClasses()
	{
		for (int i = 0; i < files.allUnits.size(); i++)
	    {
			unitClasses[files.allUnits.get(i).getClassOrdinal()].add(files.allUnits.get(i));
	    }
	}
}
