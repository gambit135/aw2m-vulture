package retrieval.wikipedia.util;

import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.StringTokenizer;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class OpenSearchUtil {

    /**
     * Performs an Open Search using the MediaWiki API and returns the titles of
     * the matching Wikipedia articles as a LinkedList containing String
     * objects, an int value can be sent to specify the max number of results to
     * retrieve for the search. If a number smaller than 1 is sent as parameter,
     * 10 is used as default.
     *
     * @param search The text string to be searched.
     * @param max    The max number of results wanted. If < 1, 10 is used.
     * @return A LinkedList of Strings, containing the titles of the matching
     *         articles.
     */
    public static LinkedList<String> openSearch(String search, int max) {

        if (max < 1) {
            max = 10;
        }
        //Response body and an HttpClient for performing the request.
        String responseBody = "";
        HttpClient httpclient = new DefaultHttpClient();
        try {
            search = URLEncoder.encode(search, "UTF-8");
        }
        catch (java.io.UnsupportedEncodingException e) {
        }
        try {
            HttpGet httpget =
                    new HttpGet(
                    "http://en.wikipedia.org/w/api.php?action=opensearch&search=" + search + "&limit=" + max + "&namespace=0&format=jsonfm");

            System.out.println("executing request " + httpget.getURI());

            // Create a response handler
            //ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = httpclient.execute(httpget, new BasicResponseHandler());
            //System.out.println(responseBody);
        }
        catch (java.io.IOException e) {
        }
        finally {
            /*
             * When HttpClient instance is no longer needed, shut down the
             * connection manager to ensureimmediate deallocation of all system
             * resources
             */
            httpclient.getConnectionManager().shutdown();
        }
        return retrieveJSONOpenSearchResultLines(responseBody);
    }

    public static LinkedList<String> retrieveJSONOpenSearchResultLines(String raw) {

        LinkedList<String> titles = null;
        StringTokenizer st = new StringTokenizer(
                raw.substring(raw.indexOf("["), raw.lastIndexOf("]") + 1),
                System.getProperty("line.separator"));
        if (st.hasMoreTokens()) {
            titles = new LinkedList<String>();
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                if (s.indexOf("&quot;") > -1) {
                    /*
                     * N.B. Store this on memory, as it's the purified title of
                     * a result
                     */
                    titles.add(
                            s.substring(s.indexOf("&quot;") + 6,
                                        s.lastIndexOf("&quot;")));
                }
            }
        }
        /*
         * Remove the first element of the list, as it's the string that was
         * originally searched
         */
        titles.removeFirst();
        return titles;
    }

    public static void searchInfobox(String articleTitle) {
    }

    public static String assembleSearchURL(String search, int max) {
        try {
            search = URLEncoder.encode(search, "UTF-8");
        }
        catch (java.io.UnsupportedEncodingException e) {
        }
        finally {
            return "http://en.wikipedia.org/w/api.php?action=opensearch&search=" + search + "&limit=" + max + "&namespace=0&format=jsonfm";
        }
    }
}
