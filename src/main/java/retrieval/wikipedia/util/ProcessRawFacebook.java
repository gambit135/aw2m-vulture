package retrieval.wikipedia.util;

import java.net.URLEncoder;
import java.util.HashSet;
import java.util.LinkedList;
import retrieval.wikipedia.infobox.model.GenericInfobox;
import retrieval.wikipedia.infobox.model.MusicalArtistInfobox;
import com.example.test.HttpGetConnectionClient;

/**
 * This class provides methods for processing raw data from Wikipedia, i.e., a
 * collection of String objects, and providing as output Infobox objects.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 26/11/2012 - 07:10:32 AM
 */
public class ProcessRawFacebook {

    public HashSet<MusicalArtistInfobox> processRaw(HashSet<String> likes) {
        HashSet<MusicalArtistInfobox> infoboxes = new HashSet<MusicalArtistInfobox>();
        HttpGetConnectionClient client =
                HttpGetConnectionClient.getHttpGetConnectionClient();
        for (String like : likes) {
            MusicalArtistInfobox processSingleLike = this.processSingleLike(like, client);
            if (processSingleLike != null) {
                infoboxes.add(processSingleLike);
            }
        }
        client.shutdownHttpClientConnection();
        client.destroy();
        return infoboxes;
    }

    protected MusicalArtistInfobox processSingleLike(String like, HttpGetConnectionClient client) {

        try {
            like = URLEncoder.encode(like, "UTF-8");
        }
        catch (java.io.UnsupportedEncodingException e) {
            
        }

        LinkedList<String> titles = OpenSearchUtil.retrieveJSONOpenSearchResultLines(client.executeRequest(OpenSearchUtil.assembleSearchURL(like, 10)));

        LinkedList<GenericInfobox> infoBoxes = new LinkedList<GenericInfobox>();

        for (String title : titles) {
            GenericInfobox gi;
            gi = QueryUtil.retrieveRawInfoboxMusicalArtist(client.executeRequest(QueryUtil.assembleQueryURL(title, null)));

            if (gi.size() > 0) {
                gi.setTitle(title);
                infoBoxes.add(gi);
            }
        }
        for (GenericInfobox gi : infoBoxes) {
            MusicalArtistInfobox mai = new MusicalArtistInfobox();
            //Takes into account only the first artist found on the infoboxes
            mai.importGenericInfobox(infoBoxes.getFirst());
            return mai;
        }
        return null;
    }
}