package com.group10;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * This handles all the profile interactions
 */
public class Profile {
    static private String profileName;
    static private int maxLevel;
    static private String currentLevel;
    private static final String PROFILE_PATH = "profiles.txt";
    private static final String PROFILE_FORMAT = "\"%s\" %d%n## NEXT PROFILE ##%n";
    private static final String PROFILE_INFO = "\"%s\" %d%n";
    private static final String NEW_PROFILE = "## NEXT PROFILE ##%n";
    private static final String FILE_NOT_FOUND = " didn't resolve to a file";
    
    /**
     * Saves the new max level to the profile
     * @param profileName the name of the profile to be saved to
     * @param maxLevel the new max level
     */
    public static void saveProfile(String profileName, int maxLevel) {
        Profile.profileName = profileName;
        Profile.maxLevel = maxLevel;
        
        // Read the file
        InputStream file = Profile.class.getResourceAsStream(PROFILE_PATH);
        if (file == null) {
            throw new RuntimeException(FILE_NOT_FOUND);
        }
        String fileContents = "";
        
        Scanner in = new Scanner(file);
        in.useDelimiter(String.format(NEW_PROFILE));
        
        // Loop though and find the profile account
        String profile = null;
        while (in.hasNext()) {
            profile = in.next();
            if (profile.startsWith("\""+profileName+ "\"")) {
                System.out.println("Found profile");
                break;
            } else {
                fileContents += String.format(profile + NEW_PROFILE);
            }
        }
        if (profile == null) {
            throw new IllegalArgumentException("Profile not found");
        }
        
        Scanner profileData = new Scanner(profile);
        profileData.nextLine();  // Throw away the first line
        
        fileContents += String.format(PROFILE_INFO, profileName, maxLevel);
        
        while (profileData.hasNext()) {
            fileContents += String.format(profileData.next());
        }
        profileData.close();
        fileContents += String.format(NEW_PROFILE);
        
        while (in.hasNext()) {
            fileContents += String.format(in.next() + NEW_PROFILE);
        }
        in.close();
        
        // Write the file
        String path = Profile.class.getResource(PROFILE_PATH).getPath();
        try {
            OutputStream os = new FileOutputStream(path);
            os.write(fileContents.getBytes(), 0, fileContents.length());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Saves the level to the profile
     * @param profileName the name of the profile to be saved to
     * @param currentLevel the current level to be saved
     */
    public static void saveLevel(String profileName, String currentLevel) {
        Profile.profileName = profileName;
        Profile.currentLevel = currentLevel;
    
        // Read the file
        InputStream file = Profile.class.getResourceAsStream(PROFILE_PATH);
        if (file == null) {
            throw new RuntimeException(FILE_NOT_FOUND);
        }
        String fileContents = "";
    
        Scanner in = new Scanner(file);
        in.useDelimiter(String.format(NEW_PROFILE));
    
        // Loop though and find the profile account
        String profile = null;
        while (in.hasNext()) {
            String curProfile = in.next();
            if (!curProfile.startsWith("\""+profileName+ "\"")) {
                fileContents += String.format(curProfile + NEW_PROFILE);
            } else {
                profile = curProfile;
            }
        }
        in.close();
        if (profile == null) {
            throw new IllegalArgumentException("Profile not found");
        }
    
        Scanner profileData = new Scanner(profile);
        profileData.nextLine();  // Throw away the first line
        profileData.close();
    
        String profileInfo = String.format(PROFILE_INFO, profileName, maxLevel);
        fileContents = String.format(profileInfo + currentLevel + NEW_PROFILE) + fileContents;
    
        // Write the file
        String path = Profile.class.getResource(PROFILE_PATH).getPath();
        try {
            OutputStream os = new FileOutputStream(path);
            os.write(fileContents.getBytes(), 0, fileContents.length());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Updates the profile with the new max level if the new max level is greater than the old max level
     * @param maxLevel the new max level
     */
    public static void updateProfile(int maxLevel) {
        if (maxLevel > Profile.maxLevel) {
            Profile.maxLevel = maxLevel;
            saveProfile(profileName, maxLevel);
        }
    }
    
    /**
     * Creates a new profile
     * @param profileName the name of the profile to be created
     * @param maxLevel the max level of the profile to be created
     */
    public static void createProfile(String profileName, int maxLevel) {
        String path = Profile.class.getResource(PROFILE_PATH).getPath();
        // Save file to resources path
        try {
            OutputStream os = new FileOutputStream(path, true);
            String data = String.format(PROFILE_FORMAT, profileName, maxLevel);
            os.write(data.getBytes(), 0, data.length());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Gets the max level of the profile
     * @param profileName the name of the profile to get the max level of
     * @return sets the Profile.profileName, Profile.maxLevel and Profile.currentLevel
     */
    public static void loadProfile(String profileName) {
        // Read the file
        InputStream file = Profile.class.getResourceAsStream(PROFILE_PATH);
        if (file == null) {
            throw new RuntimeException(FILE_NOT_FOUND);
        }
        
        Scanner in = new Scanner(file);
        in.useDelimiter(String.format(NEW_PROFILE));
        
        // Loop though and find the profile account
        String profile = null;
        while (in.hasNext()) {
            String curProfile = in.next();
            if (curProfile.startsWith("\"" + profileName + "\"")) {
                profile = curProfile;
                break;
            }
        }
        if (profile == null) {
            Profile.profileName = profileName;
            Profile.maxLevel = 1;
            Profile.currentLevel = "";
            createProfile(profileName, maxLevel);
        } else {
            Scanner profileData = new Scanner(profile);
            String profileInfo = profileData.nextLine();
            // split on "
            String[] profileInfoSplit = profileInfo.split("\"");
            Profile.profileName = profileInfoSplit[1];
            Profile.maxLevel = Integer.parseInt(profileInfoSplit[2].strip());
            profileData.useDelimiter("\\Z");
            if (profileData.hasNext()) {
                Profile.currentLevel = profileData.next().strip();
            } else {
                Profile.currentLevel = "";
            }
            profileData.close();
        }
        in.close();
    }
    
    /**
     * Deletes the profile from the game
     * @param profileName the name of the profile to be deleted
     */
    public static void deleteProfile(String profileName) {
        Profile.profileName = null;
        Profile.maxLevel = 0;
        Profile.currentLevel = null;
        deleteProfileFromFile(profileName);
        Highscores.deleteHighscore(profileName);
    }
    
    /**
     * Deletes the profile from the file
     * @param profileName the name of the profile to be deleted
     */
    private static void deleteProfileFromFile(String profileName) {
        // Read the file
        InputStream file = Profile.class.getResourceAsStream(PROFILE_PATH);
        if (file == null) {
            throw new RuntimeException(FILE_NOT_FOUND);
        }
        String fileContents = "";
        
        Scanner in = new Scanner(file);
        in.useDelimiter(String.format(NEW_PROFILE));
        
        // Loop though and find the profile account
        String profile = null;
        while (in.hasNext()) {
            profile = in.next();
            if (!profile.startsWith("\""+profileName+ "\"")) {
                fileContents += String.format(profile + NEW_PROFILE);
            }
        }
        in.close();
        
        // Write the file
        String path = Profile.class.getResource(PROFILE_PATH).getPath();
        try {
            OutputStream os = new FileOutputStream(path);
            os.write(fileContents.getBytes(), 0, fileContents.length());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getProfileName() {
        return profileName;
    }
    
    public static int getMaxLevel() {
        return maxLevel;
    }
    
    public static String getLevel() {
        return currentLevel;
    }
}