package workingWithFiles.yamlDoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import workingWithFiles.bankTransactions.Editable;
import workingWithFiles.bankTransactions.enums.Action;
import workingWithFiles.bankTransactions.model.Bank;

import java.io.File;
import java.io.IOException;

import static workingWithFiles.bankTransactions.ElementController.*;
import static workingWithFiles.bankTransactions.enums.Action.*;
import static workingWithFiles.bankTransactions.validation.Utils.inputAction;
import static workingWithFiles.bankTransactions.validation.Utils.inputValidateStr;
import static workingWithFiles.yamlDoc.YamlReader.readerYAML;
@Slf4j
public class EditorYaml implements Editable {

    public static void addYmlElement(Bank bank, String pathNew) {

        Bank newBank = addElement(bank);
        writeYamlDoc(newBank, pathNew);
    }

    public static void removeYamlElement(Bank bank, String pathNew){
        Bank newBank = removeElement(bank);
        writeYamlDoc(newBank, pathNew);
    }

    public static void editYamlElement(Bank bank, String pathNew) {
        Bank newBank = editElement(bank);
        writeYamlDoc(newBank, pathNew);
    }
    private static void writeYamlDoc(Bank bank, String pathNew) {
        try {
            ObjectMapper om = new ObjectMapper(new YAMLFactory());
            om.writeValue(new File(pathNew), bank);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void choiceActionYaml(Bank bank, String path){
        System.out.println("Selection of actions for working with a YAML document............. " +
                ", " + ADD +
                ", " + EDIT +
                ", " + DELETE +
                ", " + READ);
        Action selection = inputAction();

        switch (selection) {
            case ADD -> addYmlElement(bank, path);
            case EDIT -> editYamlElement(bank, path);
            case DELETE -> removeYamlElement(bank, path);
            case READ -> System.out.println(bank);
        }
    }
    public void editorDoc(String path, String pathNew) {
        Bank bank = readerYAML(path);
        System.out.println("Start working with document YAML.");
        String condition;
        do {
            choiceActionYaml(bank, pathNew);
            System.out.println("Start working with document YAML. Input START or STOP!");
            condition = inputValidateStr();
        }  while (!condition.equalsIgnoreCase("stop")) ;
        System.out.println("The end!");
    }

}
