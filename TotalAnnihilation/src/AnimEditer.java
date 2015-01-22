import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AnimEditer extends AbstractEditer{
	
	public AnimEditer(String input, String output) throws IOException 
	{
		super(input, output, "anims/", "_gadget.GAF");
		// TODO Auto-generated constructor stub
	}
	
	public void copyAndRenameAnim() throws IOException
	{
		byte[] inputArr = input.getBytes();
		byte[] outputArr = output.getBytes();
		
		List<Byte> newOutputBytes = new ArrayList<Byte>();
		List<Byte> byteTemp = new ArrayList<Byte>();
        int i = 0;

        for (int k = 0; k < inputBytes.size(); ++k)
        {
        	if (inputBytes.get(k) == inputArr[i++])
        	{
        		byteTemp.add(inputBytes.get(k));
        		if (i == inputArr.length)
        		{
        			for (int j = 0; j < outputArr.length; ++j)
        			{
        				newOutputBytes.add(outputArr[j]);
        			}
        			newOutputBytes.add((byte) 0);
        			
        			int offset = outputArr.length - inputArr.length + 1;
        			
        			if (offset > 0)
        			{
        				k += offset;
        			}
        			else
        			{
        				for (int j = 0; j < offset * -1; ++j)
        				{
        					newOutputBytes.add((byte) 32);
        				}
        			}
        			
        			byteTemp.clear();
        			i = 0;
        		}
        	}
        	else
        	{
        		i = 0;
        		if (!byteTemp.isEmpty())
        		{
        			newOutputBytes.addAll(byteTemp);
        			byteTemp.clear();
        		}
        		newOutputBytes.add(inputBytes.get(k));
        	}
        }
        FileEditer.updateByteFile(outputBytes, newOutputBytes, outputFile);
	}

//	public static void copyAndRenameAnim(String input, String output) throws IOException {
//
//		File source = new File(FileEditer.TABA_DIRECTORY + "anims\\" + input + "_gadget.gaf");
//		File target = new File(FileEditer.TABA_DIRECTORY + "anims\\" + output + "_gadget.gaf");
//		
//		byte[] inputArr = input.getBytes();
//		byte[] outputArr = output.getBytes();
//		
//		List<Byte> oldOutputBytes = new ArrayList<Byte>();
//		if (target.exists())
//		{
//			oldOutputBytes = FileEditer.getBytes(target);
//		}
//		
//		List<Byte> inputBytes = FileEditer.getBytes(source);
//        List<Byte> outputBytes = new ArrayList<Byte>();
//        List<Byte> byteTemp = new ArrayList<Byte>();
//        int i = 0;
//
//        for (int k = 0; k < inputBytes.size(); ++k)
//        {
//        	if (inputBytes.get(k) == inputArr[i++])
//        	{
//        		byteTemp.add(inputBytes.get(k));
//        		if (i == inputArr.length)
//        		{
//        			for (int j = 0; j < outputArr.length; ++j)
//        			{
//        				outputBytes.add(outputArr[j]);
//        			}
//        			outputBytes.add((byte) 0);
//        			
//        			int offset = outputArr.length - inputArr.length + 1;
//        			
//        			if (offset > 0)
//        			{
//        				k += offset;
//        			}
//        			else
//        			{
//        				for (int j = 0; j < offset * -1; ++j)
//        				{
//        					outputBytes.add((byte) 32);
//        				}
//        			}
//        			
//        			byteTemp.clear();
//        			i = 0;
//        		}
//        	}
//        	else
//        	{
//        		i = 0;
//        		if (!byteTemp.isEmpty())
//        		{
//        			outputBytes.addAll(byteTemp);
//        			byteTemp.clear();
//        		}
//        		outputBytes.add(inputBytes.get(k));
//        	}
//        }
//        FileEditer.updateByteFile(oldOutputBytes, outputBytes, target);
//    }
}
