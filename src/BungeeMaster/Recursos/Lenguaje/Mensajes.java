package BungeeMaster.Recursos.Lenguaje;

import java.util.ArrayList;

public class Mensajes
{
    private static ArrayList<Mensajes> mensajes = new ArrayList<Mensajes>();

    private int ID;
    private Mensaje msg;

    public Mensajes(int id, String en, String es)
    {
        this.ID = id;
        this.msg = new Mensaje(en, es);
        mensajes.add(this);
    }

    public int getID()
    {
        return ID;
    }

    public String get(String lang)
    {
        lang = lang.toLowerCase();
        switch (lang)
        {
            case "es":
                return msg.getEs();
            case "en":
                return msg.getEn();
        }
        return null;
    }

    public static Mensajes getMensaje(int ID)
    {
        for (Mensajes m : mensajes)
            if (m.getID() == ID)
                return m;
        return null;
    }

    private class Mensaje
    {
        private String en;
        private String es;

        public Mensaje(String en, String es)
        {
            this.en = en;
            this.es = es;
        }

        public String getEn() {
            return en;
        }

        public String getEs() {
            return es;
        }
    }
}
