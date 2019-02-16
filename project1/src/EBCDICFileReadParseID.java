import java.time.*;
import java.text.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;
import java.nio.charset.Charset;
import javax.xml.bind.DatatypeConverter;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
class EBCDICFileReadParseID
{
	static Charset charsetEBCDIC = Charset.forName("CP037");
	static Charset charsetACSII = Charset.forName("CP1047");
	static int[] myIntArray = new int[]{32,2,24,5,5,52,2,5,1,4,2,6,2,2,50};
	static String[] mycharArray = new String[]{"BN","C","BN","C","C","BN","B","C","CN","C","C","CN","B","B","C"};
	static String[] mystrArray = new String[]{"Header","RecID","Fill1","StartDate","EndDate","Fill2","IndexDate","FlightDate","Fill3","FlightNumber","AirlineCode","Fill4","LegCount","CompCount","ClassList"};
	public static void main(String args[]) throws FileNotFoundException 
	{
	try
        {
		InputStreamReader reader = new InputStreamReader(new FileInputStream("C:\\Users\\sg0940305\\Desktop\\EBDATA-BIN-ID"),"CP037");
		PrintWriter printer = new PrintWriter("C:\\Users\\sg0940305\\Desktop\\EBDATA-BIN-ID-OUT.txt");
		BufferedReader bufferedReader=new BufferedReader(reader);
		char[] buffer = new char[4127];
		int length = bufferedReader.read(buffer,0,4127);
		String line = new String(buffer,0,length);
		while (line != null) 
		{
			//System.out.println("Array Length = " +myIntArray.length +" -- " +line.length());
			String PDString = line.substring(0,195);
			int st_pos = 0;
			int end_pos = 0;
			for (int i = 0;i < myIntArray.length;i++)
			{
				end_pos = st_pos + myIntArray[i];
				//System.out.println("Start pos = " +st_pos +"----" +"End Pos = " +end_pos);
				String temp = PDString.substring(st_pos,end_pos);
				//System.out.println("temp String = " +temp +temp.length());
				if (mycharArray[i] == "B")
				{
					byte[] pd = temp.getBytes("CP037");
					//System.out.println("PD length = " +pd.length);
					//System.out.println("Field Position = " +st_pos +" to " +end_pos);
					printer.write(mystrArray[i] +"="); //write
					for (int j = 0;j < pd.length;j++)
					{
						printer.write(String.format("%02x", pd[j],"CP1047").toUpperCase()); //write
					}
					printer.flush();printer.printf("%s\r\n","");
				}
				if (mycharArray[i] == "C")
				{
					//System.out.println("Here = " +mycharArray[i] +"-" +i);
					String temp_char = convertTO(temp,charsetEBCDIC,charsetACSII);
					//System.out.println("Field Position = " +st_pos);
					printer.write(mystrArray[i] +"=" +temp_char.toUpperCase());printer.flush();printer.printf("%s\r\n",""); //Write
				}
				st_pos = st_pos + myIntArray[i];
				//printer.write("");printer.flush();printer.printf("%s\r\n",""); //write
				//System.out.println("--");
			}
			//System.out.println("Next Line");
			printer.write("NextRec=New");printer.flush();printer.printf("%s\r\n",""); //write
			line = "";
			//line=bufferedReader.readLine();
			//char[] buffer = new char[4127];
			length = bufferedReader.read(buffer,0,4127);
			if (length > 0) line = new String(buffer,0,length);
			if (length < 0) break;
		}
			bufferedReader.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace(System.err);
		}
	}
public static String convertTO(String dados, Charset encondingFrom, Charset encondingTo) 
	{
        return new String(dados.getBytes(encondingFrom), encondingTo);
    }
}