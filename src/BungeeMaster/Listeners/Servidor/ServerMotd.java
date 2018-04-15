package BungeeMaster.Listeners.Servidor;

import java.awt.List;
import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Librerias.Lista;
import BungeeMaster.Listeners.Modulo;
import BungeeMaster.Recursos.Config;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.event.EventHandler;

@SuppressWarnings("deprecation")
public class ServerMotd extends Modulo
{
	private boolean hover;
	private boolean custom_slots;
	private UUID EMPTY_UUID;
	private Lista<String> descripcion;
	private Lista<String> hoverdescrp;
	private Config config;
	
    public ServerMotd(BungeeMaster p)
    {
		super(p);
		
		this.hover = false;
		this.custom_slots = false;
		this.EMPTY_UUID = UUID.fromString("0-0-0-0-0");
		this.descripcion = new Lista<String>();
		this.hoverdescrp = new Lista<String>();
		this.config = new Config(getNombre());
	}

	@EventHandler
    public void onProxyPingEvent(ProxyPingEvent event)
    {
		int max, online;
		String server_name;
		
    	ServerPing ping;
    	ServerPing.Players ppp;
    	ServerPing.PlayerInfo[] samples = new ServerPing.PlayerInfo[4];

        if (getPlugin() != null && ( ping=event.getResponse()) != null)
        {
	    	ppp = event.getResponse().getPlayers();
	    	max = ppp.getMax();
	    	online = ppp.getOnline();
	    	server_name = getPlugin().getNetworkName();
	    	
	    	
	    	for (int i = 0; i < 10; i++)
	    	{
				samples[i] = new ServerPing.PlayerInfo(ChatColor.GOLD+"AAAAAAAA", EMPTY_UUID);
			}
	    	ppp.setSample(samples);

	    	if(custom_slots)
	    		ping.setVersion(new Protocol("§b§l» §6"+ping.getPlayers().getOnline()+"§f/§6"+ping.getPlayers().getMax(), 1));

	    	ping.setDescription("holis"); //Motd
        }
    }

	@Override
	public void iniciar()
	{
		try
		{
			if(!isActivado())
			{
				super.iniciar();
				if(!config.createConfig())
					crearConfig();
			}
		}
		catch(IOException ex) {}
	}

	private void crearConfig()
	{
		LinkedList<String> aux = new LinkedList<String>();
		aux.add("holis");
		aux.add(" asdf asdf a9so2 0edsopo ss");
		
		
		config.setData("general.hover", "false");
		config.setData("general.custom_slots", "false");
		config.setData("prueba", aux);
	}

	@Override
	public void finalizar()
	{
		super.finalizar();
	}
}
