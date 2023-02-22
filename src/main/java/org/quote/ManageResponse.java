package org.quote;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class ManageResponse {

    private Map<String, Object> response;

    /**
     * Parses a JSON file into a Map using Gson.
     *
     * @param fileName The name of the file to be parsed.
     * @return A ManageResponse object with the parsed response.
     * @throws RuntimeException if the file is not found.
     */
    public ManageResponse readFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.exists()) {
                Reader reader = new FileReader(file);
                Gson gson = new Gson();
                this.response = (Map<String, Object>) gson.fromJson(reader, Map.class);
            }
        } catch (FileNotFoundException e) {
            System.out.println("can not read from file");
        }
        return this;
    }

    /**
     * Parses the response from a web request into a Map using Gson.
     *
     * @param responseBody The response body from the web request.
     * @return A ManageResponse object with the parsed response.
     */
    public ManageResponse readWeb(String responseBody) {
        Gson gson = new Gson();
        try {
            this.response = gson.fromJson(responseBody, Map.class);
        } catch (Exception e) {
            System.out.println("No connection");
        }
        return this;
    }

    /**
     * Extract quote from the API response.
     */
    public boolean extractQuote() {
        boolean connection=false;
        try {
            Map<String, Object> contents=  (Map<String, Object>) response.get("contents");
            ArrayList<Object> quoteArray= (ArrayList<Object>) contents.get("quotes");
            Map<String, Object> quoteObject=  (Map<String, Object>) quoteArray.get(0);
            String quote = (String) quoteObject.get("quote");
         System.out.println(quote);
         connection=true;
        } catch (Exception e) {
            System.out.println("can not parse json ");
        }
    return connection;}


    public void WriteFile(String responseBody, String FILE_NAME) {

        File file = new File(FILE_NAME);
        try (FileWriter writer = new FileWriter(file)) {
            // Write the response to the file as a JSON string.
            writer.write(responseBody);

        } catch (Exception e) {
            // Print the error message if the file cannot be written.
            System.out.println("Can not write the response to file");
        }
    }
}

