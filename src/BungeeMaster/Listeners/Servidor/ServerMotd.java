package BungeeMaster.Listeners.Servidor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Listeners.Modulo;
import BungeeMaster.Recursos.Config;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.event.EventHandler;

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
	}
    
    @SuppressWarnings("deprecation")
	@EventHandler
    public void onProxyPingEvent(ProxyPingEvent event)
    {
    	ServerPing ping;
    	ServerPing.Players ppp;
    	ServerPing.PlayerInfo[] samples;
    	
    	
        if (getPlugin() != null && ( ping=event.getResponse()) != null)
        {
	    	ppp = event.getResponse().getPlayers();
	    	
	    	if(!custom_slots.isEmpty())
	    		ping.setVersion(new Protocol(ChatColor.translateAlternateColorCodes('&', getPlugin().replace(custom_slots)), 1));

	    	if(!descripcion[0].isEmpty() || !descripcion[1].isEmpty())
	    		ping.setDescription(ChatColor.translateAlternateColorCodes('&', getPlugin().replace(descripcion[0])+(((descripcion[0]+descripcion[1]).isEmpty())?"":"&r"+'\n')+getPlugin().replace(descripcion[1])));
	    	
	    	if(hover)
	    	{
	    		samples = new ServerPing.PlayerInfo[hoverdescrp.size()];
	    		
	    		int i = 0;
	    		for (String cad : hoverdescrp)
	    		{
	    			samples[i] = new ServerPing.PlayerInfo(ChatColor.translateAlternateColorCodes('&', getPlugin().replace(cad)), EMPTY_UUID);
	    			i++;
	    		}
		    	ppp.setSample(samples);
	    	}
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
				leerConfig();
				super.iniciar();
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
	
	@Override
	public void finalizar()
	{
		super.finalizar();
	}

	@Override
	public String toString() {
		return "ServerMotd [hover=" + hover + ", custom_slots=" + custom_slots + ", EMPTY_UUID=" + EMPTY_UUID
				+ ", descripcion=" + descripcion + ", hoverdescrp=" + hoverdescrp + ", config=" + config + "]";
	}
}
