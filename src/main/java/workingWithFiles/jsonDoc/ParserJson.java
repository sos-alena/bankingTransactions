package workingWithFiles.jsonDoc;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import workingWithFiles.bankTransactions.model.Bank;
import workingWithFiles.bankTransactions.model.Transaction;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static workingWithFiles.bankTransactions.enums.Tags.*;
import static workingWithFiles.bankTransactions.validation.VerifyData.verifyDoc;

@Slf4j
public class ParserJson {

    public Bank parse(String path) {
        Bank bank = new Bank();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(path)) {

            JSONObject bankJASONObject = (JSONObject) jsonParser.parse(reader);

            String name = (String) bankJASONObject.get("name");

            JSONArray transferJsonArray = (JSONArray) bankJASONObject.get("transactions");

            List<Transaction> transList = new ArrayList<>();
            for(Object item: transferJsonArray){
                JSONObject jasonTransaction = (JSONObject) item;

                StringBuilder cb = new StringBuilder();

                long id = (long) jasonTransaction.get(TAG_ID.getTitle());
                cb.append(id).append(",");
                String sender_number = (String) jasonTransaction.get(TAG_SENDER.getTitle());
                cb.append(sender_number).append(",");
                String recipient_number = (String) jasonTransaction.get(TAG_RECIPIENT.getTitle());
                cb.append(recipient_number).append(",");
                double amount = (double) jasonTransaction.get(TAG_AMOUNT.getTitle());
                cb.append(amount).append(",");
                String currency = (String) jasonTransaction.get(TAG_CURRENCY.getTitle());
                cb.append(currency).append(",");
                LocalDateTime dataTimeJ = (LocalDateTime) jasonTransaction.get(TAG_DATA.getTitle());
                cb.append(dataTimeJ).append(",");
                String purpose = (String) jasonTransaction.get(TAG_PURPOSE.getTitle());
                cb.append(purpose).append(",");
                String status = (String) jasonTransaction.get(TAG_STATUS.getTitle());
                cb.append(status);

              if(!verifyDoc(String.valueOf(cb))){
                    continue;
                }

                Transaction transaction = new Transaction((int) id, sender_number, recipient_number,
                        amount, currency, dataTimeJ, purpose, status);
                transList.add(transaction);
            }
            bank.setName(name);
            bank.setTransactions(transList);

            return bank;
        } catch (Exception e) {
            log.info("Error " + e.getMessage());
        }
        return null;
    }
}
