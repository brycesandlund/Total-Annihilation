import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;





public class test {
	public static void main(String[] args) throws IOException
	{
//		ScriptEditer sE = new ScriptEditer("ARMBULL", "ARMHCANNON");
//		sE.copyToWeaponScript();
		AnimEditer aE = new AnimEditer("CORARCH", "CORMFLAK");
		aE.copyAndRenameAnim();
		
		UnitPicEditer uE = new UnitPicEditer("CORARCH", "CORMFLAK");
		uE.copyUnitPic();
		
//		Unit unit = new Unit("CORLBLAS");
//		unit.updateOnDisk();
//		System.out.println("Heavy Turret, Very Heavy Weapon, A=3200, DPS=142, R=1375, E=1.02".length());
	}
}
