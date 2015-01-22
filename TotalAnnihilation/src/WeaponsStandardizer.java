import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class WeaponsStandardizer extends Standardizer{
	private static final int weaponsTolerance = 4000;
	private HashSet<String> weaponWeapons = new HashSet<String>();
	
	public WeaponsStandardizer() throws FileNotFoundException
	{
		UnitFiles uf = new UnitFiles();
		for (int i = 0; i < uf.weapons.size(); ++i)
		{
			String weapon = FileParsing.getTag(FileEditer.getLines(uf.weapons.get(i).fileOnDisk), "weapon1");
			if (weapon == null)
			{
				throw new RuntimeException("no weapon found");
			}
			weaponWeapons.add(weapon);
		}
	}
	
	@Override
	protected void postProcess()
	{
		if (weaponWeapons.size() != 0)
		{
			System.out.println("Weapons not updated: ");
			Iterator<String> iter = weaponWeapons.iterator();
			while (iter.hasNext())
			{
				System.out.println(iter.next());
			}
		}
	}

	@Override
	protected String processLine(String line1, String line2, int i) {
		if (line2.startsWith("tolerance"))
		{
			if (weaponWeapons.remove(name.toLowerCase().trim()))
			{
				line1 = "\ttolerance=" + weaponsTolerance + ";";
			}
		}
		return line1;
	}

}
