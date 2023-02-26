package workingWithFiles.bankTransactions.model;

import lombok.Data;

import java.util.List;

@Data
public class Bank {

    private String name;

    private List<Transaction> transactions;

    public void setName(String name) {
        this.name = name;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "\n" + "------------------------------" + name + "-------------------------------" + "\n"
                + "\n" +
                transactions;
    }
}
