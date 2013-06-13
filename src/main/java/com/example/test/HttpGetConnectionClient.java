package com.example.test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * This class modularizes the flux of making an HTTP GET request using the
 * HttpClient and HttpGet classes, as a Singleton solution. Only a single
 * instance of this class can be gotten. Defines methods for initializing the
 * variables necessary to execute an HTTP GET request.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class HttpGetConnectionClient {

    private HttpClient httpclient;
    private HttpGet httpGet;
    private ResponseHandler<String> responseHandler;
    private static HttpGetConnectionClient singletonConnectionClient;

    static {
        singletonConnectionClient = null;
    }

    private static void init() {
        singletonConnectionClient.httpclient = new DefaultHttpClient();
        singletonConnectionClient.responseHandler = new BasicResponseHandler();
    }

    private static void initHttpGet(String URI) {
        singletonConnectionClient.httpGet = new HttpGet(URI);
    }

    /**
     * Executes an HTTP GET request, using the in-class HttpClient and HttpGet
     * objects, previously initialized, and returns the response body of the
     * request; i.e., the renderized html. When successive requests need to be
     * done, iterative calls to this method can be made, in order to preserve
     * just one HttpClient and ResponseHandler objects.
     *
     * @param URI
     * @return The response body of the request (HTML)
     */
    public String executeRequest(String URI) {
        singletonConnectionClient.initHttpGet(URI);
        String responseBody = null;
        try {
            responseBody = httpclient.execute(
                    singletonConnectionClient.httpGet,
                    singletonConnectionClient.responseHandler);
        }
        catch (IOException ex) {
            Logger.getLogger(HttpGetConnectionClient.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        finally {
            return responseBody;
        }
    }

    public static void shutdownHttpClientConnection() {
        singletonConnectionClient.httpclient.getConnectionManager().shutdown();
    }

    //Singleton
    private HttpGetConnectionClient() {
    }

    public static HttpGetConnectionClient getHttpGetConnectionClient() {
        if (singletonConnectionClient == null) {
            singletonConnectionClient = new HttpGetConnectionClient();
            HttpGetConnectionClient.init();
        }
        return singletonConnectionClient;
    }

    public static void destroy() {
        singletonConnectionClient = null;
    }
}
