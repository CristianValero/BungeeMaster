package BungeeMaster;

import BungeeMaster.Listeners.Modulo;
import BungeeMaster.Listeners.Query.BBDD;
import BungeeMaster.Listeners.Seguridad.JoinDomain;
import BungeeMaster.Listeners.Servidor.ServerMotd;
import BungeeMaster.Recursos.Config;
import BungeeMaster.Recursos.Datos;
import BungeeMaster.Recursos.JsonSimple.JSONObject;
import BungeeMaster.Recursos.JsonSimple.parser.JSONParser;
import BungeeMaster.Recursos.JsonSimple.parser.ParseException;
import BungeeMaster.Recursos.Lenguaje.Mensajes;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;

public class BungeeMaster extends Plugin
{
    private Modulo[] modulos;
    private BBDD bd;
    private Config config;
    private String NETWORK_NAME;
    private String DOMINIO;
    private String IDIOMA_CONSOLA;
    
    public BungeeMaster()
    {
		super();
		config = new Config(Datos.CONFIG_NAME);
		NETWORK_NAME = null;
	    DOMINIO = null;
	    IDIOMA_CONSOLA = "ES";
	}

	@Override
    public void onLoad() //En esta función se van a cargar todas las cosas esenciales como los mensajes.
    {
        try
        {
            cargarMensajes();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable()
    {
        modulos = new Modulo[]
        {
			new JoinDomain(this),
			new ServerMotd(this)
        };

        try
        {
        	if (config.createConfig())
            {
            	bd = new BBDD
    			(
    				config.getString(Datos.CONFIG_MYSQL_HOST),
    				config.getInteger(Datos.CONFIG_MYSQL_PUERTO),
    				config.getString(Datos.CONFIG_MYSQL_NOMBRE),
    				config.getString(Datos.CONFIG_MYSQL_USUARIO),
    				config.getString(Datos.CONFIG_MYSQL_CONTRA),
    				config.getString(Datos.CONFIG_MYSQL_PREFIX)
    			);
    	        //bd.conectar();

    	        NETWORK_NAME = config.getString(Datos.CONFIG_PROPIEDADES_SERVIDOR);
    	        DOMINIO = config.getString(Datos.CONFIG_PROPIEDADES_DOMINIO);
    	        IDIOMA_CONSOLA = config.getString(Datos.CONFIG_LENG_CONSOLA);
    	        
    	        console(10);
    	        for (Modulo m : modulos)
    	        {
    	            String path = "modulos."+m.getNombre();
    	            if (config.getObject(path) == null)
    	                config.setData(path, false);
    	            else
    	            	if (config.getBoolean(path))
    	            		m.iniciar();
    	        }
    	        console(11);
    	        console(12);
    	        //getProxy().getPluginManager().registerCommand(this, new ModuleCommand(this,"Modulo", Datos.PERMISO_ADMIN, "modulo", "module", "mdle", "md"));
            }
            else
            {
                config.setData(Datos.CONFIG_PROPIEDADES_SERVIDOR, "NOMBRESERVIDOR");
                config.setData(Datos.CONFIG_PROPIEDADES_DOMINIO, "mc.nombreservidor.com");
                config.setData(Datos.CONFIG_LENG_CONSOLA, "es");

                config.setData(Datos.CONFIG_MYSQL_HOST, "169.0.0.1");
                config.setData(Datos.CONFIG_MYSQL_PUERTO, 3306);
                config.setData(Datos.CONFIG_MYSQL_NOMBRE, "minecraft");
                config.setData(Datos.CONFIG_MYSQL_USUARIO, "root");
                config.setData(Datos.CONFIG_MYSQL_CONTRA, "123abc");
                config.setData(Datos.CONFIG_MYSQL_PREFIX, "nameserver_");

                config.save();
                BungeeCord.getInstance().stop();
            }
		}
        catch (ClassNotFoundException e){ e.printStackTrace(); BungeeCord.getInstance().stop(); }
        catch (IOException e){ e.printStackTrace(); BungeeCord.getInstance().stop(); }
        //catch (SQLException e){ e.printStackTrace(); BungeeCord.getInstance().stop(); }
    }

    @Override
    public void onDisable()
    {
        pararModulos();
        if(bd != null) bd.desconectar();
    }

    private void cargarMensajes() throws IOException, ParseException
    {
        InputStream is = test.class.getResourceAsStream(Datos.LANG_JSON_PATH);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        JSONParser parser = new JSONParser();
        Object o = parser.parse(br);
        JSONObject object = (JSONObject) o;

        for (int i=0; i<Datos.MENSAJES_CARGADOS; i++)
        {
            JSONObject msg = (JSONObject) object.get(String.valueOf(i));
            String en = (String) msg.get("en");
            String es = (String) msg.get("es");

            new Mensajes(i, en, es);
        }
    }

    private void pararModulos()
    {
        console("&c(!) Todos los modulos van a ser desactivados.");
        for (Modulo m : modulos)
            if (m.isActivado())
                m.finalizar();
    }

    public void console(Object msg)
    {
        getLogger().info("[" + NETWORK_NAME + "] "+ ChatColor.translateAlternateColorCodes('&', msg.toString()));
    }
    
    public void console(int num)
    {
        console(Mensajes.getMensaje(num).get(IDIOMA_CONSOLA));
    }
    
    public Modulo getModulo(String name)
    {
        for (Modulo modulo : modulos)
            if (modulo.getNombre().equals(name))
            	return modulo;
        return null;
    }

    public String getNetworkName()
    {
        return NETWORK_NAME;
    }
    
    public String getDominio()
    {
        return DOMINIO;
    }
    
    public void registerListener(Listener listener)
    {
        getProxy().getPluginManager().registerListener(this, listener);
    }

    public void unregisterListener(Listener listener)
    {
        getProxy().getPluginManager().unregisterListener(listener);
    }
}
