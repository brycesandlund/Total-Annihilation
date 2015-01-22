import java.io.FileNotFoundException;
import java.util.Scanner;


public class ChangeButton {
	public static void main(String[] args) throws FileNotFoundException
	{
		while (true)
		{
			Scanner in = new Scanner(System.in);
			System.out.println("Enter Unit to Change Button: ");
			String unit = in.nextLine();
			System.out.println("Enter Desired Button: ");
			int button = in.nextInt();
			
			Download newDownload = new Download(unit, unit);
			newDownload.setButton(button);
			newDownload.copyDownloads();
			System.out.println("Done.");
		}
	}
}
