package workingWithFiles.xmlDoc;

import workingWithFiles.bankTransactions.Editable;
import workingWithFiles.bankTransactions.enums.Action;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import workingWithFiles.bankTransactions.model.Bank;
import workingWithFiles.bankTransactions.validation.RegexValidation;

import javax.xml.transform.TransformerException;
import java.io.IOException;


import static workingWithFiles.bankTransactions.enums.Action.*;
import static workingWithFiles.bankTransactions.enums.Tags.*;
import static workingWithFiles.bankTransactions.validation.FormatData.createNumberCard;
import static workingWithFiles.bankTransactions.validation.Utils.*;
import static workingWithFiles.xmlDoc.WriterNewXML.addNewTransaction;
import static workingWithFiles.xmlDoc.WriterNewXML.writeDocument;
import static workingWithFiles.xmlDoc.myParser.DomParser.buildDocument;
import static workingWithFiles.xmlDoc.myParser.SaxMyParser.parseSax;

@Slf4j
public class EditorXML implements Editable{

    public static Node searchNodeFirst(Document doc) {
        assert doc != null;
        Node rootNode = doc.getFirstChild();

        NodeList rootChilds = rootNode.getChildNodes();

        Node nodeTransfer = null;
        for (int i = 0; i < rootChilds.getLength(); i++) {

            if (rootChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            switch (rootChilds.item(i).getNodeName()) {
                case "name":
                    continue;
                case "transfers": {
                    nodeTransfer = rootChilds.item(i);
                }
            }
        }
        return nodeTransfer;
    }

    public static Node searchNodeNext(NodeList transferNode) {
        String attr = RegexValidation.inputNumberValue();
        Node nodeTwo = null;

        for (int j = 0; j < transferNode.getLength(); j++) {

            if (transferNode.item(j).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            if (transferNode.item(j).getNodeName().equals("element")) {
                Node n = transferNode.item(j);
                if (n.getAttributes().getNamedItem("num").getNodeValue().equals(attr)) {
                    nodeTwo = n;
                }
            }
        }
        if (nodeTwo != null) {
            return nodeTwo;
        }
        log.info("Cannot invoke because \"nodeTwo\" is null");
        return searchNodeNext(transferNode);
    }

    private static void removeElementNode(Node nodeTwo) {
        Node parent = nodeTwo.getParentNode();
        parent.removeChild(nodeTwo);
    }

    private static void printElementChild(Document doc) {
        Node nodeTransfer = searchNodeFirst(doc);
        NodeList transferNode = nodeTransfer.getChildNodes();
        Node nodeTwo = searchNodeNext(transferNode);

        NodeList elementChilds = nodeTwo.getChildNodes();

        for (int k = 0; k < elementChilds.getLength(); k++) {
            if (elementChilds.item(k).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            System.out.println(elementChilds.item(k).getNodeName()
                    + " - " + elementChilds.item(k).getTextContent());
        }
    }

    private static void removeElementChild(Node nodeTwo) {
        NodeList elementChilds = nodeTwo.getChildNodes();
        for (int k = 0; k < elementChilds.getLength(); k++) {
            nodeTwo.removeChild(elementChilds.item(k));
        }
    }

    public static void editLastNodeList(Document doc) {
        Node nodeTransfer = searchNodeFirst(doc);
        NodeList transferNode = nodeTransfer.getChildNodes();
        Node nodeTwo = searchNodeNext(transferNode);

        NodeList elementChilds = nodeTwo.getChildNodes();
        printElementChild(doc);

        for (int k = 0; k < elementChilds.getLength(); k++) {
            String tag = elementChilds.item(k).getNodeName();
            if (elementChilds.item(k).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if (tag.equals(TAG_ID.getTitle())) {
                System.out.println("If you want correct TAG_ID input Y? if not input N");
                String answer = RegexValidation.inputYesOrNot();
                if (answer.equals("N")) {
                    continue;
                }
                String newContext = RegexValidation.inputNumberValue();
                elementChilds.item(k).setTextContent(newContext);
            }

            if (tag.equals(TAG_SENDER.getTitle())) {
                System.out.println("If you want correct TAG_SENDER input Y? if not input N");
                String answer = RegexValidation.inputYesOrNot();
                if (answer.equals("N")) {
                    continue;
                }
                String newContext = createNumberCard();
                elementChilds.item(k).setTextContent(newContext);
            }

            if (tag.equals(TAG_RECIPIENT.getTitle())) {
                System.out.println("If you want correct TAG_RECIPIENT input Y? if not input N");
                String answer = RegexValidation.inputYesOrNot();
                if (answer.equals("N")) {
                    continue;
                }
                String newContext = createNumberCard();
                elementChilds.item(k).setTextContent(newContext);
            }

            if (tag.equals(TAG_AMOUNT.getTitle())) {
                System.out.println("If you want correct TAG_AMOUNT input Y? if not input N");
                String answer = RegexValidation.inputYesOrNot();
                if (answer.equals("N")) {
                    continue;
                }
                String newContext = String.valueOf(inputValidateNumberDouble());
                elementChilds.item(k).setTextContent(newContext);

            }
            if (tag.equals(TAG_CURRENCY.getTitle())) {
                System.out.println("If you want correct TAG_CURRENCY input Y? if not input N");
                String answer = RegexValidation.inputYesOrNot();
                if (answer.equals("N")) {
                    continue;
                }
                String newContext = String.valueOf(inputCurrency());
                elementChilds.item(k).setTextContent(newContext);
            }
            if (tag.equals(TAG_DATA.getTitle())) {
                System.out.println("If you want correct TAG_DATA input Y? if not input N");
                String answer = RegexValidation.inputYesOrNot();
                if (answer.equals("N")) {
                    continue;
                }
                String newContext = String.valueOf(inputDateTime());
                elementChilds.item(k).setTextContent(newContext);

            }
            if (tag.equals(TAG_PURPOSE.getTitle())) {
                System.out.println("If you want correct TAG_PURPOSE input Y? if not input N");
                String answer = RegexValidation.inputYesOrNot();
                if (answer.equals("N")) {
                    continue;
                }
                String newContext = inputValidateStr();
                elementChilds.item(k).setTextContent(newContext);

            }
            if (tag.equals(TAG_STATUS.getTitle())) {
                System.out.println("If you want correct TAG_STATUS input Y? if not input N");
                String answer = RegexValidation.inputYesOrNot();
                if (answer.equals("N")) {
                    continue;
                }
                String newContext = String.valueOf(inputStatus());
                elementChilds.item(k).setTextContent(newContext);

            }
        }
        System.out.println();
        log.info("Editing completed successfully!");
    }

    public static void deleteElement(Document doc) {
        Node nodeTransfer = searchNodeFirst(doc);
        NodeList transferNode = nodeTransfer.getChildNodes();
        Node nodeTwo = searchNodeNext(transferNode);
        printElementChild(doc);

        System.out.println();
        System.out.println("Element is found!");
        System.out.println("----------------------------------------------------------------");
        System.out.println("If you want remove element with their child input Y, if not - N");
        String str = RegexValidation.inputYesOrNot();
        if (str.equals("Y")) {
            removeElementNode(nodeTwo);
            removeElementChild(nodeTwo);
        }
        System.out.println("Item removed successfully");
    }

    private static void choiceActionEdit(Document doc, String path) {

        System.out.println("Selection of actions for working with a document............. " +
                ", " + ADD +
                ", " + DELETE +
                ", " + EDIT +
                ", " + PRINT+
                ", " + READ);
        Action selection = inputAction();

        switch (selection) {
            case ADD -> {
                try {
                    addNewTransaction(doc, path);
                } catch (TransformerException | IOException e) {
                    log.info("Error" + e.getMessage());
                }
            }
            case DELETE -> deleteElement(doc);
            case PRINT -> printElementChild(doc);
            case EDIT -> editLastNodeList(doc);
            case READ -> System.out.println(parseSax(path));
        }
    }
    @Override
    public void editorDoc(String path, String pathNew) {
        Document doc = buildDocument(path);
        System.out.println("Start working with document XML.");
        String condition;
        do {
            choiceActionEdit(doc, path);
            System.out.println("Start working with document XML. Input START or STOP!");
            condition = inputValidateStr();
        } while (!condition.equalsIgnoreCase("stop"));
        System.out.println("The end!");
        try {
            writeDocument(doc, 3, pathNew);
        } catch (IOException e) {
            log.info("Error "+ e.getMessage());
        }
    }
}




