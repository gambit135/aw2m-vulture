package aw2m.common.serialize;

import aw2m.common.core.GameInstance;
import aw2m.remote.creator.maploader.MapCatalog;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * This class makes a POST request, appending the serialized data of a
 * GameInstance as parameters.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 13/08/2013 - 02:05:48 AM
 */
public class MakePostRequest {

    static String remoteEndpointURL = "http://aw2m-vulture.herokuapp.com/DeserializeEndpoint";
    String responseBody;
    HttpClient client;
    PostMethod method;

    /**
     * The constructor creates a new instance of this class, with the game
     * instance to be serialized and sent to the cloud sent as parameter. By
     * calling this method, the game instance is automatically serialized, so no
     * calls for serialization methods shall be made outside this class.
     *
     * @param game The game instance to serialize and send to the cloud
     */
    public MakePostRequest(GameInstance game) {
        //Instantiate an HttpClient
        client = new HttpClient();

        //Instantiate a GET HTTP method
        method = new PostMethod(remoteEndpointURL);
        method.setRequestHeader("Content-type",
                                "text/xml; charset=ISO-8859-1");

        boolean isPredefined = false;
        if (game.mapChosen != MapCatalog.UNDEFINED) {
            isPredefined = true;
        }

        //Define name-value pairs to set into the QueryString
        NameValuePair mapChosen;
        NameValuePair xSize;
        NameValuePair ySize;
        NameValuePair terrainPair;

        Serialize s = new Serialize();

        NameValuePair isPredefinedPair = new NameValuePair("isPredefined", isPredefined + "");
        NameValuePair playersPair = new NameValuePair("players", s.serializePlayers(game));
        NameValuePair propertiesPair = new NameValuePair("properties", s.serializeProperties(game));

        NameValuePair unitsPair = new NameValuePair("units", s.serializeUnits(game));

        //Assemble the QueryString

        //If the map is predefined, just send mapChosen data, else, send X and Y coordinates and the terrain.
        if (isPredefined) {
            mapChosen = new NameValuePair("mapChosen", game.mapChosen + "");
            method.setQueryString(new NameValuePair[]{isPredefinedPair, mapChosen, playersPair, propertiesPair, unitsPair});
        }
        else {
            xSize = new NameValuePair("xSize", "" + game.map.length);
            ySize = new NameValuePair("ySize", "" + game.map[0].length);
            terrainPair = new NameValuePair("terrain", s.serializeTerrain(game));
            method.setQueryString(new NameValuePair[]{isPredefinedPair, xSize, ySize, playersPair, propertiesPair, terrainPair, unitsPair});
        }
        responseBody = "";
    }

    public String executePostRequest() {
        //Try to execute post request
        responseBody = "";
        try {
            System.out.println("Trying to execute POST request");
            int statusCode = client.executeMethod(method);
            System.out.println("Status Code = " + statusCode);
            System.out.println("QueryString>>> " + method.getQueryString());
            System.out.println("Status Text>>>"
                    + HttpStatus.getStatusText(statusCode));

            responseBody = method.getResponseBodyAsString();

            byte[] res = method.getResponseBody();
            Calendar calendar = Calendar.getInstance();
            //Write output to a HTML file
            FileOutputStream fos = new FileOutputStream("EndpointResponse-"
                    + calendar.get(Calendar.DAY_OF_MONTH) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.YEAR)
                    + ".html");
            fos.write(res);
            fos.close();
        }
        catch (IOException ioe) {
            System.err.println("IOException at request");
            System.err.println(ioe.getLocalizedMessage());
        }
        finally {
            //release connection
            method.releaseConnection();
        }
        return responseBody;
    }
}
