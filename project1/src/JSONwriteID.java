import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.*;
import org.json.*;
/*import org.json.simple.JSONObject;
import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;*/

public class JSONwriteID 
{
	public static void main(String[] args) 
	{
		try
		{
		File f = new File("C:\\Users\\sg0940305\\Desktop\\EBDATA-BIN-ID-OUT.txt");
		FileWriter file = new FileWriter("C:\\Users\\sg0940305\\Desktop\\EBDATA-BIN-ID-OUT-JSON.txt");
		Scanner sc = new Scanner(f);
// Read Text File
		while(sc.hasNextLine())
			{
				String line = sc.nextLine();
				JSONObject obj = new JSONObject();
				if (line != null)
				{
					String[] details = line.split("=");
					String temp = details[0];
					String temp1 = details[1];
					obj.put(details[0],details[1]);
					try
					{
						file.write(obj.toString());
						file.write("\r\n");
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
				file.flush();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
