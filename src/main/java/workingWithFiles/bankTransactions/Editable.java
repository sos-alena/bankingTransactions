package workingWithFiles.bankTransactions;

import workingWithFiles.bankTransactions.model.Bank;

import static workingWithFiles.bankTransactions.validation.Utils.inputValidateStr;

public interface Editable {
 void editorDoc(String path, String pathNew);

}

