package BungeeMaster.Listeners.Comandos;

/**import com.network.bungeemaster.BungeeMaster;
import com.network.bungeemaster.listeners.Modulo;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

public class ModuleCommand extends Command
{
    private BungeeMaster pl;

    public ModuleCommand(BungeeMaster pl, String name, String permission, String... aliases)
    {
        super(name, permission, aliases);
        this.pl = pl;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args)
    {
        if (!(commandSender instanceof ConsoleCommandSender))
        {
            switch (args[0].toLowerCase())
            {
                case "enable":
                    activarModulo(commandSender, args);
                    break;
                case "disable":
                    break;
                case "list":
                    enviarListaModulos(commandSender);
                    break;
                default:
                    playerData.sendMessage(Mensaje.SUGERENCIA_COMANDO);
                    playerData.sendMessage("§c - /bmd list");
                    playerData.sendMessage("§c - /bmd enable <module>");
                    playerData.sendMessage("§c - /bmd disable <module>");
                    break;
            }
        }
    }

    private void activarModulo(CommandSender commandSender, String[] args)
    {
        if (args.length == 0)
            .sendMessage("§cDebes especificar un nombre de módulo válido.");
        else
        {
            Modulo escrito = pl.getModulo(args[1]);
            if (escrito != null)
            {
                .sendMessage();
            }
            else
                errorMessage(commandSender, );
        }
    }

    private void enviarListaModulos(CommandSender commandSender)
    {
        TextComponent mensaje = new TextComponent("Mostrando módulos de BungeeCord: ");
        mensaje.setColor(ChatColor.YELLOW);
        commandSender.sendMessage(mensaje);

        for (Modulo m : pl.getModulos())
        {
            mensaje = new TextComponent(" - "+m.getNombre());
            if (m.isActivado())
            {
                mensaje.setColor(ChatColor.GREEN);
                String atributo = "§e§lINFORMACIÓN DE "+m.getNombre().toUpperCase()+"\n\n§7Activado: §aSI";
                mensaje.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(atributo).create()));
            }
            else
            {
                mensaje.setColor(ChatColor.RED);
                String atributo = "§e§lINFORMACIÓN DE "+m.getNombre().toUpperCase()+"\n\n§7Activado: §cNO";
                mensaje.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(atributo).create()));
            }
            commandSender.sendMessage(mensaje);
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
}*/
