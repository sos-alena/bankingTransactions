package workingWithFiles.jasonDoc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import workingWithFiles.bankTransactions.model.Bank;

import workingWithFiles.bankTransactions.model.LocalDateTimeDeserializerMy;

import java.io.FileReader;
import java.time.LocalDateTime;

public class ParserGson {

    public static Bank myParseGson(String path) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializerMy());

        Gson gson = gsonBuilder.setPrettyPrinting().create();

        try (FileReader reader = new FileReader(path)) {

            return gson.fromJson(reader, Bank.class);

        } catch (Exception e) {
            System.out.println("Error parsing " + e.getMessage());
        }
        return null;
    }
}

