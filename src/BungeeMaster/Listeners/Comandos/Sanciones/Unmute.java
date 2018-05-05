package BungeeMaster.Listeners.Comandos.Sanciones;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Recursos.Datos;
import BungeeMaster.Recursos.Jugador;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Unmute extends Command
{
    private BungeeMaster pl;

    public Unmute(BungeeMaster bungeeMaster, String name, String permission, String... aliases)
    {
        super(name, permission, aliases);
        this.pl = bungeeMaster;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args)
    {
        if (commandSender instanceof ProxiedPlayer)
        {
            Jugador jugador = pl.getJugador((ProxiedPlayer) commandSender);

            if (jugador.isLogueado() && jugador.getJugador().hasPermission(Datos.PERMISO_MODERADOR))
            {
                if (args.length == 0)
                    jugador.enviarMensaje(40);
                else
                {
                    Jugador muteado = pl.getJugador(args[0], "name");
                    if (muteado == null)
                        jugador.enviarMensaje(pl.getMensajes().get(41, jugador.getIdioma())
                                .replaceAll(Datos.USER_NAME, args[0]));
                    else
                    {
                        Mute.deleteMute(muteado);

                        muteado.enviarMensaje(42);

                        jugador.enviarMensaje(pl.getMensajes().get(43, jugador.getIdioma())
                                .replaceAll(Datos.USER_NAME, muteado.getJugador().getName()));

                        pl.console(pl.getMensajes().get(44, pl.getIdiomaConsola())
                                .replaceAll(Datos.MODNAME, jugador.getJugador().getName())
                                .replaceAll(Datos.USER_NAME, muteado.getJugador().getName()));
                    }
                }
            }
            else
                jugador.enviarMensaje(2);
        }
    }
}
