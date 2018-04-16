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

public class Mensajes
{
	private ArrayList<Traducciones> lista;
	private Lista<String> idiomas;
	
	public Mensajes()
	{
		this.lista = new ArrayList<Traducciones>();
		this.idiomas = new Lista<String>();
	}
	
	public void cargar() throws IOException, ParseException
	{
		InputStream is = getClass().getResourceAsStream(Datos.LANG_JSON_PATH);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(br), msg;

		int contador = 0;
        while (object.get(String.valueOf(contador)) != null)
		{
			msg = (JSONObject) object.get(String.valueOf(contador));
			Traducciones aux = new Traducciones();
			for (String idioma : idiomas)
				aux.add(new Mensaje(idioma.toLowerCase(), (String)msg.get(idioma)));
			lista.add(aux);

			contador++;
		}
	}
	
	public void addIdioma(String idioma)
	{
		idiomas.add(idioma);
	}
	
	public String get(int num, String idioma)
	{
		return lista.get(num).get(idioma);
	}

	private class Mensaje
	{
		private String idioma;
		private String mensaje;

		private Mensaje(String idioma, String mensaje)
		{
			this.idioma = idioma;
			this.mensaje = mensaje;
		}

		public String getIdioma()
		{
			return idioma;
		}

		@Override
		public String toString()
		{
			return mensaje;
		}
	}

	private class Traducciones
	{
		private Lista<Mensaje> mensajes;

		private Traducciones()
		{
			this.mensajes = new Lista<Mensaje>();
		}

		public void add(Mensaje mensaje)
		{
			mensajes.add(mensaje);
		}

		public String get(String idioma)
		{
			for (Mensaje mensaje : mensajes)
			{
				if (mensaje.getIdioma().equalsIgnoreCase(idioma))
					return mensaje.toString();
			}
			return "";
		}
	}
}
