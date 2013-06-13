package com.example.test;

import java.net.URLEncoder;
import java.util.LinkedList;
import retrieval.wikipedia.util.OpenSearchUtil;
import retrieval.wikipedia.util.QueryUtil;
import retrieval.wikipedia.infobox.model.GenericInfobox;
import retrieval.wikipedia.infobox.model.MusicalArtistInfobox;

/**
 * Class for testing the modularity of the HttpGetConnectionClient and
 * OpenSearchUtil classes.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 *
 */
public class ConnectionTest1 {

    public static void main(String args[]) {
        HttpGetConnectionClient client =
                HttpGetConnectionClient.getHttpGetConnectionClient();
        String search = "Halestorm";
        try {
            search = URLEncoder.encode(search, "UTF-8");
        }
        catch (java.io.UnsupportedEncodingException e) {
        }
        //String responseBody = client.executeRequest(OpenSearchUtil.assembleSearchURL(search, 10));
        LinkedList<String> titles = OpenSearchUtil.retrieveJSONOpenSearchResultLines(client.executeRequest(OpenSearchUtil.assembleSearchURL(search, 10)));
        System.out.println("Printing titles: ");
        for (String title : titles) {
            System.out.println("Title: " + title);
        }
        //LinkedList<String> titles = OpenSearchUtil.retrieveJSONOpenSearchResultLines(responseBody);
        //LinkedList of LinkedList of Strings
        LinkedList<GenericInfobox> infoboxes = new LinkedList<GenericInfobox>();
        for (String title : titles) {
            GenericInfobox gi;
            gi = QueryUtil.retrieveRawInfoboxMusicalArtist(client.executeRequest(QueryUtil.assembleQueryURL(title, null)));
            //pages.add(QueryUtil.retrieveRawInfoboxMusicalArtist(client.executeRequest(QueryUtil.assembleQueryURL(title, null))));
            if (gi.size() > 0) {
                gi.setTitle(title);
                infoboxes.add(gi);
            }
        }
        client.shutdownHttpClientConnection();
        client.destroy();
        System.out.println("lista, de tamaño " + infoboxes.size());
        for (GenericInfobox gi : infoboxes) {
            System.out.println(gi.toString());
            MusicalArtistInfobox mai = new MusicalArtistInfobox();
            mai.importGenericInfobox(infoboxes.getFirst());
        }

    }
}
