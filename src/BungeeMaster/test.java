package BungeeMaster;

import BungeeMaster.Recursos.Datos;
import BungeeMaster.Recursos.JsonSimple.JSONObject;
import BungeeMaster.Recursos.JsonSimple.parser.JSONParser;
import BungeeMaster.Recursos.JsonSimple.parser.ParseException;
import BungeeMaster.Recursos.Lenguaje.Mensajes;

import java.io.*;

public class test
{
    public static void main(String args[]) throws IOException, ParseException
    {
        InputStream is = test.class.getResourceAsStream("Recursos/Lenguaje/lang.json");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        JSONParser parser = new JSONParser();
        Object o = parser.parse(br);
        JSONObject object = (JSONObject) o;

        for (int i = 0; i< Datos.MENSAJES_CARGADOS; i++)
        {
            JSONObject msg = (JSONObject) object.get(String.valueOf(i));
            String en = (String) msg.get("en");
            String es = (String) msg.get("es");
            new Mensajes(i, en, es);
        }

        System.out.println(Mensajes.getMensaje(10).get("en"));

        /**InputStream is = test.class.getResourceAsStream("Recursos/Lenguaje/lang.json");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        //BufferedReader br = new BufferedReader(new FileReader(test.class.getResource("Recursos/lang.json").getPath()));

        JSONParser parser = new JSONParser();
        Object o = parser.parse(br);
        JSONObject object = (JSONObject) o;
        JSONObject object2 = (JSONObject) object.get("0");

        System.out.println(object2.get("es"));*/
    }

    public static void prueba() throws IOException, ParseException
    {
        InputStream is = test.class.getResourceAsStream("Recursos/Lenguaje/lang.json");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        //BufferedReader br = new BufferedReader(new FileReader(test.class.getResource("Recursos/lang.json").getPath()));

        JSONParser parser = new JSONParser();
        Object o = parser.parse(br);
        JSONObject object = (JSONObject) o;
        JSONObject object2 = (JSONObject) object.get("0");

        System.out.println(object2.get("es"));
    }
}
