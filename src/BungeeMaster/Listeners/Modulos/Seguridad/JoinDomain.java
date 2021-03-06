package BungeeMaster.Listeners.Modulos.Seguridad;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Listeners.Modulo;
import BungeeMaster.Recursos.Datos;
import net.md_5.bungee.api.ChatColor;
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
        if (!e.getConnection().getVirtualHost().getHostName().equals(getPlugin().getDominio()) && getPlugin() != null)
        {
            String kickReason = getPlugin().getMensajes().get(15, getPlugin().getIdiomaConsola())
                    .replace(Datos.SERVER_NAME, getPlugin().getDominio())
                    .replace(Datos.SERVER_DOMAIN, getPlugin().getNetworkName());

            e.setCancelled(true); //Denegamos el acceso al Servidor
            e.setCancelReason(ChatColor.translateAlternateColorCodes('&', kickReason)); //Establecemos un mensaje de kick

            getPlugin().console(getPlugin().getMensajes().get(18, getPlugin().getIdiomaConsola()).replace(Datos.USER_NAME, e.getConnection().getName())
                    .replace(Datos.IP_PLAYER, e.getConnection().getAddress().getHostString()));

            //System.out.println("Hay: "+BungeeCord.getInstance().getOnlineCount()+"/"+BungeeCord.getInstance().getConfig().getPlayerLimit());
        }
    }

    @Override
    public void finalizar()
    {
        super.finalizar();
    }
}
