package BungeeMaster.Listeners.Modulos.Seguridad;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Listeners.Modulo;
import BungeeMaster.Recursos.Config;
import BungeeMaster.Recursos.Datos;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AuthManager extends Modulo
{
    /** Rutas de la config */
    private static final String AUTH = "auth.";
    private static final String MAX_USERS_IP = "max_users_ip";

    private Config config;
    private int MAX_USERS_PER_IP;

    private ArrayList<Connections> conectados;

    public AuthManager(BungeeMaster plugin)
    {
        super(plugin);

        config = new Config(getNombre());
        conectados = new ArrayList<>();
    }

    @Override
    public void iniciar()
    {
        if (!isActivado())
        {
            super.iniciar();
            leerConfig();
        }
    }

    @Override
    public void crearConfig() throws IOException
    {
        if (config.createConfig())
        {
            config.setData(AUTH+MAX_USERS_IP, 2);
            config.save();
        }
    }

    private void leerConfig()
    {
        MAX_USERS_PER_IP = config.getInteger(AUTH+MAX_USERS_IP);
    }

    @EventHandler(priority = EventPriority.HIGHEST) //Comprobar si el usuario ya está conectado y comprobar si la ip ya coincide
    public void onJoin(PostLoginEvent e)
    {
        Connections con = getConnection(e.getPlayer().getAddress().getHostString());
        if (con != null)
        {
            if (!con.canJoin() && !e.getPlayer().hasPermission(Datos.PERMISO_ADMIN))
            {
                e.getPlayer().disconnect(getPlugin().getMensajes().get(28, getPlugin().getJugador(e.getPlayer()).getIdioma()));
            }
            else
                con.addConnex();
        }
        else
            conectados.add(new Connections(e.getPlayer().getAddress().getHostString()));
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e)
    {
        if (e.getTag().equalsIgnoreCase("BungeeCord"))
        {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
            try
            {
                final String canal = in.readUTF();

                if (canal.equals("Logueado"))
                {
                    final String pName = in.readUTF();
                    getPlugin().getJugador(pName, "name").setLogueado(true);
                }
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    private Connections getConnection(String IP)
    {
        for (Connections cons : conectados)
            if (cons.getIP().equals(IP))
                return cons;
        return null;
    }

    @Override
    public void finalizar()
    {
        super.finalizar();
    }

    class Connections
    {
        private String IP;
        private int conex;

        public Connections(String IP)
        {
            this.IP = IP;
        }

        private boolean canJoin()
        {
            return (conex == 2) ? false : true;
        }

        public String getIP() {
            return IP;
        }

        public void setIP(String IP) {
            this.IP = IP;
        }

        public int getConex() {
            return conex;
        }

        public void addConnex() {
            this.conex++;
        }
    }
}
