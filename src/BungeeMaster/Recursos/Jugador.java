package BungeeMaster.Recursos;

import BungeeMaster.BungeeMaster;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Jugador
{
    private BungeeMaster bm;

    private ProxiedPlayer jugador;
    private String idioma;

    private boolean logueado;

    public Jugador(ProxiedPlayer j, BungeeMaster b)
    {
        this.bm = b;
        this.jugador = j;
        this.logueado = false;

        final String isNew = "SELECT idioma FROM "+bm.getBd().getPrefix()+ Datos.PLAYERS_TABLE+" WHERE nombre = '"+jugador.getName()+"';";
        try
        {
            ResultSet rs = bm.getBd().getConex().prepareStatement(isNew).executeQuery();
            if (rs.next())
                this.idioma = rs.getString("idioma");
            else
                this.idioma = "en";
        }
        catch (SQLException e)  { e.printStackTrace(); }
    }

    public void setLogueado(boolean a) {
        this.logueado = a;
    }

    public boolean isLogueado() {
        return logueado;
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
        enviarMensaje(bm.getMensajes().get(num, idioma));
    }

    public void enviarMensaje(BaseComponent[] a) {
        jugador.sendMessage(a);
    }
}
