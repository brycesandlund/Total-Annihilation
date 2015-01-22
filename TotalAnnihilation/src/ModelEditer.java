import java.io.IOException;


public class ModelEditer extends AbstractEditer{

	public ModelEditer(String input, String output) throws IOException {
		super(input, output, "objects3d\\", ".3do");
		// TODO Auto-generated constructor stub
	}

	public void copyModel() throws IOException
	{
		FileEditer.updateByteFile(outputBytes, inputBytes, outputFile);
	}
}
