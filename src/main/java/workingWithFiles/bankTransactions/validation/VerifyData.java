package workingWithFiles.bankTransactions.validation;

import lombok.extern.slf4j.Slf4j;
import workingWithFiles.bankTransactions.enums.Currency;
import workingWithFiles.bankTransactions.enums.Extension;
import workingWithFiles.bankTransactions.enums.StatusPayment;

import java.time.LocalDate;
import java.util.regex.Pattern;
import static workingWithFiles.bankTransactions.validation.RegexValidation.validateData;
import static workingWithFiles.bankTransactions.validation.Utils.createDayMonth;

@Slf4j
public class VerifyData {
       public static Pattern patternDataTime = Pattern.compile("^(20)\\d{2}-(0?[1-9]|1[012])-\\d{1,2}T\\d{1,2}:\\d{1,2}$");
       static Pattern patternDoc= Pattern.compile("^\\d+," +
            "(\\d{4}-\\d{4}-\\d{4}-\\d{4},){2}" +
            "((\\d{1,6})|(\\d{1,6}\\.\\d{1,2}))," +
            "((?i)(EUR|GBP|CHF|CNY|JPY|UAH))," +
            "((20)\\d{2}-(0?[1-9]|1[012])-\\d{1,2}T\\d{1,2}:\\d{1,2})," +
            "(\\w|[.\\S]+)," +
            "((?i)(CONFIRMED|REJECTED|PENDING|RETURNED))$");

    public static boolean verifyCurrency(String str) {
       try {
            Currency.valueOf(str);
            return true;
        } catch (Exception exception) {
            log.info("Incorrect value type. " + "(" + str + ")");
            return false;
        }
    }
       public static boolean verifyStatus(String str) {
        try {
            StatusPayment.valueOf(str);
            return true;
        } catch (Exception exception) {
            log.info("Incorrect value type. " + "(" + str + ")");
            return false;
        }
       }
    public static boolean verifyExtension(String str) {
        try {
            Extension.valueOf(str);
            return true;
        } catch (Exception exception) {
            log.info("Incorrect value type. " + "(" + str + ")");
            return false;
        }
    }
    public static boolean verifyDataTime(String str) {
        return !validateData(str, patternDataTime);
    }

    public static boolean verifyCurrentData(String str){
        StringBuilder dataTime = new StringBuilder();
       if (verifyDataTime(str)){
           log.info("Error " + str);
       } else {
           int year = Integer.parseInt(str.substring(0,4));
           LocalDate current_date = LocalDate.now();
           int current_Year = current_date.getYear();

           if(year > current_Year) {
               log.info("The year is greater than the current date.");
               System.out.println("Check the date on the document ");
           } else {
               dataTime.append(year).append("-");
           }

           int month = Integer.parseInt(str.substring(5,7));
           if(month > 12) {
               log.info("The month is greater than the 12.");
               System.out.println("Check the date on the document ");
           } else {
               dataTime.append(month).append("-");
           }

           int data = Integer.parseInt(str.substring(8,10));
           int maxDay = createDayMonth(month);

           if(data > maxDay) {
               log.info("The data is greater than the " + maxDay);
               System.out.println("Check the date on the document ");
           } else {
               dataTime.append(data).append("T");
           }

           int hour = Integer.parseInt(str.substring(11,13));
           if(hour > 24) {
               log.info("The hour is greater than the 24");
               System.out.println("Check the date on the document ");
           } else {
               dataTime.append(hour).append(":");
           }

           int minutes = Integer.parseInt(str.substring(14,16));
           if(minutes > 60) {
               log.info("The minutes is greater than the 60");
               System.out.println("Check the date on the document ");
           } else {
               dataTime.append(minutes);
           }
       }
        return !verifyDataTime(String.valueOf(dataTime));
    }
    public static Boolean verifyDoc(String str, int index) {
        if(str.isEmpty()){
            log.info("Line is empty. Index = " + index);
            return false;
        }
        if (validateData(str, patternDoc)) {
            return true;
        }
        log.info("Line is invalid. Index = " + index);
        System.out.println(str);
        System.out.println();
        return false;
    }

    public static Boolean verifyDoc(String str) {
        if(str.isEmpty()){
            log.info("Line is empty.");
            return false;
        }
        if (validateData(str, patternDoc)) {
            return true;
        }
        log.info("Line is invalid. Index" );
        System.out.println(str);
        System.out.println();
        return false;
    }
}
