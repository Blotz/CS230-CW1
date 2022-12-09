package com.group10;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
Not sure if this is the best way to handle files. Might be better in a
different class but I need somewhere to put the code for now.
 */
public class ProfileHandler {
    private ArrayList<Profile> profiles;

    private static final String FILE_NOT_FOUND = " didn't resolve to a file";
    private static final String INCORRECT_NAME_AND_LEVEL = " Incorrect file format, should be in: profileName|maxLevel";

    public ProfileHandler(String profilesPath) throws FileNotFoundException {
        // Load file from resources path
        InputStream file = Level.class.getResourceAsStream(profilesPath);
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

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
        //Code needed to add the new profile to the .txt file.
    }
}
