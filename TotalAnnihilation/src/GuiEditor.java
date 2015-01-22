import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class GuiEditor {
	public static void main(String[] args) throws FileNotFoundException
	{
		UnitFiles files = new UnitFiles();
		UnitClasses classes = new UnitClasses(files);
		for (int i = 0; i < files.turrets.size(); ++i)
		{
			String unit = UnitClasses.unitFileToString(files.turrets.get(i));
			if (unit.equals("ARMAMD"))
			{
				continue;
			}
			//System.out.println(new File(FileEditer.directory + "guis/" + unit + "1.gui").exists() + " " + new File(FileEditer.directory + "guis/" + unit + ".GUI").exists());	//warning... will delete some files you want still :S
			File currentFile = new File(FileEditer.TABA_DIRECTORY + "guis/" + unit + "1.gui");
			if (currentFile.exists())
			{
				currentFile.delete();
				System.out.println(currentFile + " Deleted.");
			}
		}
	}
}
