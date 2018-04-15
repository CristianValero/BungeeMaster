package BungeeMaster.Listeners.Servidor;

import java.util.UUID;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Listeners.Modulo;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.event.EventHandler;

@SuppressWarnings("deprecation")
public class ServerMotd extends Modulo
{
	
    public ServerMotd(BungeeMaster p)
    {
		super(p);
	}

	@EventHandler
    public void onProxyPingEvent(ProxyPingEvent event)
    {
    	UUID EMPTY_UUID = UUID.fromString("0-0-0-0-0");
    	ServerPing ping;
    	ServerPing.Players ppp;
    	//ServerPing.PlayerInfo[] samples;

        if (getPlugin() != null && ( ping=event.getResponse()) != null)
        {
	    	ppp = event.getResponse().getPlayers();
	    	
	    	ppp.setSample(new ServerPing.PlayerInfo[]
			{
				new ServerPing.PlayerInfo(ChatColor.GOLD+"AAAAAAAA", EMPTY_UUID),
				new ServerPing.PlayerInfo(ChatColor.BLUE+"------------------------------------", EMPTY_UUID),
				new ServerPing.PlayerInfo(ChatColor.RED+"BBBBBBBBBB", EMPTY_UUID),
				new ServerPing.PlayerInfo(ChatColor.RED+"BBBBBBBBBB", EMPTY_UUID),
				new ServerPing.PlayerInfo(ChatColor.RED+"BBBBBBBBBB", EMPTY_UUID),
				new ServerPing.PlayerInfo(ChatColor.RED+"BBBBBBBBBB", EMPTY_UUID),
				new ServerPing.PlayerInfo(ChatColor.RED+"BBBBBBBBBB", EMPTY_UUID),
				new ServerPing.PlayerInfo(ChatColor.RED+"BBBBBBBBBB", EMPTY_UUID)
			});

	    	ping.setVersion(new Protocol("§b§l» §6"+ping.getPlayers().getOnline()+"§f/§6"+ping.getPlayers().getMax(), 3));

	    	ping.setDescription("holis"); //Motd
        }
    }

	@Override
	public void iniciar()
	{
		if(!isActivado())
		{
			super.iniciar();
		}
	}

	@Override
	public void finalizar()
	{
		super.finalizar();
	}
}
