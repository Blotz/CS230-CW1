package com.group10;

import java.net.http.*;
import java.nio.*;

    /*
     * MOTDGetter handles creating the GET request to pull the MOTD from
     * cswebcat.swansea.ac.uk, it also features a function to 
     */
    public class MOTDRetriever {
        private Character[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z' };

        private static String MOTDGetter() throws URISyntaxException{
            HttpRequest getMOTD = HttpRequest.newBuilder()
            .uri(new URI("http://cswebcat.swansea.ac.uk/puzzle"))
            .GET()
            .build();


            return null;
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
    }