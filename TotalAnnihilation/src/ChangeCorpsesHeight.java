import java.io.FileNotFoundException;


public class ChangeCorpsesHeight extends ChangeValuesInDirectory{

	public static final String location = "C:/Users/Owner/Documents/TABA/TABA_revisited/corpses";
	public static final String expression = "[\\s&&[^\n]]height=([0-9]*);";//^ character not working as expected. This should capture any number of whitespaces - newline and then height
	
	public ChangeCorpsesHeight(String fileDirectory, String expression) {
		super(fileDirectory, expression);
	}

	@Override
	protected String performOperation(String match) {
		int height = Integer.parseInt(match);
		return Math.round(height * .5) + "";
	}

	public static void main(String[] args) throws FileNotFoundException {
		ChangeCorpsesHeight cch = new ChangeCorpsesHeight(location, expression);
		cch.change();
	}
}
