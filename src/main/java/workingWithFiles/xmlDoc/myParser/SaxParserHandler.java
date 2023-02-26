package workingWithFiles.xmlDoc.myParser;

import workingWithFiles.bankTransactions.model.Bank;
import workingWithFiles.bankTransactions.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static workingWithFiles.bankTransactions.enums.Tags.TAG_ELEMENT;
import static workingWithFiles.bankTransactions.enums.Tags.TAG_NAME;

public class SaxParserHandler extends DefaultHandler {

    private final Bank bank = new Bank();
    private final List<Transaction> transactionsList = new ArrayList<>();
    
    public Bank getBank(){
        return bank;
    }
    private String currentTagName;

    private Boolean isElement = false;

    int numb = 0;
    String sender_number = null;
    String recipient_number = null;
    double amount = 0;
    String currency = null;
    LocalDateTime dataTime = null;
    String purpose = null;
    String status = null;

    @Override
    public void startDocument() {

    }

    @Override    public void endDocument() {

        bank.setTransactions(transactionsList);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        currentTagName = qName;
          if(currentTagName.equals(TAG_ELEMENT.getTitle())){
              isElement = true;
          }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        if(qName.equals(TAG_ELEMENT.getTitle())){
            isElement = false;

            Transaction transaction = new Transaction(numb, sender_number, recipient_number,
                    amount, currency, dataTime, purpose, status);
            transactionsList.add(transaction);

        }

        currentTagName = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if(currentTagName == null){
            return;
        }

        if (currentTagName.equals(TAG_NAME.getTitle())) {
            bank.setName(new String(ch, start, length));
        }

        if(isElement){

            switch (currentTagName) {
                case "id" -> numb = Integer.parseInt(new String(ch, start, length));
                case "sender_number" -> sender_number = new String(ch, start, length);
                case "recipient_number" -> recipient_number = new String(ch, start, length);
                case "amount" -> amount = Double.parseDouble((new String(ch, start, length)));
                case "currency" -> currency = new String(ch, start, length);
                case "dataTime" -> dataTime = LocalDateTime.parse(new String(ch, start, length));
                case "purpose" -> purpose = new String(ch, start, length);
                case "status" -> status = new String(ch, start, length);
            }
        }
    }
}
