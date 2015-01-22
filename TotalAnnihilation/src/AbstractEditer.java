import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AbstractEditer {
	protected File modelFile, inputFile, outputFile;
	protected List<String> modelLines = new ArrayList<String>();
	protected List<String> inputLines = new ArrayList<String>();
	protected List<String> outputLines = new ArrayList<String>();
	protected List<Byte> modelBytes = new ArrayList<Byte>();
	protected List<Byte> inputBytes = new ArrayList<Byte>();
	protected List<Byte> outputBytes = new ArrayList<Byte>();
	protected String input, model, output;
	
	/**
	 * Will mess up if anything is not properly cased...
	 * @param input
	 * @param output
	 * @param model
	 * @param subDirectory
	 * @param extension
	 * @throws IOException
	 */
	public AbstractEditer(String input, String output, String model, String subDirectory, String extension) throws IOException
	{
		inputFile = new File(FileEditer.TABA_DIRECTORY + subDirectory + input + extension);
		outputFile = new File(FileEditer.TABA_DIRECTORY + subDirectory + output + extension);
		modelFile = new File(FileEditer.TABA_DIRECTORY + subDirectory + model + extension);
		this.model = model;
		this.output = output;
		this.input = input;
		if (!inputFile.exists())
		{
			inputFile = new File(FileEditer.TABA_COMPLETE_DIRECTORY + subDirectory + input + extension);
		}
		if (!modelFile.exists())
		{
			modelFile = new File(FileEditer.TABA_COMPLETE_DIRECTORY + subDirectory + model + extension);
		}
		modelLines = FileEditer.getLines(modelFile);
		inputLines = FileEditer.getLines(inputFile);
		modelBytes = FileEditer.getBytes(modelFile);
		inputBytes = FileEditer.getBytes(inputFile);
		if(!outputFile.exists())
		{
			outputFile.createNewFile();
		}
		else
		{
			outputLines = FileEditer.getLines(outputFile);
			outputBytes = FileEditer.getBytes(outputFile);
		}
	}
	
	public AbstractEditer(String input, String output, String subDirectory, String extension) throws IOException
	{
		inputFile = new File(FileEditer.TABA_DIRECTORY + subDirectory + input + extension);
		outputFile = new File(FileEditer.TABA_DIRECTORY + subDirectory + output + extension);
		this.output = output;
		this.input = input;
		if (!inputFile.exists())
		{
			inputFile = new File(FileEditer.TABA_COMPLETE_DIRECTORY + subDirectory + input + extension);
		}
		inputLines = FileEditer.getLines(inputFile);
		inputBytes = FileEditer.getBytes(inputFile);
		if(!outputFile.exists())
		{
			outputFile.createNewFile();
		}
		else
		{
			outputLines = FileEditer.getLines(outputFile);
			outputBytes = FileEditer.getBytes(outputFile);
		}
	}
}
