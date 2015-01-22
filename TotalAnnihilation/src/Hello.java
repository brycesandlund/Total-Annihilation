import java.lang.reflect.Array;

public class Hello 
{
	public static void main(String[] args)
	{
		int stringnumber = 0;
/*Default Damage*/			int damage = 1700;
/*Damage Multipliers*/		int VLArmor = 0, LArmor = 0, MArmor = 0, HArmor = 0, VHArmor = 0, EHArmor = 0, UHArmor = 0, Krogoth = 0;
/*Very Light Units*/		String[] VeryLightUnits = {"ARMFAV", "CORFAV", "ARMROCK", "CORSTORM", "ARMPW"};	

/*Light Units*/				String[] LightUnits = {"CORPYRO", "ARMFAV", "CORMORT", "CORROACH"};			

/*Medium Units*/			String[] MediumUnits = {"CORLEVLR", "ARMMAV"};							

/*Heavy Units*/				String[] HeavyUnits = {"ARMGBT", "CORGBT", "ARMCS", "CORCS"};			

/*Very Heavy Units*/		String[] VeryHeavyUnits = {"ARMZEUS", "CORCAN", "ARMBULL", "CORGOL"};	

/*Extremely Heavy Units*/	String[] ExtremelyHeavyUnits = {};										

/*Ultra Heavy Units*/		String[] UltraHeavyUnits = {};

/*Krogoth*/					String[] KrogothUnits = {"CORKROG"};
							
							




		if (damage < 75)
		{
			System.out.println("//WeaponClass = Very Light;");
			VLArmor = 130;
			LArmor = 115;
			MArmor = 100;
			HArmor = 85;
			VHArmor = 70;
			EHArmor = 55;
			UHArmor = 40;
			Krogoth = 25;
		}
		if (damage >= 75 && damage < 150)
		{
			System.out.println("//WeaponClass = Light;");
			VLArmor = 106;
			LArmor = 121;
			MArmor = 106;
			HArmor = 91;
			VHArmor = 76;
			EHArmor = 61;
			UHArmor = 46;
			Krogoth = 31;
		}
		if (damage >= 150 && damage < 300)
		{
			System.out.println("//WeaponClass = Medium;");
			VLArmor = 88;
			LArmor = 103;
			MArmor = 118;
			HArmor = 103;
			VHArmor = 88;
			EHArmor = 73;
			UHArmor = 58;
			Krogoth = 43;
		}
		if (damage >= 300 && damage < 600)
		{
			System.out.println("//WeaponClass = Heavy;");
			VLArmor = 76;
			LArmor = 91;
			MArmor = 106;
			HArmor = 121;
			VHArmor = 106;
			EHArmor = 91;
			UHArmor = 76;
			Krogoth = 61;
		}
		if (damage >= 600 && damage < 1200)
		{
			System.out.println("//WeaponClass = Very Heavy;");
			VLArmor = 70;
			LArmor = 85;
			MArmor = 100;
			HArmor = 115;
			VHArmor = 130;
			EHArmor = 115;
			UHArmor = 100;
			Krogoth = 75;
		}
		if (damage >= 1200 && damage < 2400)
		{
			System.out.println("//WeaponClass = Extremely Heavy;");
			VLArmor = 70;
			LArmor = 85;
			MArmor = 100;
			HArmor = 115;
			VHArmor = 130;
			EHArmor = 145;
			UHArmor = 130;
			Krogoth = 115;
		}
		if (damage >= 2400 && damage < 4800)
		{
			System.out.println("//WeaponClass = Ultra Heavy;");
			VLArmor = 70;
			LArmor = 85;
			MArmor = 100;
			HArmor = 115;
			VHArmor = 130;
			EHArmor = 145;
			UHArmor = 160;
			Krogoth = 145;
		}
		if (damage >= 4800 && damage < 32000)
		{
			System.out.println("//WeaponClass = Anti-Krogoth;");
			VLArmor = 70;
			LArmor = 85;
			MArmor = 100;
			HArmor = 115;
			VHArmor = 130;
			EHArmor = 145;
			UHArmor = 160;
			Krogoth = 185;
		}
		if (damage == 32000)
		{
			System.out.println("YOU'RE RETARDED, USER!");
		}
		System.out.println("default=" + damage + ";");
		System.out.println("//very light units - " + VLArmor + "%");
		for (stringnumber = 0; stringnumber < Array.getLength(VeryLightUnits); ++stringnumber)
		{
			System.out.println(VeryLightUnits[stringnumber] + "=" + damage*VLArmor / 100 + ";");
		}
		System.out.println("//light units - " + LArmor + "%");
		for (stringnumber = 0; stringnumber < Array.getLength(LightUnits); ++stringnumber)
		{
			System.out.println(LightUnits[stringnumber] + "=" + damage*LArmor / 100 + ";");
		}
		System.out.println("//medium units - " + MArmor + "%");
		for (stringnumber = 0; stringnumber < Array.getLength(MediumUnits); ++stringnumber)
		{
			System.out.println(MediumUnits[stringnumber] + "=" + damage*MArmor / 100 + ";");
		}
		System.out.println("//heavy units - " + HArmor + "%");
		for (stringnumber = 0; stringnumber < Array.getLength(HeavyUnits); ++stringnumber)
		{
			System.out.println(HeavyUnits[stringnumber] + "=" + damage*HArmor / 100 + ";");
		}
		System.out.println("//very heavy units - " + VHArmor + "%");
		for (stringnumber = 0; stringnumber < Array.getLength(VeryHeavyUnits); ++stringnumber)
		{
			System.out.println(VeryHeavyUnits[stringnumber] + "=" + damage*VHArmor / 100 + ";");
		}
		System.out.println("//extremely heavy units - " + EHArmor + "%");
		for (stringnumber = 0; stringnumber < Array.getLength(ExtremelyHeavyUnits); ++stringnumber)
		{
			System.out.println(ExtremelyHeavyUnits[stringnumber] + "=" + damage*EHArmor / 100 + ";");
		}
		System.out.println("//ultra heavy units - " + UHArmor + "%");
		for (stringnumber = 0; stringnumber < Array.getLength(UltraHeavyUnits); ++stringnumber)
		{
			System.out.println(UltraHeavyUnits[stringnumber] + "=" + damage*UHArmor / 100 + ";");
		}
		System.out.println("//krogoth - " + Krogoth + "%");
		for (stringnumber = 0; stringnumber < Array.getLength(KrogothUnits); ++stringnumber)
		{
			System.out.println(KrogothUnits[stringnumber] + "=" + damage*Krogoth / 100 + ";");
		}
	}
}
