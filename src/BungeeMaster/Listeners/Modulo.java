package BungeeMaster.Listeners;
import BungeeMaster.BungeeMaster;
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
        getPlugin().console(getPlugin().getMensajes().get(13, getPlugin().getIdiomaConsola()).replace("%modulename%", getNombre()));
	}

    public void finalizar()
    {
        getPlugin().unregisterListener(this);
        setActivado(false);
        getPlugin().console(getPlugin().getMensajes().get(14, getPlugin().getIdiomaConsola()).replace("%modulename%", getNombre()));
	}

    public final String getNombre()
    {
        return this.getClass().getSimpleName();
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) { this.activado = activado; }

    public BungeeMaster getPlugin() {
        return plugin;
    }
}
