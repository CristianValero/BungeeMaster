package BungeeMaster.Listeners;
import java.io.IOException;

import BungeeMaster.BungeeMaster;
import net.md_5.bungee.api.ChatColor;
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
    	console(ChatColor.GREEN, 13);
	}

    public void finalizar()
    {
        getPlugin().unregisterListener(this);
        setActivado(false);
        console(ChatColor.GREEN, 14);
	}

    public final String getNombre()
    {
        return this.getClass().getSimpleName();
    }

    public boolean isActivado() {
        return activado;
    }
    
    public void console(ChatColor color, int num)
    {
    	getPlugin().console(color, getPlugin().getMensajes().get(num, getPlugin().getIdiomaConsola()).replace("%modulename%", getNombre()));
    }

    public void setActivado(boolean activado)
    {
    	this.activado = activado;
    }

    public BungeeMaster getPlugin() {
        return plugin;
    }
    
    public void crearConfig() throws IOException{}
}
