package BungeeMaster.Listeners.Servidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Listeners.Modulo;
import BungeeMaster.Recursos.Config;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.event.AsyncEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.event.EventHandler;

@SuppressWarnings("deprecation")
public class ServerMotd extends Modulo
{
	private boolean hover;
	private String custom_slots;
	private UUID EMPTY_UUID;
	private String[] descripcion;
	private ArrayList<String> hoverdescrp;
	private Config config;
	
    public ServerMotd(BungeeMaster p)
    {
		super(p);
		
		this.EMPTY_UUID = UUID.fromString("0-0-0-0-0");
		this.config = new Config(getNombre());
		
		this.hover = false;
		this.custom_slots = "";
		this.descripcion = new String[2];
		this.hoverdescrp = new ArrayList<String>();
		
		/*callback = new Callback<ProxyPingEvent>()
		{
            @Override
            public void done(ProxyPingEvent event, Throwable throwable)
            {
                event.getResponse().setDescription("holiz");
            }
        };
        
        bungeeCord.getScheduler().schedule(getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                ServerPing ping = new ServerPing(new ServerPing.Protocol("protocolo 1", 2), new ServerPing.Players(10, 10, null), new TextComponent("hola"), null);
                
                bungeeCord.getPluginManager().callEvent(new ProxyPingEvent(scConnection, ping, callback));
            }
        }, 0L, 1L, java.util.concurrent.TimeUnit.SECONDS);*/
	}
    
    @EventHandler
    public void onProxyPingEvent(ProxyPingEvent event)
    {
    	ServerPing ping;
    	ServerPing.Players ppp;
    	ServerPing.PlayerInfo[] samples;
    	
    	
        if (getPlugin() != null && ( ping=event.getResponse()) != null)
        {
	    	ppp = event.getResponse().getPlayers();
	    	
	    	/*if(!custom_slots.isEmpty())
	    		ping.setVersion(new Protocol(ChatColor.translateAlternateColorCodes('&', replace(custom_slots, ppp)), 1));

	    	if(!descripcion[0].isEmpty() || !descripcion[1].isEmpty())
	    		ping.setDescription(ChatColor.translateAlternateColorCodes('&', replace(descripcion[0], ppp)+(((descripcion[0]+descripcion[1]).isEmpty())?"":"&r"+'\n')+replace(descripcion[1], ppp)));
	    	ping.setDescriptionComponent(new TextComponent("&3xx"));
	    	
	    	if(hover)
	    	{
	    		samples = new ServerPing.PlayerInfo[hoverdescrp.size()];
	    		
	    		int i = 0;
	    		for (String cad : hoverdescrp)
	    		{
	    			samples[i] = new ServerPing.PlayerInfo(ChatColor.translateAlternateColorCodes('&', replace(cad, ppp)), EMPTY_UUID);
	    			i++;
	    		}
		    	ppp.setSample(samples);
	    	}*/
	    	
	    	/*getPlugin().getProxy().getScheduler().schedule(getPlugin(), new Runnable()
	    	{
	    		int i = 0;
	    		
				@Override
				public void run()
				{
					ping.setDescriptionComponent(new TextComponent("&3"+i));
					getPlugin().console(String.valueOf(i));
					i++;
					
					event.setResponse(ping);
				}
			}, 1, 1, TimeUnit.SECONDS);*/
        }
    }

	@Override
	public void iniciar()
	{
		try
		{
			crearConfig();
			if(!isActivado())
			{
				super.iniciar();
				leerConfig();
			}
		}
		catch(IOException ex) {}
	}

	public void crearConfig() throws IOException
	{
		if(!config.createConfig())
		{
			LinkedList<String> hoverdescrp = new LinkedList<String>();
			
			hoverdescrp.add("Hover 1");
			hoverdescrp.add("Hover 2");
			
			
			config.setData("properties.hover", true);
			config.setData("properties.custom_slots", "");
			
			config.setData("description.line1", "- Linea 1");
			config.setData("description.line2", "- Linea 2");
			
			config.setData("hover", hoverdescrp);
			
			config.save();
		}
	}
	
	private void leerConfig()
	{
		this.hover = config.getBoolean("properties.hover");
		this.custom_slots = config.getString("properties.custom_slots");
		
		this.descripcion[0] = config.getString("description.line1");
		this.descripcion[1] = config.getString("description.line2");
		
		this.hoverdescrp = (ArrayList<String>) config.getStringList("hover");
	}
	
	private String replace(String cad, ServerPing.Players ppp)
	{
		return cad
			.replaceAll("%online%", String.valueOf(ppp.getOnline()))
			.replaceAll("%slots%", String.valueOf(ppp.getMax()))
			.replaceAll("%name%", String.valueOf(getPlugin().getNetworkName()));
	}

	@Override
	public void finalizar()
	{
		super.finalizar();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServerMotd [hover=" + hover + ", custom_slots=" + custom_slots + ", EMPTY_UUID=" + EMPTY_UUID
				+ ", descripcion=" + descripcion + ", hoverdescrp=" + hoverdescrp + ", config=" + config + "]";
	}
}
