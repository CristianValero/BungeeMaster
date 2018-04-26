package BungeeMaster.Listeners.Comandos;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Listeners.Modulo;
import BungeeMaster.Recursos.Datos;
import BungeeMaster.Recursos.Jugador;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;

public class ModuleCommand extends Command
{
    private BungeeMaster pl;

    private Jugador jugador;

    public ModuleCommand(BungeeMaster pl, String name, String permission, String... aliases)
    {
        super(name, permission, aliases);
        this.pl = pl;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args)
    {
        if (commandSender instanceof ProxiedPlayer)
        {
            jugador = pl.getJugador((ProxiedPlayer) commandSender);

            if (jugador.isLogueado())
            {
                try
                {
                    switch (args[0].toLowerCase())
                    {
                        case "enable":
                            accionModulo(args, true);
                            break;
                        case "disable":
                            accionModulo(args, false);
                            break;
                        case "list":
                            enviarListaModulos();
                            break;
                        default:
                            jugador.enviarMensaje(4);
                            jugador.enviarMensaje("§c - /bmd list");
                            jugador.enviarMensaje("§c - /bmd enable <module>");
                            jugador.enviarMensaje("§c - /bmd disable <module>");
                            break;
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void accionModulo(String[] args, boolean activar) throws IOException
    {
        if (args.length == 0)
            jugador.enviarMensaje(pl.getMensajes().get(5, jugador.getIdioma()));
        else if (args.length == 1)
        {
            Modulo escrito = pl.getModulo(args[1]);
            if (escrito != null)
            {
                if (activar)
                {
                    if (escrito.isActivado())
                    {
                        jugador.enviarMensaje(pl.getMensajes().get(24, jugador.getIdioma())
                                .replace(Datos.MODULE_NAME, escrito.getNombre()));
                        return;
                    }

                    enviarDecision(23, escrito.getNombre(), "bmd enable "+escrito.getNombre()+" true");
                }
                else
                {
                    if (!escrito.isActivado())
                    {
                        jugador.enviarMensaje(pl.getMensajes().get(25, jugador.getIdioma())
                                .replace(Datos.MODULE_NAME, escrito.getNombre()));
                        return;
                    }
                }

                enviarDecision(26, escrito.getNombre(), "bmd disable "+escrito.getNombre()+" true");
            }
            else
                jugador.enviarMensaje(pl.getMensajes().get(20, jugador.getIdioma()));
        }
        else if (args.length == 2 && args[2].equals("true"))
        {
            Modulo escrito = pl.getModulo(args[1]);
            if (activar)
            {
                escrito.iniciar();
                pl.getMainConfig().setData("modules."+escrito.getNombre(), true).save();
                jugador.enviarMensaje(pl.getMensajes().get(13, jugador.getIdioma())
                        .replace(Datos.MODULE_NAME, escrito.getNombre()));
                pl.console(ChatColor.GREEN, 21);
            }
            else
            {
                escrito.finalizar();
                pl.getMainConfig().setData("modules."+escrito.getNombre(), false).save();
                jugador.enviarMensaje(pl.getMensajes().get(14, jugador.getIdioma())
                        .replace(Datos.MODULE_NAME, escrito.getNombre()));
                pl.console(ChatColor.GREEN, 27);
            }
        }
    }

    private void enviarListaModulos()
    {
        TextComponent mensaje = new TextComponent("Mostrando módulos de BungeeCord: "), nmbre;
        mensaje.setColor(ChatColor.YELLOW);
        jugador.enviarMensaje(mensaje);

        for (Modulo m : pl.getModulos())
        {
            mensaje = new TextComponent(" - ");
            mensaje.setColor(ChatColor.GRAY);

            nmbre = new TextComponent(m.getNombre());
            final String atb1 = "§e§l"+pl.getMensajes().get(7, jugador.getIdioma())
                    .replaceAll(Datos.MODULE_NAME, m.getNombre()).toUpperCase();

            String atributo = "", atb2 = "";
            if (m.isActivado())
            {
                nmbre.setColor(ChatColor.GREEN);
                atb2 = "§7"+pl.getMensajes().get(8, jugador.getIdioma())+": "+
                        "§a§l"+pl.getMensajes().get(0, jugador.getIdioma()).toUpperCase();
            }
            else
            {
                nmbre.setColor(ChatColor.RED);
                atb2 = "§7"+pl.getMensajes().get(8, jugador.getIdioma())+": "+
                        "§c§l"+pl.getMensajes().get(1, jugador.getIdioma()).toUpperCase();
            }

            atributo = atb1 + "\n\n" + atb2;
            mensaje.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(atributo).create()));
            jugador.enviarMensaje(mensaje);
        }
    }

    private void enviarDecision(int msg, String mName, String comando)
    {
        jugador.enviarMensaje(pl.getMensajes().get(msg, jugador.getIdioma()).replace(Datos.MODULE_NAME, mName));

        TextComponent decision = new TextComponent("[" + pl.getMensajes().get(0, jugador.getIdioma()).toUpperCase() + "]");
        decision.setColor(ChatColor.GREEN);
        decision.setBold(true);
        decision.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(pl.getMensajes().get(9, jugador.getIdioma()))
                        .create()));
        decision.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, comando));

        TextComponent negacion = new TextComponent("[NO]");
        negacion.setColor(ChatColor.RED);
        negacion.setBold(true);
        negacion.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(pl.getMensajes().get(22, jugador.getIdioma()))
                        .create()));

        decision.addExtra(negacion);

        jugador.enviarMensaje(decision);
    }
}
