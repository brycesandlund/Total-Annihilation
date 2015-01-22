import java.io.FileNotFoundException;


public class UniversalFileEditer {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		UnitFiles files = new UnitFiles();
		String oldLine = "\tShootMe=1;";
		String newLine = "\tShootMe=0;";
		FileEditer.switchLineInFiles(oldLine, newLine, files.weapons);
	}
}
