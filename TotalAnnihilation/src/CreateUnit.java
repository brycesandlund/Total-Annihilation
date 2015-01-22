import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class CreateUnit {
	public static void main(String[] args) throws FileNotFoundException
	{
		Update();
		
	}
	
	public static void Update() throws IOException
	{
		Scanner in =  new Scanner(System.in);
		System.out.println("Enter unit file (run after CreateWeapon): ");
		String file = in.nextLine();
		
	//	System.out.println("Enter unit range: ");
	//	char range = in.next().charAt(0);
		char range = 'C';
	
		boolean repeat = true;
		while(repeat)
		{
			repeat = false;
			try
			{
				ScriptEditer newScript = new ScriptEditer(file, file);
//				newScript.convertToUnitTurretScript();
				
				UnitEditer newUnit = new UnitEditer(file, range);
//				newUnit.convertToUnitTurretFile();
			}
			catch (FileNotFoundException e)
			{
				repeat = true;
//				e.printStackTrace(System.out);
				System.out.println(e.getMessage());
				System.out.println("Press Enter to repeat attempt.");
				in.nextLine();
			}
		}
		
		
		System.out.println("Steps until Completion:");
		System.out.println("1. Open " + file + ".3do and create new piece k, copy k into turret and child objects.");
		System.out.println("Rename turret to attachpt, and k to turret.");
		System.out.println("2. Compile " + file + ".bos.");
		System.out.println("3. Run UnitCostUpdater.");
	}
}
