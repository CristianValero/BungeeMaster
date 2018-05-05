package BungeeMaster;

import BungeeMaster.Recursos.JsonSimple.parser.ParseException;
import com.gtranslate.Language;
import com.gtranslate.Translator;

import java.io.*;

public class test
{
    public static void main(String args[]) throws IOException, ParseException
    {
        Translator translate = Translator.getInstance();
        String text = translate.translate("Hello!", Language.ENGLISH, Language.ROMANIAN);
        System.out.println(text); // "Bună ziua!"
    }
}
