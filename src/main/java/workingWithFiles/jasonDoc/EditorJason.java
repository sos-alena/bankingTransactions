package workingWithFiles.jasonDoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import workingWithFiles.bankTransactions.Editable;
import workingWithFiles.bankTransactions.enums.Action;
import workingWithFiles.bankTransactions.model.Bank;

import java.io.File;

import static workingWithFiles.bankTransactions.ElementController.*;
import static workingWithFiles.bankTransactions.enums.Action.*;
import static workingWithFiles.bankTransactions.validation.Utils.inputAction;
import static workingWithFiles.bankTransactions.validation.Utils.inputValidateStr;
import static workingWithFiles.jasonDoc.ParserGson.myParseGson;

public class EditorJason implements Editable {

    public static void addJasonElement(Bank bank, String pathNew) {
            Bank newBank = addElement(bank);
            writeJsonDoc(newBank, pathNew);
    }
    public static void removeJasonElement(Bank bank, String pathNew){
        Bank newBank = removeElement(bank);
        System.out.println(bank);
        writeJsonDoc(newBank, pathNew);
    }

    public static void editJsonElement(Bank bank, String pathNew) {
        Bank newBank = editElement(bank);
        System.out.println(newBank);
        writeJsonDoc(newBank, pathNew);
    }
    private static void writeJsonDoc(Bank bank, String pathNew){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(pathNew), bank);
            mapper.writeValueAsString(bank);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void choiceActionJSON(Bank bank, String path){
        System.out.println("Selection of actions for working with a JSON document............. " +
                ", " + ADD +
                ", " + DELETE +
                ", " + EDIT +
                 ", " + READ);
        Action selection = inputAction();

        switch (selection) {
            case ADD -> addJasonElement(bank, path);
            case DELETE -> removeJasonElement(bank, path);
            case EDIT -> editJsonElement(bank, path);
            case READ -> System.out.println(bank);
        }
    }
    @Override
    public void editorDoc(String path, String pathNew) {
        Bank bank = myParseGson(path);
        System.out.println("Start working with document JSON.");
        String condition;
        do {
            choiceActionJSON(bank, pathNew);
            System.out.println("Start working with document JSON. Input START or STOP!");
            condition = inputValidateStr();
        }  while (!condition.equalsIgnoreCase("stop")) ;
        System.out.println("The end!");
    }

}
