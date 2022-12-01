package com.group10;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;

    /**
     * @author Luca
     * @version 1.0 Class made to collect MOTD via HTTP GET Request
     * MOTDGetter handles creating the GET request to pull the MOTD from
     * cswebcat.swansea.ac.uk, it also features methods to handle exceptions
     * and solve the validification puzzle to access the MOTD.
     */
public class MOTDRetriever {
    private static String alphabet = "abcdefghijklmnopqrstuvwyxz";

    /**
     * MOTDGetter sends a GET request and returns the body of the response
     * @param url URL to send the GET request too
     * @return Returns the body of the packet sent back by the server
     * @throws Exception URISyntaxException, IOException, InterruptedException
     */
    private static String MOTDGetter(String url) 
        throws Exception{
        HttpRequest getResponse = HttpRequest.newBuilder()
        .uri(new URI(url))
        .GET()
        .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(getResponse, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    /**
     * @param puzzle current auth puzzle sent by cswebcat
     * @return solution to the cracked ciphertext.
     */
    private static String Solve(String puzzle) {
        String password = "";
        int newIndex;
        for (int i = 0; i < puzzle.length(); i++){
            newIndex = alphabet.indexOf(puzzle.substring(i));
            if (i % 2 == 0){
                newIndex = newIndex + i > 25 
                    ? (newIndex + i - 25) : newIndex + i;
                password += alphabet.charAt(newIndex);
            } else {
                newIndex = newIndex - i < 0
                    ? (newIndex + i + 25) : newIndex + i;
                password += alphabet.charAt(newIndex - i);
            }
        }
        password = password.length() + password;
        password += "CS-230";
        return password;
    }

    /**
     * getMOTD is a wrapper to catch exceptions from MOTDGetter and
     * handles sending both requests and returning todays MOTD
     * @return The current MOTD from cswebcat.swansea.ac.uk/message
     */
    public static String getMOTD() {
        try {
            String keyword = Solve(MOTDGetter("http://cswebcat.swansea.ac.uk/puzzle"));
            return MOTDGetter("http://cswebcat.swansea.ac.uk/message?solution=" + keyword);
        } catch (Exception e) {
            System.out.print(e);
            return "Connection to cswebcat.swansea.ac.uk/puzzle failed.";
        }
    }

    public static void main(String args[]){
        System.out.println(getMOTD());
    }
}