import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParsing
{
	public static final String expressionRegex = "=(.*);";
	
	public static int parseInt(String line)
	{
		int n = 0, m = 0;
		for(int i = 0; i < line.length(); ++i)
		{
			if(line.charAt(i) == '=')
			{
				if (n == 0)
				{
					n = i+1;
				}
			}
			if(line.charAt(i) == ';')
			{
				if (m == 0)
				{
					m = i;
				}
			}
		}
		return Integer.parseInt(line.substring(n, m).trim());
	}
	
	public static int parseNextInt(String line)
	{
		int n = 0, m = 0;
		for(int i = 0; i < line.length(); ++i)
		{
			if(line.charAt(i) == '=')
			{
				if (n == 0)
				{
					n = i+1;
				}
				++i;
			}
			if(n != 0 && !Character.isDigit(line.charAt(i)))
			{
				if (m == 0)
				{
					m = i;
				}
			}
		}
		if (m != 0)
			return Integer.parseInt(line.substring(n, m).trim());
		else
			return Integer.parseInt(line.substring(n).trim());
	}
	
	public static double parseDouble(String line)
	{
		int n = 0, m = 0;
		for(int i = 0; i < line.length(); ++i)
		{
			if(line.charAt(i) == '=')
			{
				n = i+1;
			}
			if(line.charAt(i) == ';')
			{
				m = i;
			}
		}
		return Double.parseDouble(line.substring(n, m).trim());
	}
	
	public static String parseString(String line)
	{
		int n = 0, m = 0;
		for(int i = 0; i < line.length(); ++i)
		{
			if(line.charAt(i) == '=')
			{
				if (n == 0)
				{
					n = i+1;
				}
			}
			if(line.charAt(i) == ';')
			{
				if (m == 0)
				{
					m = i;
				}
			}
		}
		return line.substring(n, m).trim();
	}
	
	/**
	 * 
	 * @param regex  Pattern to find in oldLine. Will replace contents in ( ... ) - group(1) - with newValue
	 * @param oldLine  Previous String that needs replacing
	 * @param newValue  Value that will replace the captured group(1) in regex
	 * @return
	 */
	public static String replace(String regex, String oldLine, String newValue)
	{
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(oldLine);
		if (m.find())
		{
			return m.replaceAll(replaceGroup(regex, newValue));
		}
		else
		{
			throw new RuntimeException("No match");
		}
	}
	
	/**
	 * Replaces group(1) ( ... ) with replacement, and returns the resulting regex with replacement String
	 * @param regex  Regular expression whose parenthetical group will be literally replaced by replacement
	 * @param replacement  Replacement String
	 * @return
	 */
	public static String replaceGroup(String regex, String replacement)
	{
		return regex.replaceAll("\\(.*\\)", replacement);
	}
	
	/**
	 * Returns group(1) of regex string on line, or null if there are no matches in the given line
	 * @param regex
	 * @param line
	 * @return
	 */
	public static String parse(String regex, String line)
	{
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
		if (m.find())
		{
			String found = m.group(1);
			if (found == null)
			{
				throw new RuntimeException("No group one");
			}
			else
			{
				return found;
			}
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Replaces value in tag with newValue
	 */
	public static List<String> replaceTag(List<String> lines, String tag, String newValue)
	{
		List<String> newLines = new ArrayList<String>();
		for (int i = 0; i < lines.size(); ++i)
		{
			String line1 = lines.get(i);
			String line2 = line1.trim().toLowerCase();
			if (line2.startsWith(tag.toLowerCase().trim()))
			{
				line1 = FileParsing.replace(FileParsing.expressionRegex, line1, newValue);
			}
			newLines.add(line1);
		}
		return newLines;
	}
	
	/**
	 * 
	 * @param unitFile
	 * @param tag weaponTag, i.e. "weapon1"
	 * @return The tag value, if found, or null if not found
	 * @throws FileNotFoundException
	 */
	public static String getTag(List<String> lines, String tag) throws FileNotFoundException
	{
		for (int i = 0; i < lines.size(); ++i)
		{
			String line1 = lines.get(i);
			String line2 = line1.trim().toLowerCase();
			if (line2.startsWith(tag.toLowerCase().trim()))
			{
				return FileParsing.parseString(line2);
			}
		}
		return null;
	}
	
	/**
	 * Needs testing to see if not calling getTag for each tag and instead parsing looking for each tag could make it quicker
	 * @param lines
	 * @param tags
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static String[] getTags(List<String> lines, String[] tags) throws FileNotFoundException
	{
		String[] tagValues = new String[tags.length];
		for (int i = 0; i < tagValues.length; ++i)
		{
			tagValues[i] = getTag(lines, tags[i]);
		}
		return tagValues;
	}
	
	/**
	 * Replaces tags. Tags and Values must be the same length.
	 * @param lines
	 * @param tags
	 * @param values
	 * @return
	 */
	public static List<String> replaceTags(List<String> lines, String[] tags, String[] values)
	{
		List<String> newLines = new ArrayList<String>(lines);
		if (tags.length != values.length)
		{
			throw new RuntimeException("replaceTags called with tags and values different length");
		}
		for (int i = 0; i < tags.length; ++i)
		{
			newLines = replaceTag(newLines, tags[i], values[i]);
		}
		return newLines;
	}
}