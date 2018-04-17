package BungeeMaster;

import BungeeMaster.Recursos.JsonSimple.parser.ParseException;

import java.io.*;

public class test
{
    public static void main(String args[]) throws IOException, ParseException
    {

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
