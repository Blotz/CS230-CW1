package com.group10;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
Not sure if this is the best way to handle files. Might be better in a
different class but I need somewhere to put the code for now.
 */
public class ProfileHandler {
    private ArrayList<Profile> profiles;
    private static String PROFILES_PATH;

    private static final String FILE_NOT_FOUND = " didn't resolve to a file";
    private static final String INCORRECT_NAME_AND_LEVEL = " Incorrect file format, should be in: profileName|maxLevel";

    public ProfileHandler(String profilesPath) throws FileNotFoundException {
        PROFILES_PATH = profilesPath;
        // Load file from resources path
        InputStream file = ProfileHandler.class.getResourceAsStream(profilesPath);
        // If the path is invalid, throw an error!
        if (file == null) {
            throw new FileNotFoundException(profilesPath + FILE_NOT_FOUND);
        }
        Scanner in = new Scanner(file);
        while (in.hasNextLine()) {
            String profileInfoString = in.next();
            Scanner profileInfo = new Scanner(profileInfoString).useDelimiter("|");
            // If file format is incorrect, throw an error
            try {
                String playerName = profileInfo.next();
                int maxLevel = profileInfo.nextInt();
                profiles.add(new Profile(playerName, maxLevel));
            } catch (NoSuchElementException e) {
                throw new IllegalArgumentException(INCORRECT_NAME_AND_LEVEL);
            }
        }
    }

    public ArrayList<Profile> selectProfiles() {
        if (profiles == null) {
            System.out.println("No profiles have been created!");
            return null;
        } else {
            return profiles;
        }
    }

    public Profile createProfile(String playerName) {
        Profile newProfile = new Profile(playerName, 1);
        profiles.add(newProfile);
        //Code to write the new profile to profiles.txt
        try {
            FileWriter myWriter = new FileWriter(PROFILES_PATH);
            myWriter.write(playerName + "|" + "1");
            myWriter.close();
            System.out.println("New profile created!");
        } catch (IOException e) {
            System.out.println("An error occurred");
        }
        return newProfile;
    }
}
