package workingWithFiles.csvDoc;

import lombok.extern.slf4j.Slf4j;
import workingWithFiles.bankTransactions.Editable;
import workingWithFiles.bankTransactions.enums.Action;
import workingWithFiles.bankTransactions.enums.StatusPayment;
import workingWithFiles.bankTransactions.model.Bank;
import workingWithFiles.bankTransactions.model.Transaction;


import java.io.PrintWriter;
import java.util.Currency;
import java.util.List;

import static workingWithFiles.bankTransactions.ElementController.*;
import static workingWithFiles.bankTransactions.enums.Action.*;

import static workingWithFiles.bankTransactions.validation.Utils.*;
import static workingWithFiles.csvDoc.ParserCSV.readCSVDoc;

@Slf4j

public class EditorCSV implements Editable {

    public static void writeCSVDoc(Bank bank, String path) {
                try (PrintWriter writer = new PrintWriter(path)) {
            StringBuilder br = new StringBuilder();
            List<Transaction> bankCSV = bank.getTransactions();

            for (Transaction transaction : bankCSV) {
                br.append(transaction.getId()).append(",");
                br.append(transaction.getSender_number()).append(",");
                br.append(transaction.getRecipient_number()).append(",");
                br.append(transaction.getAmount()).append(",");
                br.append(Currency.getInstance(transaction.getCurrency())).append(",");
                br.append(transaction.getDateTime()).append(",");
                br.append(transaction.getPurpose()).append(",");
                br.append(StatusPayment.valueOf(transaction.getStatus())).append("\n");
            }
            writer.write(String.valueOf(br));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void addCSVElement(Bank bank, String path) {
        Bank newBank = addElement(bank);
        writeCSVDoc(newBank, path);
    }
    public static void editCSVElement(Bank bank, String path) {
        Bank newBank = editElement(bank);
        writeCSVDoc(newBank, path);
    }
    public static void removeCSVTransaction(Bank bank, String path) {
       Bank newBank = removeElement(bank);
        writeCSVDoc(newBank, path);
    }
    public static void choiceActionCSV(Bank bank, String path) {
        System.out.println("Selection of actions for working with a CSV document............. " +
                ", " + ADD +
                ", " + EDIT +
                ", " + DELETE +
                ", " + READ);
        Action selection = inputAction();

        switch (selection) {
            case ADD -> addCSVElement(bank, path);
            case EDIT -> editCSVElement(bank, path);
            case DELETE -> removeCSVTransaction(bank, path);
            case READ -> System.out.println(bank);
        }
    }
  @Override
   public void editorDoc(String path, String pathNew) {
            Bank bank = readCSVDoc(path);
            System.out.println("Start working with document CSV.");
            String condition;
            do {
                choiceActionCSV(bank, pathNew);
                System.out.println("Start working with document CSV. Input START or STOP!");
                condition = inputValidateStr();
            }  while (!condition.equalsIgnoreCase("stop")) ;
            System.out.println("The end!");
        }
}





