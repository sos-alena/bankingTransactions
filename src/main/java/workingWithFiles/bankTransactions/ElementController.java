package workingWithFiles.bankTransactions;

import lombok.extern.slf4j.Slf4j;
import workingWithFiles.bankTransactions.model.Bank;
import workingWithFiles.bankTransactions.model.Transaction;
import workingWithFiles.bankTransactions.validation.RegexValidation;

import java.util.ArrayList;
import java.util.List;

import static workingWithFiles.bankTransactions.validation.FormatData.createNumberCard;
import static workingWithFiles.bankTransactions.validation.FormatData.formatDouble;
import static workingWithFiles.bankTransactions.validation.RegexValidation.inputNumberValue;
import static workingWithFiles.bankTransactions.validation.Utils.*;

@Slf4j
public class ElementController {

    public static Bank addElement(Bank bank) {

        System.out.println("Add new element: ");

        List<Transaction> transaction = bank.getTransactions();

        Transaction newTransaction = new Transaction();
        int id = 0;
        if (transaction == null) {
            transaction = new ArrayList<>();
            newTransaction.setId(id + 1);
            bank.setName("bank");
            bank.setTransactions(transaction);
        } else {
            id = getLasId(bank);
            newTransaction.setId(id + 1);
        }
        newTransaction.setSender_number(createNumberCard());
        newTransaction.setRecipient_number(createNumberCard());
        newTransaction.setAmount(inputValidateNumberDouble());
        newTransaction.setCurrency(String.valueOf(inputCurrency()));
        newTransaction.setDateTime(inputDateTime());
        System.out.println("Input purpose: ");
        newTransaction.setPurpose(inputValidateStr());
        newTransaction.setStatus(String.valueOf(inputStatus()));
        transaction.add(newTransaction);

       return bank;
    }

    public static Bank removeElement(Bank bank){
        System.out.println("Input number ID");
        int temp = inputValidateInt();
        List<Transaction> transaction = bank.getTransactions();
        try {
            for (int i = 0; i < transaction.size(); i++) {
                int num = transaction.get(i).getId();

                if (num == temp) {
                    transaction.remove(transaction.get(i));
                }
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return bank;
    }

    public static Bank editElement(Bank bank) {
        System.out.println("Input number ID");
        int index = isNumberId(bank);

        List<Transaction> transaction = bank.getTransactions();
        try {
            for (int i = 0; i < transaction.size(); i++) {

                if (transaction.get(i).getId() == index) {
                    editID(transaction, i);
                    editSenderNumber(transaction, i);
                    editRecipientNumber(transaction, i);
                    editAmount(transaction, i);
                    editCurrency(transaction, i);
                    editDataTime(transaction, i);
                    editPurpose(transaction, i);
                    editStatus(transaction, i);
                }
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(bank);
       return bank;
    }

    private static void editID(List<Transaction> transaction, int i) {

        System.out.println("If you want correct ID input Y? if not input N");
        String answer = RegexValidation.inputYesOrNot();
        if (answer.equals("Y")) {
            System.out.println("Input ID: ");
            transaction.get(i).setId(Integer.parseInt(inputNumberValue()));
        } else {
            System.out.println("Element unchanged!");
        }
    }

    private static void editSenderNumber(List<Transaction> transaction, int i) {

        System.out.println("If you want correct TAG_SENDER input Y? if not input N");
        String answer = RegexValidation.inputYesOrNot();
        if (answer.equals("Y")) {
            System.out.println("--------------Sender_number-----------------");
            transaction.get(i).setSender_number(createNumberCard());
        } else {
            System.out.println("Element unchanged!");
        }
    }

    private static void editRecipientNumber(List<Transaction> transaction, int i) {

        System.out.println("If you want correct TAG_RECIPIENT input Y? if not input N");
        String answer = RegexValidation.inputYesOrNot();
        if (answer.equals("Y")) {
            System.out.println("--------------Recipient_number-----------------");
            transaction.get(i).setRecipient_number(createNumberCard());
        } else {
            System.out.println("Element unchanged!");
        }
    }

    private static void editAmount(List<Transaction> transaction, int i) {

        System.out.println("If you want correct AMOUNT input Y? if not input N");
        String answer = RegexValidation.inputYesOrNot();
        if (answer.equals("Y")) {
            transaction.get(i).setAmount(Double.parseDouble(formatDouble()));
        } else {
            System.out.println("Element unchanged!");
        }
    }

    private static void editCurrency(List<Transaction> transaction, int i) {

        System.out.println("If you want correct TAG_CURRENCY input Y? if not input N");
        String answer = RegexValidation.inputYesOrNot();
        if (answer.equals("Y")) {
            transaction.get(i).setCurrency(String.valueOf(inputCurrency()));
        } else {
            System.out.println("Element unchanged!");
        }
    }

    private static void editDataTime(List<Transaction> transaction, int i) {

        System.out.println("If you want correct DATA input Y? if not input N");
        String answer = RegexValidation.inputYesOrNot();
        if (answer.equals("Y")) {
            transaction.get(i).setDateTime(inputDateTime());
        } else {
            System.out.println("Element unchanged!");
        }
    }

    private static void editPurpose(List<Transaction> transaction, int i) {

        System.out.println("If you want correct PURPOSE input Y? if not input N");
        String answer = RegexValidation.inputYesOrNot();
        if (answer.equals("Y")) {
            System.out.println("Input the purpose of payment: ");
            transaction.get(i).setPurpose(inputValidateStr());
        } else {
            System.out.println("Element unchanged!");
        }
    }

    private static void editStatus(List<Transaction> transaction, int i) {

        System.out.println("If you want correct Status input Y? if not input N");
        String answer = RegexValidation.inputYesOrNot();
        if (answer.equals("Y")) {
            System.out.println("Input the purpose of payment: ");
            transaction.get(i).setStatus(String.valueOf(inputStatus()));
        } else {
            System.out.println("Element unchanged!");
        }
    }

    public static int getLasId(Bank bank) {
        int index = 0;
        if (bank != null) {
            List<Transaction> transactions = bank.getTransactions();
            index = transactions.get(transactions.size() - 1).getId();
        }
        return index;
    }

    private static int isNumberId(Bank bank) {
        int numberId = inputValidateInt();
        int temp = 0;

        if (bank != null) {
            List<Transaction> transactions = bank.getTransactions();
            for (Transaction transaction : transactions) {
                if (transaction.getId() == numberId) {
                    temp = numberId;
                    break;
                }
            }
        }

        if (numberId != temp) {
            log.info("index - " + numberId + " not found!");
            System.out.println("Input numberId again");
            return isNumberId(bank);
        }
        return numberId;
    }
}
