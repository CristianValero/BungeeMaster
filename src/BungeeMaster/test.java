package BungeeMaster;

import BungeeMaster.Recursos.JsonSimple.parser.ParseException;

import java.io.*;

public class test
{
    public static void main(String args[]) throws IOException, ParseException
    {
        System.out.println(replace("Hola me llamo %nombre", new String[]{"%nombre"}, new String[]{"pepe"}, 0));
    }

    public static String replace(String cad, String[] toReplace, String[] replaces, int total)
    {
        if (total == -1)
            return cad;
        else
            return replace(cad.replaceAll(toReplace[total], replaces[total]), toReplace, replaces, total-1);
    }

    public static void runnableAsync(Runnable runnable) {
        Thread t = new Thread(runnable);
        t.start();
    }

    public static void runnableSync(Runnable runnable) {
        Thread t = new Thread(runnable);
        t.run();
    }
}
