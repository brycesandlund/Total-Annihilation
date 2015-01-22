import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * Used for weapon special damages. Does not perform any actual updates on disk, use TabaWeapons for that purpose.
 * @author Owner
 *
 */
public class SpecialDamages {
	public UnitClasses classes;

	/**
	 * First dimension is this unit's weapon class, and second dimension is its damage to all other unit's armor classes
	 */
	public static final int[][] specialDamages = new int[][]
	{
		{130, 115, 100, 85, 70, 55, 40, 25},
		{115, 130, 115, 100, 85, 70, 55, 40},
		{100, 115, 130, 115, 100, 85, 70, 55},
		{85, 100, 115, 130, 115, 100, 85, 60},
		{70, 85, 100, 115, 130, 115, 100, 85},
		{55, 70, 85, 100, 115, 130, 115, 100},
		{40, 55, 70, 85, 100, 115, 130, 115},
		{25, 40, 55, 70, 85, 100, 115, 130}
	};
	
	public SpecialDamages() throws FileNotFoundException
	{
		classes = new UnitClasses();
	}
	
	public SpecialDamages(UnitClasses existing)
	{
		classes = existing;
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		TabaWeapons tabaW = new TabaWeapons();
		tabaW.updateAllWeapons();
	}
	
	/**
	 * Returns the new special damage lines given the Weapon input
	 */
	public List<String> getSpecialDamages(TabaWeapons.Weapon in)
	{
		List<String> newLines = new ArrayList<String>();
		String header = "\t\t//WeaponClass = ";
		Set<String> specialNames = new HashSet<String>();
		if (in.special)
		{
			newLines.add("\t\t//Special");
			List<String> prev = in.getSpecialDamageLines();
			specialNames = new HashSet<String>();
			for (int i = 0; i < prev.size() && !prev.get(i).trim().toLowerCase().startsWith("//weapon") && !prev.get(i).trim().toLowerCase().startsWith("//all"); ++i)	//parses until it finds header, if header is changed to not start with //weapon
			{																				//this line will also have to be changed
				if (!prev.get(i).trim().startsWith("//") && !prev.get(i).trim().isEmpty())
				{
					String unitName = FileParsing.parse(("(.+)=\\d+;"), prev.get(i).trim());
					if (unitName == null)
					{
						throw new RuntimeException("No match found");
					}
					specialNames.add(unitName);
					newLines.add(prev.get(i));
				}
			}
		}
		if (in.noDamage)
		{
			newLines.add("\t\t//All Units - 0%");
			for (int i = 0; i < classes.files.allUnits.size(); ++i)
			{
				if (!specialNames.contains(classes.files.allUnits.get(i).getUnitName()))
				{
					newLines.add("\t\t" + classes.files.allUnits.get(i).getUnitName() + "=0;");
				}
			}
		}
		else
		{
			newLines.add(header + in.getWeaponClass() + ";");
			for (int i = 0; i < TabaWeapons.weaponClasses.length; ++i)
			{
				newLines.add("\t\t//" + Unit.unitClasses[i] + " - " + specialDamages[in.getClassOrdinal()][i] + "%");
				int thisDamage = (int)Math.round(in.damage * specialDamages[in.getClassOrdinal()][i] / 100.0);
				int thisAA = (int)Math.round(thisDamage * in.AA);
				for (int j = 0; j < classes.unitClasses[i].size(); ++j)
				{
					Unit cur = classes.unitClasses[i].get(j);
					if (!specialNames.contains(cur.getUnitName()))
					{
						newLines.add("\t\t" + cur.getUnitName() + "=" + (cur.isPlane ? thisAA : thisDamage) + ";");
					}
				}
			}
		}
		return newLines;
	}
}
