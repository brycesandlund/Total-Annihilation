import java.io.IOException;


public class UnitPicEditer extends AbstractEditer{

	public UnitPicEditer(String input, String output) throws IOException 
	{
		super(input, output, "unitpics/", ".PCX");
		// TODO Auto-generated constructor stub
	}

	public void copyUnitPic() throws IOException
	{
		FileEditer.updateByteFile(outputBytes, inputBytes, outputFile);
	}
}
