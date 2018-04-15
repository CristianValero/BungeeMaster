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
            super.iniciar();
        }
    }

    @EventHandler
    public void alEntrar(LoginEvent e)
    {
        if (!e.getConnection().getVirtualHost().getHostName().equals(getPlugin().getDominio()))
        {
            final String dominio = getPlugin().getDominio();
            final String nombreServidor = getPlugin().getNetworkName();
            String kickReason = getPlugin().getMensajes().get(15, "en").replace("%servername%", nombreServidor);
            kickReason = kickReason.replace("%serverdomain%", dominio);

            e.setCancelled(true); //Denegamos el acceso al Servidor
            e.setCancelReason(kickReason); //Establecemos un mensaje de kick
        }
    }

    @Override
    public void finalizar()
    {
        super.finalizar();
    }
}
