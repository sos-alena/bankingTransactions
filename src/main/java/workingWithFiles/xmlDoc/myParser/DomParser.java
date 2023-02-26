package workingWithFiles.xmlDoc.myParser;

import workingWithFiles.bankTransactions.enums.Tags;
import workingWithFiles.bankTransactions.model.Bank;
import workingWithFiles.bankTransactions.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import workingWithFiles.bankTransactions.validation.Utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class DomParser {

    public Bank parse(String path) {

        Bank bank = new Bank();

        Document doc = buildDocument(path);

        assert doc != null;
        Node bankNode = doc.getFirstChild();

        NodeList nodeChilds = bankNode.getChildNodes();

        String bankName = null;
        Node transfersNode = null;
        for (int i = 0; i < nodeChilds.getLength(); i++) {
            if (nodeChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            Tags tag = Tags.valueOf(nodeChilds.item(i).getNodeName());
            switch (tag) {
                case TAG_NAME -> bankName = nodeChilds.item(i).getTextContent();
                case TAG_TRANSFERS -> transfersNode = nodeChilds.item(i);
            }
        }

        if (transfersNode == null) {
            return null;
        }

        List<Transaction> transList = parsTransactionList(transfersNode);

        bank.setName(bankName);
        bank.setTransactions(transList);

        return bank;
    }
    public static Document buildDocument(String path) {
        File file = new File(path);
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            return dbf.newDocumentBuilder().parse(file);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            log.info("Open parsing error");
            buildDocument(path);
            return null;
        }
    }
    private List<Transaction> parsTransactionList(Node transfersNode) {
        List<Transaction> transList = new ArrayList<>();
        NodeList bankChilds = transfersNode.getChildNodes();

        for (int i = 0; i < bankChilds.getLength(); i++) {
            if (bankChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            bankChilds.item(i);
        }
        return transList;
    }

    private Transaction parsElement(Node elementNode) {
        int numb = 0;
        String sender_number = null;
        String recipient_number = null;
        double amount = 0;
        String currency = null;
        LocalDateTime dataTime = null;
        String purpose = null;
        String status = null;

        NodeList elementChilds = elementNode.getChildNodes();

        for (int j = 0; j < elementChilds.getLength(); j++) {
            if (elementChilds.item(j).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            switch (Tags.valueOf(elementChilds.item(j).getNodeName())) {
                case TAG_ID -> numb = Integer.parseInt(elementChilds.item(j).getTextContent());
                case TAG_SENDER -> sender_number = elementChilds.item(j).getTextContent();
                case TAG_RECIPIENT -> recipient_number = elementChilds.item(j).getTextContent();
                case TAG_AMOUNT -> amount = Integer.parseInt(elementChilds.item(j).getTextContent());
                case TAG_CURRENCY -> currency = elementChilds.item(j).getTextContent();
                case TAG_DATA -> dataTime = LocalDateTime.parse(elementChilds.item(j).getTextContent());
                case TAG_PURPOSE -> purpose = elementChilds.item(j).getTextContent();
                case TAG_STATUS -> status = elementChilds.item(j).getTextContent();
            }
        }
        return new Transaction(numb, sender_number, recipient_number, amount,
                currency, dataTime, purpose, status);
    }
}
