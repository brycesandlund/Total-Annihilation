import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Download{
	private String input, output;
	ArrayList<AbstractEditer> files = new ArrayList<AbstractEditer>();
	ArrayList<String> outputLines = new ArrayList<String>();
	int buttonG = -1;
	
	
	public Download(String input, String output) throws IOException
	{
		files.add(new AbstractEditer(input, output, "download/", ".tdf"));
//		File currentfile = new File(FileEditer.TABA_DIRECTORY + "download/" + input + ".tdf");
		File currentfile = new File(FileEditer.TABA_DIRECTORY + "download/" + input + "2.tdf");
		for (int i = 2; currentfile.exists(); ++i)
		{
			files.add(new AbstractEditer(input, output, "download/", i + ".tdf"));
//			currentfile = new File(FileEditer.TABA_DIRECTORY + "download/" + input + (i + 1) + ".tdf");
			currentfile = new File(FileEditer.TABA_DIRECTORY + "download/" + input + (i + 1) + ".tdf");;
		}
		this.input = input;
		this.output = output;		
	}
	
	private void getOutputLines() throws FileNotFoundException
	{
//		try
//		{
//			Scanner in = new Scanner(files.get(0).outputFile);
//			while (in.hasNextLine())
//			{
//				outputLines.add(in.nextLine());
//			}
//		}
//		catch (FileNotFoundException e)
//		{
//			e.printStackTrace();
//		}
		outputLines = FileEditer.getLines(files.get(0).outputFile);
	}
	
	public void setButton(int button)
	{
		buttonG = button;
	}
	
	public void findButton() throws FileNotFoundException
	{
		getOutputLines();
		for (int i = 0; i < outputLines.size(); ++i)
		{
			if (outputLines.get(i).toUpperCase().trim().startsWith("BUTTON"))
			{
				buttonG = FileParsing.parseInt(outputLines.get(i));
			//	System.out.println("Button= " + buttonG);
			}
		}
	}
	
	public void copyDownloads() throws FileNotFoundException
	{
		for (int i = 0; i < files.size(); ++i)
		{
			copyDownloadsP(files.get(i).outputFile, files.get(i).inputLines);
		}
	}
	
	private void copyDownloadsP(File outputFile, List<String> lines) throws FileNotFoundException
	{
		ArrayList<String> newOutputLines = new ArrayList<String>();
		for (int i = 0; i < lines.size(); ++i)
		{
			int n = 0, m = 0;
			String line = lines.get(i);
			String line2 = line.trim();
			int button = 0;
			if (line2.toUpperCase().startsWith("BUTTON"))
			{
				if (buttonG == -1)
				{
					if (!input.equals(output))
					{
						button = FileParsing.parseInt(line2) + 1;
					}
					if (button == 12)
					{
						int j, menu = 2;
						for (j = 0; j < lines.size(); ++j)
						{
							String line3 = lines.get(j);
							if (line3.toUpperCase().startsWith("MENU"))
							{
								for(int l = 0; l < line2.length(); ++l)
								{
									if(line2.charAt(l) == '=')
									{
										n = l+1;
									}
									if(line2.charAt(l) == ';')
									{
										m = l;
									}
								}
								menu = Integer.parseInt(line2.substring(n, m).trim()) + 1;
								break;
							}
						}
						newOutputLines.set(j, "\tMENU=" + menu + ";");
						button = 0;
					}
					line = "\tBUTTON=" + button + ";";
				}
				else
				{
					line = "\tBUTTON=" + buttonG + ";";
				}
			}
			newOutputLines.add(line.replace(input, output));
		}
		FileEditer.updateFile(outputLines, newOutputLines, outputFile);
	}
}
