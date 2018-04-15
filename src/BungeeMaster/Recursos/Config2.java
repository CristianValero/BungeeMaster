package BungeeMaster.Recursos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config2
{
	private static final Class<YamlConfiguration> clase = YamlConfiguration.class;
	
	private File ruta;
	private Configuration configuracion;
	
	public Config2(String nombre) throws IOException
	{
		ruta = new File(Datos.CARPETA_PLUGIN+nombre);
		crear();
		load();
	}
	
	private ConfigurationProvider getPro()
	{
		return ConfigurationProvider.getProvider(clase);
	}
	
	private void crear() throws IOException
	{
		File conf = new File(Datos.CARPETA_PLUGIN);
        if (!conf.exists()) conf.mkdir();
        if(!ruta.exists()) new FileWriter(ruta);
	}
	
	public void load() throws IOException
	{
		configuracion = getPro().load(ruta);
	}
	
	public void save() throws IOException
	{
		getPro().save(configuracion, ruta);
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

    public void setData(String path, Object data)
    {
        configuracion.set(path, data);
    }
}
