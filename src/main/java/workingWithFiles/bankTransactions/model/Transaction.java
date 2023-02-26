package workingWithFiles.bankTransactions.model;

import com.fasterxml.jackson.annotation.JsonFormat;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.time.LocalDateTime;
import static workingWithFiles.bankTransactions.validation.FormatData.formatDouble;

@NoArgsConstructor
@Data
@Slf4j
public class Transaction {

    private int id;
    private String sender_number;
    private String recipient_number;
    private double amount;
    private String currency;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public LocalDateTime dateTime;
    private String purpose;
    private String status;

    public Transaction(int id, String sender_number, String recipient_number,
                       double amount, String currency, LocalDateTime dateTime, String purpose, String status) {
        this.id = id;
        this.sender_number = sender_number;
        this.recipient_number = recipient_number;
        this.amount = amount;
        this.currency = currency;

        this.dateTime = dateTime;
        this.purpose = purpose;
        this.status = status;
    }

    @Override
        public String toString() {
            return "Transaction: " +
                    "id=" + id +
                    ", sender_number = " + sender_number +
                    ", recipient_number = " + recipient_number +
                    ", amount = " + formatDouble(amount) +
                    ", currency = " + currency +
                    ", dateTime = " + dateTime +
                    ", purpose = " + purpose +
                    ", status =" + status + "\n" +
                    "___________________________________________________________________________" +
                    "\n";
        }
    }


