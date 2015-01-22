import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Takes the weapons file, and changes the damage and area of effect of weapons with the following explosions
 * to these standardized values. Run SpecialDamages, UnitCostUpdater, and WeaponCostUpdater afterwards.
 * @author Bryce Sandlund
 *
 */
public class ExplosionStandardizer extends Standardizer{
	
	private HashMap<String, Explosion> explosionMap = new HashMap<String, Explosion>();
	
	/**
	 * This was really only necessary the first time, but I'll keep it in anyway
	 */
	private HashSet<Integer> AOElineUsed = new HashSet<Integer>();
	private String explosiongaf = "";
	private String explosionart = "";
	private int AOEline = -1;
	
	public void addExplosion(Explosion explo)
	{
		if (explosionMap.get(explo.getExplosiongaf() + explo.getExplosionart()) != null)
		{
			throw new RuntimeException("Duplicate art/ gaf assignment");
		}
		explosionMap.put(explo.getExplosiongaf() + explo.getExplosionart(), explo);
	}

	@Override
	protected String processLine(String line1, String line2, int i) 
	{
		if (line2.startsWith("explosiongaf"))
		{
			explosiongaf = FileParsing.parseString(line2);
		}
		else if (line2.startsWith("explosionart"))
		{
			explosionart = FileParsing.parseString(line2);
		}
		else if (line2.startsWith("areaofeffect"))
		{
			AOEline = i;
		}
		else if (line2.startsWith("default"))
		{
			if (explosiongaf.isEmpty() || explosionart.isEmpty())
			{
				if (name.isEmpty())
				{
					System.out.println("Warning, empty explosion parts at line " + i);
				}
				else
				{
//					System.out.println("Warning, empty explosion parts in " + name);
				}
			}
			else
			{
				Explosion cur = explosionMap.get(explosiongaf + explosionart);
				if (AOElineUsed.contains(AOEline))
				{
					throw new RuntimeException("AOE line used twice");
				}
				AOElineUsed.add(AOEline);
				if (cur != null)
				{
					String newLine = "\t\tdefault=" + cur.getDamage() + ";";
					line1 = newLine;
					changeLine(AOEline, "\tareaofeffect=" + cur.getAOE() + ";");
				}
			}
			explosionart = "";
			explosiongaf = "";
			AOEline = 0;
			changed = false;
		}
		return line1;
	}
}
