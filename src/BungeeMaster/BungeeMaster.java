package BungeeMaster;

import BungeeMaster.Listeners.Modulo;
import BungeeMaster.Listeners.Query.BBDD;
import BungeeMaster.Listeners.Seguridad.JoinDomain;
import BungeeMaster.Listeners.Servidor.ServerMotd;
import BungeeMaster.Recursos.Config;
import BungeeMaster.Recursos.Datos;
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
    private Mensajes mensajes;
    
    public BungeeMaster()
    {
		super();
		config = new Config(Datos.CONFIG_NAME);
		
		mensajes = new Mensajes();
		mensajes.addIdioma("es");
		mensajes.addIdioma("en");
		
		NETWORK_NAME = null;
	    DOMINIO = null;
	    IDIOMA_CONSOLA = "es";
	}

	@Override
    public void onLoad() //En esta funci�n se van a cargar todas las cosas esenciales como los mensajes.
    {
        try
        {
        	mensajes.cargar();
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
    	            if (config.getObject(path) != null && config.getBoolean(path))
    	                m.iniciar();
    	        }
    	        console(11);
    	        console(12);
    	        //getProxy().getPluginManager().registerCommand(this, new ModuleCommand(this,"Modulo", Datos.PERMISO_ADMIN, "modulo", "module", "mdle", "md"));
            }
            else
            {
                IDIOMA_CONSOLA = "en";

                config.setData(Datos.CONFIG_PROPIEDADES_SERVIDOR, "NOMBRESERVIDOR");
                config.setData(Datos.CONFIG_PROPIEDADES_DOMINIO, "mc.nombreservidor.com");
                config.setData(Datos.CONFIG_LENG_CONSOLA, "es");

                config.setData(Datos.CONFIG_MYSQL_HOST, "169.0.0.1");
                config.setData(Datos.CONFIG_MYSQL_PUERTO, 3306);
                config.setData(Datos.CONFIG_MYSQL_NOMBRE, "minecraft");
                config.setData(Datos.CONFIG_MYSQL_USUARIO, "root");
                config.setData(Datos.CONFIG_MYSQL_CONTRA, "123abc");
                config.setData(Datos.CONFIG_MYSQL_PREFIX, "nameserver_");
                
                for (Modulo m : modulos)
    	        {
    	            String path = "modulos."+m.getNombre();
    	            if (config.getObject(path) == null)
    	                config.setData(path, false);
    	        }

                config.save();

                console(12);
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

    private void pararModulos()
    {
        console(17);
        for (Modulo m : modulos)
            if (m.isActivado())
                m.finalizar();
    }

    public Mensajes getMensajes() {
        return mensajes;
    }

    public String getIdiomaConsola() {
        return IDIOMA_CONSOLA;
    }

    public void console(Object msg)
    {
        getLogger().info(ChatColor.translateAlternateColorCodes('&', msg.toString()));
    }
    
    public void console(int num)
    {
        console(mensajes.get(num, IDIOMA_CONSOLA));
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
