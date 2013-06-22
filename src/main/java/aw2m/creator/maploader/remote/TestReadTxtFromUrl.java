package aw2m.creator.maploader.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 18/06/2013 - 08:10:20 PM
 */
public class TestReadTxtFromUrl {

    public static void main(String args[]) {
        try {
            // Create a URL for the desired page

            //URL url = new URL("https://dl.dropboxusercontent.com/u/20278793/aw2m/hola.txt");
            URL url = new URL("https://dl.dropboxusercontent.com/u/20278793/aw2m/maps/SpannIsland/SpannIsland.csv");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str = in.readLine()) != null) {
                System.out.println(str);
            }
            in.close();
        }
        catch (MalformedURLException e) {
            System.err.println(e.getLocalizedMessage());
        }
        catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
