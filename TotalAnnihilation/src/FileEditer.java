import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FileEditer {
	protected ArrayList<String> inputLines = new ArrayList<String>();
	protected ArrayList<String> outputLines = new ArrayList<String>();
	protected File inputFile;
	protected File outputFile;
	
	/**
	 * Single static final directory that all other classes refer to
	 */
	public static final String TABA_DIRECTORY = "C:\\Users\\Owner\\Documents\\TABA\\TABA_revisited\\Rev31.gp3 - TABA Unlimited_revisited\\";
	public static final String TABA_COMPLETE_DIRECTORY = "C:\\Users\\Owner\\Documents\\TABA\\TABA_revisited\\all\\";
	protected static boolean updates = false;
	protected static int nModified = 0;
	
	public FileEditer(String input, String output) throws FileNotFoundException
	{
		inputFile = new File(TABA_DIRECTORY + input);
		outputFile = new File(TABA_DIRECTORY + output);
		inputLines = getLines(inputFile);
		outputLines = getLines(outputFile);
	}
	
	public static ArrayList<String> getLines(File currentFile) throws FileNotFoundException
	{
		ArrayList<String> totalLines = new ArrayList<String>();
		Scanner in = new Scanner(currentFile);
		while (in.hasNextLine())
		{
			totalLines.add(in.nextLine());
		}
		return totalLines;
	}
	
	public static List<Byte> getBytes(File currentFile) throws IOException
	{
		InputStream in = new FileInputStream(currentFile);
    
        // Copy the bits from instream to outstream
        byte[] buf = new byte[1];
        int len;
        
        List<Byte> bytes = new ArrayList<Byte>();
        int i = 0;

        while ((len = in.read(buf)) > 0) {
        	bytes.add(buf[0]);
        }
        
        return bytes;
	}
	
	public static void switchLineInFile(String oldLine, String newLine, File file) throws FileNotFoundException
	{
		ArrayList<String> oldTotalLines = getLines(file);
		ArrayList<String> newTotalLines = switchLine(oldLine, newLine, oldTotalLines);
		updateFile(oldTotalLines, newTotalLines, file);
	}
	
	public static void switchLineInFiles(String oldLine, String newLine, ArrayList<File> totalFiles) throws FileNotFoundException
	{
		for (int i = 0; i < totalFiles.size(); ++i)
		{
			switchLineInFile(oldLine, newLine, totalFiles.get(i));
		}
		if (!updates)
		{
			System.out.println("No files Updated");
		}
	}
	
	public static void updateByteFile(List<Byte> oldBytes, List<Byte> newBytes, File currentFile) throws IOException
	{
		if (!newBytes.equals(oldBytes))
		{
			updates = true;
			OutputStream out = new FileOutputStream(currentFile);
			byte[] temp = new byte[1];
			for (int k = 0; k < newBytes.size(); ++k)
			{
			 	temp[0] = newBytes.get(k);
			  	out.write(temp, 0, 1);
			}
			++nModified;
			out.close();
			System.out.println(nModified + ": File " + currentFile + " Updated.");
		}
	}
	
	public static void updateFile(List<String> oldLines, List<String> newLines, File currentFile) throws FileNotFoundException
	{
		if (!newLines.equals(oldLines))
		{
			updates = true;
			PrintWriter out = new PrintWriter(currentFile);
			for(int k = 0; k < newLines.size(); ++k)
			{
				out.println(newLines.get(k));
			}
			out.close();
			++nModified;
			System.out.println(nModified + ": File " + currentFile + " Updated.");
		}
	}
	
	/**
	 * Doesn't actually do anything other than standardize a nice little message.
	 * @param oldLines
	 * @param newLines
	 * @param name
	 */
	public static List<String> updateList(List<String> oldLines, List<String> newLines, String name, String header)
	{
		if (!newLines.equals(oldLines))
		{
			updates = true;
			++nModified;
			System.out.println(nModified + ": " + header + " " + name + " Updated.");
		}
		return newLines;
	}
	
	public static ArrayList<String> switchLine(String oldLine, String newLine, ArrayList<String> totalLines)
	{
		ArrayList<String> newTotalLines = new ArrayList<String>();
		for (int i = 0; i < totalLines.size(); ++i)
		{
			if (totalLines.get(i).equals(oldLine))
			{
				newTotalLines.add(newLine);
			}
			else
			{
				newTotalLines.add(totalLines.get(i));
			}
		}
		return newTotalLines;
	}
	
	public static List<String> removeLine(List<String> lines, String prefixKeyword)
	{
		List<String> newLines = new ArrayList<String>();
		for (int i = 0; i < lines.size(); ++i)
		{
			if (lines.get(i).trim().toLowerCase().startsWith(prefixKeyword.toLowerCase().trim()))
			{
				
			}
			else
			{
				newLines.add(lines.get(i));
			}
		}
		return newLines;
	}
}
