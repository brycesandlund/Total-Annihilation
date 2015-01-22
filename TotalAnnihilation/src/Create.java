import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;



public class Create {
	private String fileUnM, fileNew, fileModel, name;
	private boolean download, script, unit, anim, unitpic, model;
	private PrintWriter out;
	
	public Create(String fileUnM, String fileNew, String fileModel, String name, boolean download, boolean script, boolean unit, boolean anim, boolean unitpic, boolean model, PrintWriter out)
	{
		this.fileUnM = fileUnM;
		this.fileNew = fileNew;
		this.fileModel = fileModel;
		this.name = name;
		this.download = download;
		this.script = script;
		this.unit = unit;
		this.anim = anim;
		this.unitpic = unitpic;
		this.model = model;
		this.out = out;
	}
	
	public void createWeapon() throws IOException
	{
		if (download)
		{
			Download newDownload = new Download(fileModel, fileNew);
			newDownload.copyDownloads();
			download = false;
		}
		out.println("download");
		out.println(download);
		
		if (script)
		{
			ScriptEditer newScript = new ScriptEditer(fileUnM, fileNew);
			newScript.copyToWeaponScript();
			script = false;
		}
		out.println("script");
		out.println(script);
		
		if (unit)
		{
			UnitEditer newUnit = new UnitEditer(fileUnM, fileNew, fileModel, name);
			newUnit.copyToWeaponFile();
			unit = false;
		}
		//look out for weapon. It may help to test on a weapon you know works before the new one.
		out.println("unit");
		out.println(unit);
		
		if (anim)
		{
			AnimEditer newAnim = new AnimEditer(fileUnM, fileNew);
			newAnim.copyAndRenameAnim();
			anim = false;
		}
		out.println("anim");
		out.println(anim);
		
		if (unitpic)
		{
			UnitPicEditer newUnitPic = new UnitPicEditer(fileUnM, fileNew);
			newUnitPic.copyUnitPic();
			unitpic = false;
		}
		out.println("unitpic");
		out.println(unitpic);
		
		if (model)
		{
			ModelEditer modelEditer = new ModelEditer(fileUnM, fileNew);
			modelEditer.copyModel();
			model = false;
		}
		out.println("model");
		out.println(model);
		
		System.out.println("Steps until Completion:");
		System.out.println("1. Open " + fileNew + ".3do and create new piece heightstick, copy heightstick into base and objects other than turret.");
		System.out.println("2. Center turret into middle.");
		System.out.println("3. Open " + fileModel + ".3do and copy and paste heightstick into heightstick of " + fileNew + ".3do.");
		System.out.println("4. Create unit ground plate.");
		System.out.println("5. Compile/ Edit " + fileNew + ".bos.");
	}
	
	/**
	 * CreateWeapon cannot be done after this method
	 */
	public void convertToTank()
	{
		
	}
	
//	public static void main(String[] args) throws FileNotFoundException
//	{
//		boolean repeat = true;
//		while(repeat)
//		{
//			repeat = false;
//			Scanner in =  new Scanner(System.in);
//			try
//			{
//				File preferences = new File("CreateWeaponPreferences.txt");
//				
//				System.out.println("Enter model file (most recent added download): ");
//				String fileModel = in.nextLine();
//				
//				
//				String fileNew, name, fileUnM;
//				if (fileModel.isEmpty())
//				{
//					Scanner in2 = new Scanner(preferences);
//					fileModel = in2.nextLine();
//					fileNew = in2.nextLine();
//					name = in2.nextLine();
//					fileUnM = in2.nextLine();
//				}
//				else
//				{	
//					System.out.println("Enter new weapon file: ");
//					fileNew = in.nextLine();
//					
//					System.out.println("Enter new weapon name: ");
//					name = in.nextLine();
//					
//					System.out.println("Enter unmodified file: ");
//					fileUnM = in.nextLine();
//					
//					PrintWriter out = new PrintWriter(preferences);
//					out.println(fileModel);
//					out.println(fileNew);
//					out.println(name);
//					out.println(fileUnM);
//					out.close();
//				}
////				Download newDownload = new Download(previousFile, fileNew);
////				newDownload.copyDownloads();
//			
//				Script newScript = new Script(fileUnM, fileNew);
//				newScript.copyToWeaponScript();
//				
//				Unit newUnit = new Unit(fileUnM, fileNew, fileModel, name);
//				newUnit.copyToWeaponFile();
//				//look out for weapon. It may help to test on a weapon you know works before the new one.
//				
//				Anim newAnim = new Anim(fileUnM, fileNew);
//				newAnim.copyAndRenameAnim();
//				
//				UnitPic newUnitPic = new UnitPic(fileUnM, fileNew);
//				newUnitPic.copyUnitPic();
//			
//				System.out.println("Steps until Completion:");
//				System.out.println("1. Copy " + fileUnM + ".3do and paste to " + fileNew + ".3do, open and create new piece heightstick, copy heightstick into base and objects other than turret.");
//				System.out.println("2. Center turret into middle.");
//				System.out.println("3. Open " + fileModel + ".3do and copy and paste heightstick into heightstick of " + fileNew + ".3do.");
//				System.out.println("4. Create unit ground plate.");
//				System.out.println("5. Compile " + fileNew + ".bos.");
//				System.out.println("6. Run WeaponCostUpdater.");
//			}
//			catch (FileNotFoundException e)
//			{
//				repeat = true;
////				e.printStackTrace(System.out);
//				System.out.println(e.getMessage());
//				System.out.println("Press Enter to repeat attempt.");
//				in.nextLine();
//			} catch (IOException e) {
//				repeat = true;
////				e.printStackTrace(System.out);
//				System.out.println(e.getMessage());
//				System.out.println("Press Enter to repeat attempt.");
//				in.nextLine();
//			}
//		}
//		
//	}

}
