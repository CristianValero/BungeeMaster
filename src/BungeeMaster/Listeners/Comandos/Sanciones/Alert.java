package BungeeMaster.Listeners.Comandos.Sanciones;

import BungeeMaster.BungeeMaster;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class Alert extends Command
{
    private BungeeMaster bungeeMaster;

    public Alert(BungeeMaster bungeeMaster, String name, String permission, String... aliases)
    {
        super(name, permission, aliases);
        this.bungeeMaster = bungeeMaster;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {

    }
}
