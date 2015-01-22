import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Changes the values of a certain regular expression by some operation in all files in specified directory.
 * 
 * Class is open to subclassers who want to change the performOperation method.
 * @author Owner
 *
 */
public abstract class ChangeValuesInDirectory {

	protected String fileDirectory;
	protected String expression;
	
	public ChangeValuesInDirectory(String fileDirectory, String expression)
	{
		this.fileDirectory = fileDirectory;
		this.expression = expression;
	}
	
	public void change() throws FileNotFoundException 
	{
		File directory = new File(fileDirectory);
		File[] files = directory.listFiles();
		Pattern pattern = Pattern.compile(expression);
		for (int i = 0; i < files.length; ++i)
		{
			ArrayList<String> lines = FileEditer.getLines(files[i]);
			ArrayList<String> updatedLines = new ArrayList<String>();
			for (int j = 0; j < lines.size(); ++j)
			{
				String line = lines.get(j);
				Matcher matcher = pattern.matcher(line);
				if (matcher.find())
				{
					Pattern p2 = Pattern.compile(matcher.group(1));
					Matcher m2 = p2.matcher(line);
					String line2 = m2.replaceFirst(performOperation(matcher.group(1)));
					updatedLines.add(line2);
				}
				else
				{
					updatedLines.add(line);
				}
			}
			FileEditer.updateFile(lines, updatedLines, files[i]);
//			Scanner in = new Scanner(files[i]);
//			String line = in.findWithinHorizon(pattern, 0);
//			while (line != null)
//			{
//				Matcher matcher = pattern.matcher(line);
//				if (matcher.find())
//				{
//					//replaces first occurrence of literal keyword needing to be changed with value of performOperation(keyword)
//					//and sends to FileEditer
//					Pattern p2 = Pattern.compile(matcher.group(1));
//					Matcher m2 = p2.matcher(line);
//					String line2 = m2.replaceFirst(performOperation(matcher.group(1)));
//					FileEditer.switchLineInFile(line, line2, files[i]);
//				}
//				line = in.findWithinHorizon(pattern, 0);
//			}
		}
	}
	
	protected abstract String performOperation(String match);
}
