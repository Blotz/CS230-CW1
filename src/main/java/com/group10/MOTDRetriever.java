package com.group10;

import java.net.URI;
import java.net.http.*;

    /**
     * @author Luca
     * @version 1.0 Class made to collect MOTD via HTTP GET Request
     * MOTDGetter handles creating the GET request to pull the MOTD from
     * cswebcat.swansea.ac.uk, it also features methods to handle exceptions
     * and solve the validification puzzle to access the MOTD.
     */
public class MOTDRetriever {
    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

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
     * Solve takes the puzzle text and returns the correct Solution
     * @param puzzle The input string to parse into an answer
     * @return The final output we use in our GET request
     */
    private static String Solve(String puzzle) {
        String password = "";
        int newIndex; // The index of puzzle[i] in the alphabet
        int y; // The index + 1, as index 0 is position 1
        for (int i = 0; i < puzzle.length(); i++){
            y = i + 1;
            newIndex = alphabet.indexOf(puzzle.charAt(i));
            if (y % 2 == 0){ // If pos is even shift forward
                newIndex = newIndex + y > 25 // Ternary to keep index in range
                    ? ((newIndex + y) - 26) : newIndex + y;
                password += alphabet.charAt(newIndex);
            } else { // Else shift in reverse
                newIndex = newIndex - y < 0
                    ? (26 + (newIndex - y)) : newIndex - y;
                password += alphabet.charAt(newIndex);
            }
        }
        password += "CS-230";
        password = password.length() + password;
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
}