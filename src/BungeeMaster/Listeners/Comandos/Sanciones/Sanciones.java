package BungeeMaster.Listeners.Comandos.Sanciones;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Listeners.Modulo;
import BungeeMaster.Recursos.Datos;
import BungeeMaster.Recursos.Jugador;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.sql.SQLException;

public class Sanciones extends Modulo
{
    /** Comandos disponibles */
    private Alert alert;
    private Kick kick;
    private Mute mute;
    private Ban ban;
    private Unban unban;
    private Unmute unmute;

    public Sanciones(BungeeMaster plugin)
    {
        super(plugin);

        try
        {
            ban = new Ban(null, null, null, null);
            alert = new Alert(null, null, null, null);
            kick = new Kick(null, null, null, null);
            mute = new Mute(null, null, null, null);
            unban = new Unban(null, null, null, null);
            unmute = new Unmute(null, null, null, null);
        }
        catch (SQLException e)
        { e.printStackTrace(); }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void alEntrar(LoginEvent e)
    {
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(e.getConnection().getUniqueId());
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onChat(ChatEvent e)
    {
        Jugador j = getPlugin().getJugador(e.getSender().getAddress().getHostString(), "ip");
        if (j != null && j.isLogueado() && !e.isCancelled())
        {
            if (Mute.isUserMuted(j.getJugador()))
            {
                e.setCancelled(true);
                j.enviarMensaje(getPlugin().getMensajes().get(29,
                        j.getIdioma()).replaceAll(Datos.MINUTOS, String.valueOf(Mute.getRemainingMinutes(j.getJugador()))));
            }
        }
    }

    @Override
    public void iniciar()
    {
        if (!isActivado())
        {
            super.iniciar();

            getPlugin().getProxy().getPluginManager().registerCommand(getPlugin(), alert);
            getPlugin().getProxy().getPluginManager().registerCommand(getPlugin(), kick);
            getPlugin().getProxy().getPluginManager().registerCommand(getPlugin(), mute);
            mute.iniciarLimpiezaMutes();
            getPlugin().getProxy().getPluginManager().registerCommand(getPlugin(), ban);
            getPlugin().getProxy().getPluginManager().registerCommand(getPlugin(), unban);
            getPlugin().getProxy().getPluginManager().registerCommand(getPlugin(), unmute);
        }
    }

    @Override
    public void finalizar()
    {
        super.finalizar();

        getPlugin().getProxy().getPluginManager().unregisterCommand(alert);
        getPlugin().getProxy().getPluginManager().unregisterCommand(kick);
        getPlugin().getProxy().getPluginManager().unregisterCommand(mute);
        mute.detenerLimpiezaMutes();
        getPlugin().getProxy().getPluginManager().unregisterCommand(ban);
        getPlugin().getProxy().getPluginManager().unregisterCommand(unban);
        getPlugin().getProxy().getPluginManager().unregisterCommand(unmute);
    }
}
