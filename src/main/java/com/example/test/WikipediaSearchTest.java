package com.example.test;

import java.io.PrintStream;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.StringTokenizer;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * This example demonstrates the use of the {@link ResponseHandler} to simplify
 * the process of processing the HTTP response and releasing associated
 * resources.
 */
public class WikipediaSearchTest {

    public final static void main(String[] args) throws Exception {
        String responseBody = "";
        HttpClient httpclient = new DefaultHttpClient();
        //From Bliki
        String title = "Black Sabbath";
        PrintStream ps = new PrintStream(System.out, true, "UTF-8");

        System.out.println("Título " + title);
        title = URLEncoder.encode(title, "UTF-8");
        System.out.println("Título Procesado " + title);

        try {
            HttpGet httpget =
                    //new HttpGet("http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=json&titles=Najoua%20Belyzel");
                    //new HttpGet("http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=xml&titles=Arch%20Enemy");
                    //new HttpGet("http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=xml&titles=" + title);
                    new HttpGet("http://en.wikipedia.org/w/api.php?action=opensearch&search=" + title + "&limit=10&namespace=0&format=jsonfm");

            System.out.println("executing request " + httpget.getURI());

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            //System.out.println(responseBody);
            //ps.println(responseBody);
            System.out.println("----------------------------------------");

        }
        finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        processString(responseBody);
    }

    protected static void processString(String raw) {

        LinkedList<String> titles;
        titles = new LinkedList<String>();
        System.out.println("The raw chain is " + raw);

        String infobox = raw.substring(raw.indexOf("["), raw.lastIndexOf("]") + 1);
        System.out.println("ResultSet" + infobox);

        System.out.println("using StringTokenizer");
        StringTokenizer st = new StringTokenizer(infobox, System.getProperty("line.separator"));
        System.out.println("With StringTokenizer " + st.countTokens());
        while (st.hasMoreTokens()) {
            String s;
            s = st.nextToken();
            //System.out.println("Element: " + s);

            if (s.indexOf("&quot;") > -1) {
                // System.out.println("Quotes at " + s.indexOf("&quot;"));
                //N.B. Store this on memory, as it's the purified title of a result
                String purified = s.substring(s.indexOf("&quot;") + 6, s.lastIndexOf("&quot;"));
                System.out.println("Without quotes: " + purified);
                titles.add(purified);
                //One liner, when no debugging is needed
                //titles.add(s.substring(s.indexOf("&quot;") + 6, s.lastIndexOf("&quot;")));
            }
        }
        //processString(responseBody);
    }
}
