package BungeeMaster.Listeners.Comandos;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Listeners.Modulo;
import BungeeMaster.Recursos.Datos;
import BungeeMaster.Recursos.PlayerData.Jugador;
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

            try
            {
                switch (args[0].toLowerCase())
                {
                    case "enable":
                        activarModulo(args);
                        break;
                    case "disable":
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

    private void activarModulo(String[] args) throws IOException
    {
        if (args.length == 0)
            jugador.enviarMensaje(pl.getMensajes().get(5, jugador.getIdioma()));
        else
        {
            Modulo escrito = pl.getModulo(args[1]);
            if (escrito != null)
            {
                escrito.iniciar();

                pl.getMainConfig().setData("modules."+escrito.getNombre()+".activated", true).save();

                jugador.enviarMensaje(pl.getMensajes().get(13, jugador.getIdioma())
                .replace(Datos.MODULE_NAME, escrito.getNombre()));

                pl.console(ChatColor.GREEN, 21);
            }
            else
                jugador.enviarMensaje(pl.getMensajes().get(20, jugador.getIdioma()));
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

            if (m.isActivado())
            {
                nmbre = new TextComponent(m.getNombre());
                nmbre.setColor(ChatColor.GREEN);
                final String atb1 = pl.getMensajes().get(5, jugador.getIdioma())
                        .replaceAll(Datos.MODULE_NAME, m.getNombre()).toUpperCase();
                final String atb2 = "§7"+pl.getMensajes().get(8, jugador.getIdioma())+": "+
                        "§a"+pl.getMensajes().get(0, jugador.getIdioma()).toUpperCase();
                String atributo = atb1 + "\n\n" + atb2;
                mensaje.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(atributo).color(ChatColor.YELLOW).bold(true).create()));
            }
            else
            {
                nmbre = new TextComponent(m.getNombre());
                nmbre.setColor(ChatColor.GREEN);
                final String atb1 = pl.getMensajes().get(5, jugador.getIdioma())
                        .replaceAll(Datos.MODULE_NAME, m.getNombre()).toUpperCase();
                final String atb2 = "§7"+pl.getMensajes().get(8, jugador.getIdioma())+": "+
                        "§c"+pl.getMensajes().get(0, jugador.getIdioma()).toUpperCase();
                String atributo = atb1 + "\n\n" + atb2;
                mensaje.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(atributo).color(ChatColor.YELLOW).bold(true).create()));
            }
            jugador.enviarMensaje(mensaje);
        }
    }

    private void enviarDecision(CommandSender sender, String comando)
    {
        TextComponent decision = new TextComponent("[SI] ");
        decision.setColor(ChatColor.GREEN);
        decision.setBold(true);
        decision.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§a§lSI\n\n§7Click para §aaceptar §7la acción.").create()));
        decision.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, comando));

        TextComponent negacion = new TextComponent("[NO]");
        negacion.setColor(ChatColor.RED);
        negacion.setBold(true);
        negacion.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§c§lNO\n\n§7Click para §cdenegar §7la acción.").create()));

        decision.addExtra(negacion);

        sender.sendMessage(decision);
    }
}
