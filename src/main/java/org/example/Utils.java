package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<List<String>> readFile(String fileName) {
        List<List<String>> records = new ArrayList<>();

        try (InputStream is = MovieLibrary.class.getResourceAsStream("/" + fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                List<String> record = new ArrayList<>();
                for (String part : parts) {
                    record.add(part.trim());
                }
                records.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }
}
