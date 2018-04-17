package BungeeMaster.Listeners;
import java.io.IOException;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Recursos.Datos;
import net.md_5.bungee.api.plugin.Listener;

public abstract class Modulo implements Listener
{
    private BungeeMaster plugin;
    private boolean activado;

    public Modulo(BungeeMaster plugin)
    {
        this.plugin = plugin;
        activado = false;
    }

    public void iniciar()
    {
    	getPlugin().registerListener(this);
    	setActivado(true);
    	console(13);
	}

    public void finalizar()
    {
        getPlugin().unregisterListener(this);
        setActivado(false);
        console(14);
	}

    public final String getNombre()
    {
        return this.getClass().getSimpleName();
    }

    public boolean isActivado() {
        return activado;
    }
    
    public void console(int num)
    {
    	getPlugin().console(getPlugin().getMensajes().get(num, getPlugin().getIdiomaConsola()).replace(Datos.MODULE_NAME, getNombre()));
    }

    public void setActivado(boolean activado)
    {
    	this.activado = activado;
    }

    public BungeeMaster getPlugin() {
        return plugin;
    }

    public void crearConfig() throws IOException {}
}
