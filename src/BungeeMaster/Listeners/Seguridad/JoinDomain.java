package BungeeMaster.Listeners.Seguridad;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Listeners.Modulo;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.event.EventHandler;

public class JoinDomain extends Modulo
{
    public JoinDomain(BungeeMaster p)
    {
        super(p);
    }

    @Override
    public void iniciar()
    {
        if (!isActivado())
        {
            getPlugin().console("&eIniciando modulo: "+getNombre());

            setActivado(true);
        }
    }

    @EventHandler
    public void alEntrar(LoginEvent e)
    {
        if (!e.getConnection().getVirtualHost().getHostName().equals(getPlugin().DOMINIO))
        {
            e.setCancelled(true); //Denegamos el acceso al Servidor
            e.setCancelReason(); //Establecemos un mensaje de kick
        }
    }

    @Override
    public void finalizar()
    {
        getPlugin().console("&cDesactivando modulo: "+getNombre());
        setActivado(false);
    }
}
