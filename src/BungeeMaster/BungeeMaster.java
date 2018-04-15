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
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;
import java.sql.SQLException;

public class BungeeMaster extends Plugin
{
    private Modulo[] modulos = null;
    private BBDD bd = null;

    private Config config;

    public String NETWORK_NAME = null;
    public String DOMINIO = null;
    public String IDIOMA_CONSOLA = "ES";
    
    public BungeeMaster()
    {
		super();
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

        console("&e------------------------------------------");
        console("&e------------- [BungeeMaster] -------------");
        console("&e------------------------------------------");

        try
        {
			configes();
			iniciarModulos();
			//getProxy().getPluginManager().registerCommand(this, new ModuleCommand(this,"Modulo", Datos.PERMISO_ADMIN, "modulo", "module", "mdle", "md"));
		}
        catch (ClassNotFoundException e){ e.printStackTrace(); }
        catch (IOException e){ e.printStackTrace(); }
        catch (SQLException e){ e.printStackTrace(); }
    }

    @Override
    public void onDisable()
    {
        pararModulos();
        if(bd != null) bd.desconectar();
    }

    private void cargarMensajes() throws IOException, ParseException
    {
        InputStream is = test.class.getResourceAsStream("Recursos/Lenguaje/lang.json");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        JSONParser parser = new JSONParser();
        Object o = parser.parse(br);
        JSONObject object = (JSONObject) o;

        for (int i=0; i<Datos.MENSAJES_CARGADOS; i++)
        {
            JSONObject msg = (JSONObject) object.get(i);
            String en = (String) msg.get("en");
            String es = (String) msg.get("es");

            new Mensajes(i, en, es);
        }
    }

    public Modulo getModulo(String name)
    {
        for (Modulo modulo : modulos)
            if (modulo.getNombre().equals(name))
            	return modulo;
        return null;
    }

    private void configes() throws ClassNotFoundException, IOException, SQLException
    {
        config = new Config(Datos.CONFIG_NAME);

        if (!config.exists())
        {
            config.createConfig();

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

            //BungeeCord.getInstance().stop();
        }

        config.load();

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
        
        console(Mensajes.getMensaje(12).get(IDIOMA_CONSOLA));
    }

	private void iniciarModulos() throws IOException
    {
        console(Mensajes.getMensaje(10).get(IDIOMA_CONSOLA));
        
        for (Modulo m : modulos)
        {
            String path = "modulos."+m.getNombre();
            if (config.getObject(path) == null)
                config.setData(path, false);
            else
            {
            	if (config.getBoolean(path))
            		m.iniciar();
            }
        }

        console(Mensajes.getMensaje(11).get(IDIOMA_CONSOLA));
    }

    private void pararModulos()
    {
        console("&c(!) Todos los modulos van a ser desactivados.");
        for (Modulo m : modulos)
            if (m.isActivado())
                m.finalizar();
    }

    public void console(String msg)
    {
        getLogger().info("[" + NETWORK_NAME + "] "+ ChatColor.translateAlternateColorCodes('&', msg));
    }

    public String getNetworkName()
    {
        return NETWORK_NAME;
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
