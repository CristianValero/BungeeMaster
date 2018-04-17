package BungeeMaster.Recursos.PlayerData;

import BungeeMaster.BungeeMaster;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Jugador
{
    private BungeeMaster bungeeMaster;

    private ProxiedPlayer jugador;
    private String idioma;

    public Jugador(ProxiedPlayer j, BungeeMaster b)
    {
        this.bungeeMaster = b;

        this.jugador = j;
        this.idioma = "en";
    }

    public ProxiedPlayer getJugador() {
        return jugador;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void enviarMensaje(Object msg) {
        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) msg));
    }

    public void enviarMensaje(int num) {
        enviarMensaje(bungeeMaster.getMensajes().get(num, idioma));
    }

    public void enviarMensaje(BaseComponent[] a) {
        jugador.sendMessage(a);
    }
}
