package com.group10;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;

    /*
     * MOTDGetter handles creating the GET request to pull the MOTD from
     * cswebcat.swansea.ac.uk, it also features methods to handle exceptions
     * and solve the validification puzzle to access the MOTD.
     */
public class MOTDRetriever {
    private Character[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z' };

    // MOTDGetter creates an instance of HttpRequest to create a GET Request
    // and return the results of the GET Request
    private static String MOTDGetter() 
        throws URISyntaxException, IOException, InterruptedException{
        HttpRequest getMOTD = HttpRequest.newBuilder()
        .uri(new URI("http://cswebcat.swansea.ac.uk/puzzle"))
        .GET()
        .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(getMOTD, HttpResponse.BodyHandlers.ofString());
        HttpHeaders responseHeaders = response.headers();
        return responseHeaders.toString();
    }

    // Wrapper to catch URISyntaxException thrown by MOTDRetriever()
    public static String getMOTD() {
        try {
            return MOTDGetter();
        } catch (Exception e) {
            System.out.print(e);
            return "Connection to cswebcat.swansea.ac.uk/puzzle failed.";
        }
    }

    public static void main(String args[]){
        System.out.println(getMOTD());
    }
}