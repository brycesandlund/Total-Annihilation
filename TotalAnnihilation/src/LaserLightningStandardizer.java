import java.util.HashMap;


public class LaserLightningStandardizer extends Standardizer{
	
	private HashMap<String, LaserLightning> map = new HashMap<String, LaserLightning>();
	private int velocityLine = -1;
	private int toleranceLine = -1;
	private int AOEline = -1;
	private int color = -1;
	private int color2 = -1;
	private String explosionart = "";
	private String explosiongaf = "";
	private int rendertype = -1;

	public void addLaserLightning(LaserLightning laserLightning)
	{
		if (map.get(laserLightning.color + "" + laserLightning.color2 + laserLightning.explosionart + laserLightning.explosiongaf + laserLightning.rendertype) != null)
		{
			throw new RuntimeException("Duplicate color assignment");
		}
		map.put(laserLightning.color + "" + laserLightning.color2 + laserLightning.explosionart + laserLightning.explosiongaf + laserLightning.rendertype, laserLightning);
	}
	
	@Override
	protected String processLine(String line1, String line2, int i) {
		if (line2.startsWith("explosiongaf"))
		{
			explosiongaf = FileParsing.parseString(line2);
		}
		else if (line2.startsWith("explosionart"))
		{
			explosionart = FileParsing.parseString(line2);
		}
		else if (line2.startsWith("weaponvelocity"))
		{
			velocityLine = i;
		}
		else if (line2.startsWith("areaofeffect"))
		{
			AOEline = i;
		}
		else if (line2.startsWith("color") && !line2.startsWith("color2"))
		{
			color = FileParsing.parseInt(line2);
		}
		else if (line2.startsWith("color2"))
		{
			color2 = FileParsing.parseInt(line2);
		}
		else if (line2.startsWith("rendertype"))
		{
			rendertype = FileParsing.parseInt(line2);
		}
		else if (line2.startsWith("default"))
		{
			if (color != -1 && color2 != -1)
			{
				LaserLightning cur = map.get(color + "" + color2 + explosionart + explosiongaf + rendertype);
				if (cur != null)
				{
					String newLine = "\t\tdefault=" + cur.damage + ";";
					line1 = newLine;
					if (cur.AOE != -1)
					{
						if (AOEline == -1)
						{
							throw new RuntimeException();
						}
						changeLine(AOEline, "\tareaofeffect=" + cur.AOE + ";");
					}
					if (velocityLine == -1)
					{
						throw new RuntimeException();
					}
					changeLine(velocityLine, "\tweaponvelocity=" + cur.velocity + ";");
				}
			}
			velocityLine = -1;
			toleranceLine = -1;
			AOEline = -1;
			color = -1;
			color2 = -1;
			explosionart = "";
			explosiongaf = "";
			rendertype = -1;
		}
		return line1;
	}

}
