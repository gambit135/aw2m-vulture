package aw2m.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 27/10/2013 - 06:46:20 PM
 */
public class Util {

    public static StringBuilder inputStreamToString(InputStream is)
            throws IOException {
        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        while ((line = rd.readLine()) != null) {
            total.append(line);
        }

        // Return full string
        return total;
    }
}
