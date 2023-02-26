package workingWithFiles.bankTransactions;

import lombok.extern.slf4j.Slf4j;
import workingWithFiles.bankTransactions.enums.Extension;
import workingWithFiles.csvDoc.EditorCSV;
import workingWithFiles.jasonDoc.EditorJason;
import workingWithFiles.xmlDoc.EditorXML;
import workingWithFiles.yamlDoc.EditorYaml;

import java.io.File;
import java.util.Objects;

import static workingWithFiles.bankTransactions.validation.Utils.inputValidateStr;
import static workingWithFiles.bankTransactions.validation.VerifyData.verifyExtension;
@Slf4j
public class EditorController {

    public static void editorBankDoc() {
        System.out.println("Input Filename or path (For example \"test.csv\"):");
        String path = inputValidateStr();
        System.out.println("Input Filename or path to save date (For example \"test2.csv\"):");
        String pathNew = inputValidateStr();
       try{
        Extension extension = getExtension(path);
        System.out.println(extension);
        switch (Objects.requireNonNull(extension)) {
            case XML -> new EditorXML().editorDoc(path, pathNew);
            case CSV -> new EditorCSV().editorDoc(path, pathNew);
            case JSON -> new EditorJason().editorDoc(path, pathNew);
            case YAML -> new EditorYaml().editorDoc(path, pathNew);
        }
        } catch (NullPointerException e){
           log.info("Error! File not found or path invalid " + path);
       }
    }

    private static Extension getExtension(String path) {
        return checkFile(path) ? Extension.valueOf(extensionValidation(path)) : null;
    }

    private static String extensionValidation(String path) {

        String[] strings = path.split("\\.");
        int size = strings.length;
        String extension = strings[size - 1].toUpperCase();

        if (verifyExtension(extension)) {
            return extension;
        } else {
            System.out.println("Error! Invalid extension, check filename or path.");
            return null;
        }
    }

    private static boolean checkFile(String path) {
        File file = new File(path);

        if (isFileExists(file)) {
            System.out.println("File exists!!");
            return true;
        } else {
            System.out.println("File doesn't exist or program doesn't have access " +
                    "to the file");
            return false;
        }
    }

    public static boolean isFileExists(File file) {
        return file.exists() && !file.isDirectory();
    }
}
