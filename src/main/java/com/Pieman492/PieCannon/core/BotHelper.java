package com.Pieman492.PieCannon.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *  This is super not good practice
 *  but it's my personal project so I'm doing it anyway.
 *  This is a helper class of static methods that do things like
 *  get the API token and generate a random delay.
 *  I might move these into more specialized classes later
 *  but for now they're here to clean up main()
 */

public final class BotHelper {

    // Private constructor, prevents instantiation.
    private BotHelper() {}

    //API Token grabber
    public static String grabToken() {
        String token = "";
        try {
            FileInputStream tokenInput = new FileInputStream(System.getProperty("user.home") + "/.Piecannon/ApiToken");
            Scanner tokenScanner = new Scanner(tokenInput);
            token = tokenScanner.nextLine();
            tokenInput.close();
            tokenScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    // Returns a randomized delay in seconds
    public static Duration randomizedDelay(int upperBound, int lowerBound) {
        Random RNG = new Random();
        int randomInt = RNG.nextInt(upperBound - lowerBound) + lowerBound;
        return Duration.ofSeconds(randomInt);
    }

    // Pattern: Ends with User Ping
    public static final Pattern USER_PING = Pattern.compile("<@!\\d{18}>");
    // Pattern: Contains the deletion flag
    public static final Pattern CONTAINS_DELETION_FLAG = Pattern.compile("\\Q[DeletionFlag]\\E");
}

