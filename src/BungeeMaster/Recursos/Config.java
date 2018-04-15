package BungeeMaster.Recursos;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Config
{
	private static final Class<YamlConfiguration> clase = YamlConfiguration.class;
	
    private Configuration configuracion;
    private String name;

    public Config(String name)
    {
    	this.configuracion = null;
        this.name = name+".yml";
    }

    public boolean createConfig() throws IOException
    {
    	boolean arr = false;
    	
        File dir = new File(Datos.CARPETA_PLUGIN);
        File conf = new File(dir.getPath(), name);
        if (dir.exists())
        {
            if (!conf.exists())
            	new FileWriter(conf).close();
            else
            	arr =  true;
        }
        else
        {
        	dir.mkdir();
            new FileWriter(conf).close();
        }
        load();
        return arr;
    }
    
    public void load() throws IOException
    {
        configuracion = ConfigurationProvider.getProvider(clase).load(new File(Datos.CARPETA_PLUGIN+name));
    }

    public void save() throws IOException
    {
        ConfigurationProvider.getProvider(clase).save(configuracion, new File(Datos.CARPETA_PLUGIN+name));
    }

    public List<?> getList(String path)
    {
        return configuracion.getList(path);
    }

    public Object getObject(String path)
    {
        return configuracion.get(path);
    }

    public boolean getBoolean(String path)
    {
        return configuracion.getBoolean(path);
    }

    public int getInteger(String path)
    {
        return configuracion.getInt(path);
    }

    public String getString(String path)
    {
        return configuracion.getString(path);
    }

    public Config setData(String path, Object data)
    {
        configuracion.set(path, data);
        return this;
    }
}
