package workingWithFiles.bankTransactions.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FormatData {

    public static String createNumberCard(){

        String numb = RegexValidation.inputCardNumber();
        int size = 4;
        List<String> cardNumber = new ArrayList<>();
        for (int start = 0; start < numb.length(); start += size) {
            cardNumber.add(numb.substring(start, Math.min(numb.length(), start + size)));
        }
        StringBuilder builderNumber = new StringBuilder();
        for (String str: cardNumber) {
            builderNumber.append(str).append("-");
        }
        return builderNumber.substring(0, 19);
    }
    public static String formatDouble(){
        Double amount = Utils.inputValidateNumberDouble();
        return String.format(Locale.ROOT, "%.2f", amount);
    }

    public static String formatDouble(double d){
        return String.format(Locale.ROOT, "%.2f", d);
    }

  }
