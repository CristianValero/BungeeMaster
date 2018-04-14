package BungeeMaster;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class test
{
    public static void main(String args[]) throws IOException, ParseException
    {
        BufferedReader br = new BufferedReader(new FileReader(test.class.getResource("Recursos/lang.json").getPath()));

        JSONParser parser = new JSONParser();
        Object o = parser.parse(br);
        JSONObject object = (JSONObject) o;
        JSONObject object2 = (JSONObject) object.get("0");

        System.out.println(object2.get("es"));
    }
}
