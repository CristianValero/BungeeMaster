package BungeeMaster.Listeners;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Recursos.Jugador;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class MainListeners implements Listener
{
    private BungeeMaster pl;

    public MainListeners(BungeeMaster bm)
    {
        this.pl = bm;
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onLogin(PreLoginEvent e)
    {
        if (pl != null)
        {
            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(e.getConnection().getUniqueId());
            pl.addJugador(new Jugador(p, pl));
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onDisconnect(PlayerDisconnectEvent e)
    {
        pl.remJugador(e.getPlayer().getName());
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onChat(ChatEvent e)
    {
        Jugador j = pl.getJugador(e.getSender().getAddress().getHostString(), "ip");
        if (!j.isLogueado())
            e.setCancelled(true);
    }
}
