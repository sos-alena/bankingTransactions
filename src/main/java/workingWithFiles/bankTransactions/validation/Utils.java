package workingWithFiles.bankTransactions.validation;

import lombok.Data;
import workingWithFiles.bankTransactions.enums.*;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;

import static workingWithFiles.bankTransactions.enums.Action.*;
import static workingWithFiles.bankTransactions.enums.Currency.*;
import static workingWithFiles.bankTransactions.enums.StatusPayment.*;

@Slf4j
public class Utils {
    static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static String inputValidateStr() {
        try {
            String str = READER.readLine().trim();
            while (str.isEmpty()) {
                log.info("Empty.");
                System.out.println("Enter value again: ");
                str = READER.readLine();
            }
            return str;

        } catch (IOException e) {
            log.info("Error! " + e);
            return inputValidateStr();
        }
    }

    public static double inputValidateNumberDouble() {
        try {
            System.out.println("Input the amount with a dot after integers " +
                    "if there are values after.");
            double number = Double.parseDouble(READER.readLine());
            if (number > 0 && number < 1000000) {
                return number;
            }
            log.info("Incorrect value: " + number);
            System.out.println("Enter value again: ");
            throw new Exception();

        } catch (Exception exception) {
            log.info("Incorrect value: " + exception.getMessage());
            System.out.println("Enter value again: ");
            return inputValidateNumberDouble();
        }
    }

    public static int inputValidateInt() {
        try {
            int number = Integer.parseInt(READER.readLine());
            if (number > 0) {
                return number;
            }
            log.info("Incorrect value: " + number);
            System.out.println("Enter value again: ");
            throw new Exception();

        } catch (Exception exception) {
            log.info("Incorrect value: " + exception.getMessage());
            System.out.println("Enter value again: ");
            return inputValidateInt();
        }
    }
    public static Currency inputCurrency() {
        try {
            System.out.println("Input the currency from the list: " +
                    EUR + ", " +
                    GBP + ", " +
                    CHF + ", " +
                    CNY + ", " +
                    JPY + ", " +
                    UAH);
            Currency curr = Currency.valueOf(READER.readLine());
            System.out.println("Currency is valid");
            return curr;
        } catch (Exception exception) {
            log.info("Incorrect value type. ");
            System.out.println("Enter value again: ");
            return inputCurrency();
        }
    }

    public static StatusPayment inputStatus() {
        try {
            System.out.println("Input the Status from the list: " +
                    CONFIRMED + ", " +
                    REJECTED + ", " +
                    PENDING + ", " +
                    RETURNED);
            StatusPayment status = StatusPayment.valueOf(READER.readLine());
            System.out.println("status is valid");
            return status;
        } catch (Exception exception) {
            log.info("Incorrect value type.");
            System.out.println("Enter value again." );
            return inputStatus();
        }
    }

    public static Action inputAction() {
        try {
            Action action = Action.valueOf(READER.readLine());
            System.out.println("Action is valid");
            return action;
        } catch (Exception exception) {
            log.info("Incorrect value type. ");
            System.out.println("Enter value again: ");
            return inputAction();
        }
    }
    public static LocalDateTime inputDateTime() {
        try {
            System.out.println("Input year: ");
            LocalDate current_date = LocalDate.now();
            int current_Year = current_date.getYear();
            int year = createDataTime(2000, current_Year);
            System.out.println("Input month: ");
            int month = createDataTime(1, 12);
            System.out.println("Input data: ");
            int data = createDataTime(1, createDayMonth(month));
            System.out.println("Input hour: ");
            int hour = createDataTime(0, 24);
            System.out.println("Input minute: ");
            int minute = createDataTime(0, 60);
            LocalDateTime dateTime = LocalDateTime.of(year, month, data, hour, minute) ;
            LocalDateTime dateTimeNow = LocalDateTime.now();

            if(dateTime.isBefore(dateTimeNow)
                    || dateTime.isEqual(dateTimeNow)) {
                System.out.println("dateTime: " + dateTime);
                return dateTime;
            }
            System.out.println("Error! Input dataTame more then current_date, try again.");
            return inputDateTime();

        } catch (Exception exception) {
            log.info("Incorrect value: " + exception.getMessage());
            System.out.println("Enter value again: ");
            return inputDateTime();
        }
    }

    private static int createDataTime(int min, int max){
        try{
            int number = Integer.parseInt(READER.readLine());
            if(number >= min && number <= max){
                return number;
            }
            log.info("Incorrect value: " + number);
            createDataTime(min, max);

            } catch (IOException e) {
            log.info("Incorrect value: " + e.getMessage());
            System.out.println("Enter value again: ");
            }
        return createDataTime(min, max);
        }

    public static int createDayMonth(int month){
        int maxDay;

        if (month == 2) {
            maxDay = 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDay = 30;
        } else {
            maxDay = 31;
        }
        return maxDay;
    }
}


