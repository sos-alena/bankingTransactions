package workingWithFiles.csvDoc;

import lombok.extern.slf4j.Slf4j;
import workingWithFiles.bankTransactions.model.Bank;
import workingWithFiles.bankTransactions.model.Transaction;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static workingWithFiles.bankTransactions.validation.VerifyData.*;

@Slf4j
public class ParserCSV {
    public static Bank readCSVDoc(String path) {

        Bank bank = new Bank();
        try {
            List<String> line = Files.readAllLines(Paths.get(path));

            Transaction transaction;
            List<Transaction> transList = new ArrayList<>();


            for (int i = 0; i < line.size(); i++) {

                if(!verifyDoc(line.get(i), i)){
                    continue;
                }

                String[] result = line.get(i).split(",");
                transaction = new Transaction();

                transaction.setId(Integer.parseInt(result[0]));
                transaction.setSender_number(result[1]);
                transaction.setRecipient_number(result[2]);
                transaction.setAmount(Double.parseDouble(result[3]));
                transaction.setPurpose(result[6]);

                if (!verifyCurrency(result[4])) {
                    System.out.println("Error currency.Line index = " + i + " : " + line.get(i));
                    continue;
                } else {
                    transaction.setCurrency(result[4]);
                }

                if (!verifyCurrentData(result[5])) {
                    System.out.println("Error DataTime.Line index = " + i + " : " + line.get(i));
                    continue;
                } else {
                    transaction.setDateTime(LocalDateTime.parse(result[5]));
                }


                if (!verifyStatus(result[7])) {
                    System.out.println("Error Status.Line index = " + i + " : "+ line.get(i));
                    continue;
                } else {
                    transaction.setStatus(result[7]);
                }

                transList.add(transaction);

        }
            bank.setName("bank");
            bank.setTransactions(transList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bank;
    }
}




