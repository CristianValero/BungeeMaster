package BungeeMaster.Listeners.Comandos.Sanciones;

import BungeeMaster.BungeeMaster;
import BungeeMaster.Recursos.Datos;
import BungeeMaster.Recursos.Jugador;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Mute extends Command
{
    private static HashMap<UUID, MuteInfo> mutes = new HashMap<UUID, MuteInfo>();
    private static final String tableName = "mutes";

    private BungeeMaster pl;
    private Expireds expireds;

    public Mute(BungeeMaster bm, String name, String permission, String... aliases) throws SQLException
    {
        super(name, permission, aliases);
        this.pl = bm;

        final String table = "CREATE TABLE IF NOT EXISTS "+pl.getBd().getPrefix()+tableName +
                " (id INT PRIMARY KEY AUTO_INCREMENT, muted_username VARCHAR(16), muted_uuid VARCHAR(50), mutedby_username VARCHAR(16), mutedby_uuid VARCHAR(50), duration INT, day VARCHAR(15), hour VARCHAR(10))" +
                " Engine=InnoDB;";
        pl.getBd().getConex().prepareStatement(table).execute();
    }

    @Override
    public void execute(CommandSender commandSender, String[] args)
    {
        if (commandSender instanceof ProxiedPlayer)
        {
            Jugador jugador = pl.getJugador((ProxiedPlayer) commandSender);

            if (jugador.isLogueado() && jugador.getJugador().hasPermission(Datos.PERMISO_MODERADOR))
            {
                if (args.length <= 2)
                    jugador.enviarMensaje(30);
                else
                {
                    Jugador muteado = pl.getJugador(args[0], "name");
                    if (muteado == null)
                        jugador.enviarMensaje(pl.getMensajes().get(31, jugador.getIdioma()).replaceAll(Datos.USER_NAME, args[0]));
                    else
                    {
                        if (!isUserMuted(muteado.getJugador()))
                        {
                            jugador.enviarMensaje(pl.getMensajes().get(38, jugador.getIdioma()).replaceAll(Datos.USER_NAME, args[0]));
                            return;
                        }

                        try
                        {
                            final int time = Integer.parseInt(args[1]);
                            if (time > 10 || time == 0)
                            {
                                jugador.enviarMensaje(32);
                                return;
                            }

                            final String reason = getReason(args);

                            MuteInfo muteInfo = new MuteInfo(jugador, muteado, reason, time);

                            mutes.put(muteado.getJugador().getUniqueId(), muteInfo);

                            muteado.enviarMensaje(pl.getMensajes().get(33, jugador.getIdioma())
                                    .replaceAll(Datos.REASON, reason)
                                    .replaceAll(Datos.MODNAME, jugador.getJugador().getName())
                                    .replaceAll(Datos.TIME, String.valueOf(time)));

                            jugador.enviarMensaje(pl.getMensajes().get(39, jugador.getIdioma())
                                    .replaceAll(Datos.USER_NAME, muteado.getJugador().getName())
                                    .replaceAll(Datos.TIME, String.valueOf(time)));

                            for (Jugador logueado : pl.getJugadores()) //Enviamos un mensaje a todos los jugadores logueados de que hemos muteado a un jugador.
                                if (logueado.isLogueado())
                                    logueado.enviarMensaje(34);
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                            jugador.enviarMensaje(35);
                        }
                    }
                }
            }
            else
                jugador.enviarMensaje(2);
        }
    }

    public void iniciarLimpiezaMutes()
    {
        expireds = new Expireds(20);
        expireds.start();
    }

    public void detenerLimpiezaMutes()
    {
        expireds.interrupt();
    }

    public static void removeAutoExpiredMutes()
    {
        for (Map.Entry<UUID, MuteInfo> entry : mutes.entrySet())
            entry.getValue().hasExpired();
    }

    private String getReason(String[] args)
    {
        String reason = "";
        for (int i=2; i<args.length; i++)
            reason += " "+args[i];
        return reason;
    }

    public static void deleteMute(Jugador j)
    {
        if (!isUserMuted(j.getJugador()))
            return;
        mutes.remove(j.getJugador().getUniqueId());
    }

    public static boolean isUserMuted(ProxiedPlayer p)
    {
        return mutes.get(p.getUniqueId()).hasExpired();
    }

    public static int getRemainingMinutes(ProxiedPlayer p)
    {
        if (!mutes.get(p.getUniqueId()).hasExpired())
            return mutes.get(p.getUniqueId()).getRemainingMinutes();
        else
            return 0;
    }

    private class MuteInfo
    {
        private Jugador muted;
        private Jugador mutedBy;
        private String reason;
        private int initHour;
        private int endHour;

        private MuteInfo(Jugador muted, Jugador mutedBy, String reason, int time) throws SQLException
        {
            this.muted = muted;
            this.mutedBy = mutedBy;
            this.reason = reason;

            Calendar cal = Calendar.getInstance();
            initHour = (cal.get(Calendar.HOUR)*60) + cal.get(Calendar.MINUTE);
            endHour = initHour + time;

            final String day = +cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR);

            Connection con = pl.getBd().getConex();

            con.prepareStatement("INSERT INTO "+tableName+" (muted_username, muted_uuid, mutedby_username, mutedby_uuid, duration, day, hour) VALUES " +
                    "('"+muted.getJugador().getName()+"', '"+muted.getJugador().getUniqueId().toString()+"'," +
                    " '"+mutedBy.getJugador().getName()+"', '"+mutedBy.getJugador().getUniqueId().toString()+"'," +
                    " "+ time+", "+day+", "+initHour+");")
                    .execute();

            pl.console(pl.getMensajes().get(37, pl.getIdiomaConsola())
                    .replaceAll(Datos.MODNAME, mutedBy.getJugador().getName())
                    .replaceAll(Datos.USER_NAME, muted.getJugador().getName())
                    .replaceAll(Datos.REASON, reason)
                    .replaceAll(Datos.TIME, String.valueOf(time)));
        }

        public int getRemainingMinutes()
        {
            Calendar cal = Calendar.getInstance();
            int act = (cal.get(Calendar.HOUR)*60) + cal.get(Calendar.MINUTE);
            return endHour-act;
        }

        public boolean hasExpired()
        {
            Calendar cal = Calendar.getInstance();
            int act = (cal.get(Calendar.HOUR)*60) + cal.get(Calendar.MINUTE);

            if (act >= endHour) //Habrá expirado.
            {
                mutes.remove(muted.getJugador().getUniqueId());
                return true;
            }
            else
                return false;
        }

        public Jugador getMuted() {
            return muted;
        }

        public void setMuted(Jugador muted) {
            this.muted = muted;
        }

        public Jugador getMutedBy() {
            return mutedBy;
        }

        public void setMutedBy(Jugador mutedBy) {
            this.mutedBy = mutedBy;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getInitHour() {
            return initHour;
        }

        public void setInitHour(int initHour) {
            this.initHour = initHour;
        }

        public int getEndHour() {
            return endHour;
        }

        public void setEndHour(int endHour) {
            this.endHour = endHour;
        }
    }

    private class Expireds extends Thread //Se va a encargar de eliminar las sanciones expiradas.
    {
        private int delay;

        private Expireds(int delay)
        {
            this.delay = delay;
        }

        @Override
        public void run()
        {
            Mute.removeAutoExpiredMutes(); //Eliminamos los mutes expirados.
            dormir();
        }

        private void dormir()
        {
            try {
                sleep(delay * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
