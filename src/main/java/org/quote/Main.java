package org.quote;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class that provides different quotes every day
 */
public class Main {


    public static void main(String[] args) {

        ManageResponse manageResponse = new ManageResponse();
        final String FILE_NAME = "response.txt";
        File file = new File(FILE_NAME);
        System.out.println("The quote of the day:");
        // check if file exist
        if (file.exists()) {

            // Get the last modified date of the file
            long lastModifiedTimeInMillis = file.lastModified();
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            String formattedDate = sdf.format(lastModifiedTimeInMillis);
            int fileDate=  Integer.parseInt(formattedDate);

            // Get the current day
            Calendar calendar = Calendar.getInstance();
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

            // If the user wants to get quote in same day, the program will read it from the existence file
            if (currentDay-fileDate==0){
            manageResponse.readFile(FILE_NAME).extractQuote();}

            // If not in same day, the program will delete the previous file
            else {
                try {
                    file.delete();
                } catch (Exception e) {
                    // Print the error message if the file cannot be deleted.
                    e.printStackTrace();
                }
            }
        } if (!file.exists()) {

            // If file not exist request new API
            RequestingAPI requestingAPI = new RequestingAPI();

            // Extract quote time from the requested API response
            String responseBody = requestingAPI.request();
            boolean connection = manageResponse.readWeb(responseBody).extractQuote();

            // Write the whole response to a file if response is correct
            if (connection) {
                manageResponse.WriteFile(responseBody, FILE_NAME);
            }

        }
        }

}
