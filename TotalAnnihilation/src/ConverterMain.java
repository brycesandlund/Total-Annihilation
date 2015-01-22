import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main class for converting unit to weapon or tank or both
 * @author Owner
 *
 */
public class ConverterMain {
	private static final Operation operation = Operation.WEAPON;
	private static boolean download = true, script = true, unit = true, anim = true, unitpic = true, model = true;

	public static void main(String[] args) {
//		boolean repeat = true;
//		while(repeat)
//		{
//			repeat = false;
			Scanner in = new Scanner(System.in);
			File preferences = new File("Preferences.txt");
			PrintWriter out = null;
			try
			{
				System.out.println("Enter model file (most recent added download), or press enter to use last attempt: ");
				String fileModel = in.nextLine();
				
				
				String fileNew, name, fileUnM;
				if (fileModel.isEmpty())
				{
					Scanner in2 = new Scanner(preferences);
					fileModel = in2.nextLine();
					fileNew = in2.nextLine();
					name = in2.nextLine();
					fileUnM = in2.nextLine();
					try
					{
						in2.nextLine();
						in2.nextLine();
						download = in2.nextBoolean();
						in2.nextLine();
						in2.nextLine();
						script = in2.nextBoolean();
						in2.nextLine();
						in2.nextLine();
						unit = in2.nextBoolean();
						in2.nextLine();
						in2.nextLine();
						anim = in2.nextBoolean();
						in2.nextLine();
						in2.nextLine();
						unitpic = in2.nextBoolean();
						in2.nextLine();
						in2.nextLine();
						model = in2.nextBoolean();
					}
					catch (NoSuchElementException e)
					{
						//this will mean all variables not written will be tried again
					}
				}
				else
				{	
					System.out.println("Enter new weapon file: ");
					fileNew = in.nextLine();
					
					System.out.println("Enter new weapon name: ");
					name = in.nextLine();
					
					System.out.println("Enter unmodified file: ");
					fileUnM = in.nextLine();
				}
				out = new PrintWriter(preferences);
				out.println(fileModel);
				out.println(fileNew);
				out.println(name);
				out.println(fileUnM);
				out.println("weapon");
				Create create = new Create(fileUnM, fileNew, fileModel, name, download, script, unit, anim, unitpic, model, out);
				if (operation == Operation.BOTH || operation == Operation.WEAPON)
				{
					create.createWeapon();
				}
				if (operation == Operation.BOTH || operation == Operation.TANK)
				{
					create.convertToTank();
				}
				Unit unit = new Unit(fileNew);
				unit.updateOnDisk();
			}
			catch (Exception e)
			{
//				repeat = true;
				System.out.println(e.getMessage());
				e.printStackTrace();
//				System.out.println("Press Enter to repeat attempt.");
//				in.nextLine();
			}
			finally
			{
				if (out != null)
				{
					out.close();
				}
			}
//		}
		
	}
}
