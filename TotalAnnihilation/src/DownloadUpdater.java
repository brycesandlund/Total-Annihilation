import java.io.FileNotFoundException;


public class DownloadUpdater {
	public static void main(String[] args) throws FileNotFoundException
	{
		UnitFiles files = new UnitFiles();
		UnitClasses classes = new UnitClasses(files);
		int i;
		for (i = 0; UnitClasses.unitFileToString(files.weapons.get(i)).startsWith("ARM"); ++i)
//		for (i = 0; i < 2; ++i)
		{
			Download thisDownload = new Download("ARMBOMB", UnitClasses.unitFileToString(files.weapons.get(i)));
			thisDownload.findButton();
			thisDownload.copyDownloads();
		}
		for (; i < files.weapons.size(); ++i)
		{
			Download thisDownload = new Download("CORBOMB", UnitClasses.unitFileToString(files.weapons.get(i)));
			thisDownload.findButton();
			thisDownload.copyDownloads();
		}
		System.out.println("complete.");
	}
}
