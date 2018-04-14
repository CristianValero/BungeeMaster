package BungeeMaster;

import BungeeMaster.Listeners.Modulo;
import BungeeMaster.Listeners.Query.BBDD;
import BungeeMaster.Listeners.Seguridad.JoinDomain;
import BungeeMaster.Listeners.Servidor.ServerMotd;
import BungeeMaster.Recursos.Datos;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.sql.SQLException;

public class BungeeMaster extends Plugin
{
    private Modulo[] modulos = null;
    private BBDD bd = null;

    private Configuration configuracion = null;

    public String NETWORK_NAME = null;
    public String DOMINIO = null;

    @Override
    public void onEnable()
    {
        modulos = new Modulo[]
                {
                        new JoinDomain(this),
                        new ServerMotd(this)
                };

        console("§e------------------------------------------");
        console("§e------------- [BungeeMaster] -------------");
        console("§e------------------------------------------");

        try
        {
			configuraciones();
			iniciarModulos();
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuracion, new File(Datos.CARPETA_PLUGIN+Datos.CONFIG_NAME));
			//getProxy().getPluginManager().registerCommand(this, new ModuleCommand(this,"Modulo", Datos.PERMISO_ADMIN, "modulo", "module", "mdle", "md"));
		}
        catch (ClassNotFoundException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        catch (SQLException e){e.printStackTrace();}
    }

    @Override
    public void onDisable()
    {
        pararModulos();
        if(bd != null) bd.desconectar();
    }

    public Modulo getModulo(String name)
    {
        for (Modulo modulo : modulos)
            if (modulo.getNombre().equals(name))
            	return modulo;
        return null;
    }

    private void configuraciones() throws ClassNotFoundException, IOException, SQLException
    {
        File conf = new File(Datos.CARPETA_PLUGIN);
        if (!conf.exists())
        {
            conf.mkdir();
            new FileWriter(Datos.CARPETA_PLUGIN+Datos.CONFIG_NAME);

            configuracion = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Datos.CARPETA_PLUGIN+Datos.CONFIG_NAME));
            configuracion.set("propiedades.servidor", "NOMBRESERVIDOR");
            configuracion.set("propiedades.dominio", "mc.nombreservidor.com");

            configuracion.set("mysql.host", "169.0.0.1");
            configuracion.set("mysql.port", 3306);
            configuracion.set("mysql.name", "minecraft");
            configuracion.set("mysql.user", "root");
            configuracion.set("mysql.pass", "123abc");
            configuracion.set("mysql.prefix", "nameserver_");

            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuracion, new File(Datos.CARPETA_PLUGIN+Datos.CONFIG_NAME));
        }
        else
            configuracion = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Datos.CARPETA_PLUGIN+Datos.CONFIG_NAME));
        
        bd = new BBDD
		(
			configuracion.getString("mysql.host"),
			configuracion.getInt("mysql.port"),
			configuracion.getString("mysql.name"),
			configuracion.getString("mysql.user"),
			configuracion.getString("mysql.pass"),
			configuracion.getString("mysql.prefix")
		);
        //bd.conectar();

        NETWORK_NAME = configuracion.getString("propiedades.servidor");
        DOMINIO = configuracion.getString("propiedades.dominio");
        
        console("§b(!) Todas las configuraciones han sido cargadas con exito.");
    }

	private void iniciarModulos() throws IOException
    {
        console("§b(!) Listo para iniciar los modulos.");
        
        for (Modulo m : modulos)
        {
            String path = "modulos."+m.getNombre();
            if (configuracion.get(path) == null)
                configuracion.set(path, false);
            else
            {
            	if (configuracion.getBoolean(path))
            		m.iniciar();
            }
        }
        console("§b(!) Todos los modulos han sido iniciados, revisar consola en caso de error.");
    }

    private void pararModulos()
    {
        console("Â§c(!) Todos los modulos van a ser desactivados.");
        for (Modulo m : modulos)
            if (m.isActivado())
                m.finalizar();
    }

    public void console(String msg)
    {
        getLogger().info("[" + NETWORK_NAME + "] "+msg);
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
