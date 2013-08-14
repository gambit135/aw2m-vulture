package com.example.test;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 31/07/2013 - 03:52:23 PM
 */
public class MainSendPostRequest {

    static String url =
            "http://aw2m-vulture.herokuapp.com/MultipleValues0";

    public static void main(String[] args) {

        //Instantiate an HttpClient
        HttpClient client = new HttpClient();

        //Instantiate a GET HTTP method
        PostMethod method = new PostMethod(url);
        method.setRequestHeader("Content-type",
                                "text/xml; charset=ISO-8859-1");

        //Define name-value pairs to set into the QueryString
        /*
         NameValuePair nvp1 = new NameValuePair("firstName", "fname");
         NameValuePair nvp2 = new NameValuePair("lastName", "lname");
         NameValuePair nvp3 = new NameValuePair("email", "email@email.com");
         */
        NameValuePair nvp1 = new NameValuePair("favoriteMusic", "Melodic Death Metal");
        NameValuePair nvp2 = new NameValuePair("favoriteFood", "Cheerios");
        NameValuePair nvp3 = new NameValuePair("favoriteMusic", "Melodeath");
        method.setQueryString(new NameValuePair[]{nvp1, nvp2, nvp3});

        try {
            int statusCode = client.executeMethod(method);

            System.out.println("Status Code = " + statusCode);
            System.out.println("QueryString>>> " + method.getQueryString());
            System.out.println("Status Text>>>"
                    + HttpStatus.getStatusText(statusCode));

            //Get data as a String
            System.out.println(method.getResponseBodyAsString());

            //OR as a byte array
            byte[] res = method.getResponseBody();

            //write to file
            FileOutputStream fos = new FileOutputStream("donepage.html");
            fos.write(res);

            //release connection
            method.releaseConnection();
        }
        catch (IOException e) {
        }
    }

    static String getBodyfromPostResponseToRequest() {
        //Instantiate an HttpClient
        HttpClient client = new HttpClient();

        //Instantiate a GET HTTP method
        PostMethod method = new PostMethod(url);
        method.setRequestHeader("Content-type",
                                "text/xml; charset=ISO-8859-1");
        NameValuePair nvp1 = new NameValuePair("favoriteMusic", "Melodic Death Metal");
        NameValuePair nvp2 = new NameValuePair("favoriteFood", "Cheerios");
        NameValuePair nvp3 = new NameValuePair("favoriteMusic", "Melodeath");
        method.setQueryString(new NameValuePair[]{nvp1, nvp2, nvp3});
        String st = "";
        try {
            int statusCode = client.executeMethod(method);

            System.out.println("Status Code = " + statusCode);
            System.out.println("QueryString>>> " + method.getQueryString());
            System.out.println("Status Text>>>"
                    + HttpStatus.getStatusText(statusCode));

            //Get data as a String
            System.out.println(st = method.getResponseBodyAsString());

            //OR as a byte array
            byte[] res = method.getResponseBody();

            //write to file
            FileOutputStream fos = new FileOutputStream("donepage.html");
            fos.write(res);

            //release connection
            method.releaseConnection();
        }
        catch (IOException e) {
        }
        return st;
    }
}
