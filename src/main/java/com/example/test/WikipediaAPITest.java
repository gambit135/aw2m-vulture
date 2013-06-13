package com.example.test;

import java.net.URLEncoder;
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
public class WikipediaAPITest {

    public final static void main(String[] args) throws Exception {
        String responseBody = "";
        HttpClient httpclient = new DefaultHttpClient();
        String title = "Jon Lord";        
        title = URLEncoder.encode(title, "UTF-8");
        
        /*
        //Título tentativo        
        String[] listOfTitleStrings = {"Tony Iommi"};
        //String[] listOfTitleStrings = {"Master of Puppets"};
        
        
        //From Bliki
        for (String s: listOfTitleStrings){
            System.out.println("Raw Chain: " + s);
            //s = URLEncoder.encode(s, "ISO-8859-1");
            s = URLEncoder.encode(s, "UTF-8");
            System.out.println("Processed Chain: " + s + "\n");
        }
        

        User user = new User("", "", "http://en.wikipedia.org/w/api.php");
        user.login();
        List<Page> listOfPages = user.queryContent(listOfTitleStrings);
        for (Page page : listOfPages) {
            WikiModel wikiModel = new WikiModel("${image}", "${title}");
            System.out.println("Title " + page.getTitle());
            title = page.getTitle();
            title = URLEncoder.encode(title, "ISO-8859-1");
            System.out.println("Processed Title for URL: " + title);

            String html = wikiModel.render(page.toString());
 //           System.out.println(html);
        }
        * 
        */

        try {
            HttpGet httpget =
                    //new HttpGet("http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=json&titles=Najoua%20Belyzel");
                    //new HttpGet("http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=xml&titles=Arch%20Enemy");
                    new HttpGet("http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=xml&titles=" + title);

            System.out.println("executing request " + httpget.getURI());

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = httpclient.execute(httpget, responseHandler);

            System.out.println("----------------------------------------");
            //  System.out.println(responseBody);
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
        System.out.println("The raw chain is " + raw);

        String infobox = raw.substring(raw.indexOf("Infobox musical artist"), raw.indexOf("'''"));
        System.out.println("Infobox " + infobox);
        
        System.out.println("using StringTokenizer");
        StringTokenizer st = new StringTokenizer(infobox, System.getProperty("line.separator"));
        System.out.println("With StringTokenizer " + st.countTokens());
        while (st.hasMoreTokens()) {
            String s;
            s = st.nextToken();
            //if (s.lastIndexOf("\\n") > -1) {
            //   s = s.substring(0, s.lastIndexOf("\\n"));
            // }
            System.out.println("Element: " + s);
        }
    }   
}
