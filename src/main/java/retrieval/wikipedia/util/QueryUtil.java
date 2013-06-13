package retrieval.wikipedia.util;

import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.StringTokenizer;
import retrieval.wikipedia.infobox.model.GenericInfobox;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class QueryUtil {

    public static String assembleQueryURL(String title, String format) {
        if (format == null) {
            format = "xmlfm";
        }
        try {
            title = URLEncoder.encode(title, "UTF-8");
        }
        catch (java.io.UnsupportedEncodingException e) {
        }
        return "http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=" + format + "&titles=" + title;
    }

    /**
     * Breaks a raw line of a wiki infobox musical artist into lines, making the
     * first line the title of the infobox itself, followed by it's components
     * line by line, originally separated by line separators, finishing when it
     * finds double closing braces.
     *
     * @param raw
     * @return a Generic Infobox. i.e., a LinkedList of String objects,
     *         containing the separated lines.
     */
    public static GenericInfobox retrieveRawInfoboxMusicalArtist(String raw) {
        GenericInfobox gi = new GenericInfobox();
        if (raw.indexOf("Infobox musical artist") >= 0 && raw.indexOf("'''") >= 0) {
            String infobox = raw.substring(raw.indexOf("Infobox musical artist"), raw.indexOf("'''"));
            StringTokenizer st = new StringTokenizer(infobox, System.getProperty("line.separator"));
            while (st.hasMoreTokens()) {
                String s;
                s = st.nextToken();
                gi.add(s);
            }
        }
        return gi;
    }
}
