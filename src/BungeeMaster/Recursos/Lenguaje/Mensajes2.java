package BungeeMaster.Recursos.Lenguaje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import BungeeMaster.Librerias.Lista;
import BungeeMaster.Recursos.Datos;
import BungeeMaster.Recursos.JsonSimple.JSONObject;
import BungeeMaster.Recursos.JsonSimple.parser.JSONParser;
import BungeeMaster.Recursos.JsonSimple.parser.ParseException;

public class Mensajes2
{
	private ArrayList<Bloque> lista;
	private Lista<String> idiomas;
	
	private class Mensaje
	{
		private String code;
		private String mensaje;
		
		private Mensaje(String code, String mensaje)
		{
			this.code = code;
			this.mensaje = mensaje;
		}
		
		public String getCode() {
			return code;
		}
		
		@Override
		public String toString()
		{
			return mensaje;
		}
	}
	
	private class Bloque
	{
		private Lista<Mensaje> mensajes;
		
		private Bloque()
		{
			this.mensajes = new Lista<Mensaje>();
		}
		
		public void add(Mensaje mensaje)
		{
			mensajes.add(mensaje);
		}
		
		public String get(String code)
		{
			for (Mensaje mensaje : mensajes)
			{
				if(mensaje.getCode().equals(code))
					return mensaje.toString();
			}
			return code;
		}
	}
	
	public Mensajes2()
	{
		lista = new ArrayList<Bloque>();
		idiomas = new Lista<String>();
	}
	
	public void cargar() throws IOException, ParseException
	{
		Bloque aux;
		InputStream is = getClass().getResourceAsStream("lang.json");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(br), msg;

        for (int i=0; i<=Datos.MENSAJES_CARGADOS; i++)
        {
            msg = (JSONObject) object.get(String.valueOf(i));
            aux = new Bloque();
            for (String code : idiomas)
				aux.add(new Mensaje(code, (String)msg.get(code)));
            lista.add(aux);
        }
	}
	
	public void addIdioma(String idioma)
	{
		idiomas.add(idioma);
	}
	
	public String get(int num, String code)
	{
		return lista.get(num).get(code);
	}
}
