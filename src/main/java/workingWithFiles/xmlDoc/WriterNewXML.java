package workingWithFiles.xmlDoc;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.*;
import workingWithFiles.bankTransactions.validation.RegexValidation;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static workingWithFiles.bankTransactions.validation.FormatData.createNumberCard;
import static workingWithFiles.bankTransactions.validation.FormatData.formatDouble;
import static workingWithFiles.bankTransactions.validation.Utils.*;

@Slf4j
public class WriterNewXML {

    public static void addNewTransaction(Document document, String pathNew) throws TransformerFactoryConfigurationError,
            DOMException, TransformerException, IOException {

        Node bankNode = document.getFirstChild();
        NodeList nodeChilds = bankNode.getChildNodes();
        log.info("Get the Nodelist.");
        Node transfersNode = null;
        for (int i = 0; i < nodeChilds.getLength(); i++) {
            if (nodeChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            if(nodeChilds.item(i).getNodeName().equals("name")){
                continue;
            }
            transfersNode = nodeChilds.item(i);
        }

       Element element = createElement(document);
        assert transfersNode != null;
        transfersNode.appendChild(element);

        log.info("Transaction added successfully!");
        writeDocument(document, 3, pathNew);
    }

    public static void writeDocument(Document document, int indent, String pathNew) throws TransformerFactoryConfigurationError, IOException {
        try {
            // удаляем пробелы
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.evaluate(
                    "//text()[normalize-space()='']",
                    document,
                    XPathConstants.NODESET
            );

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                node.getParentNode().removeChild(node);
            }

            // устанавливаем настройки для красивого форматирования
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // форматируем XML
            DOMSource source = new DOMSource(document);
            OutputStream fos = Files.newOutputStream(Paths.get(pathNew));
            StreamResult result = new StreamResult(fos);
            transformer.transform(source, result);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Element createElement(Document document) {
        // Создаем новую транзакцию по элементам
        // Сама транзакция <element>
        Element element = document.createElement("element");
        Attr attr = document.createAttribute("num");
        element.setAttributeNode(attr);
        System.out.println("Input Element`s number: " + "last number");
        String value = RegexValidation.inputNumberValue();
        attr.setTextContent(String.valueOf(value));

        Element id = document.createElement("id");
        // Устанавливаем значение текста внутри тега
        System.out.println("Input ID: ");
        id.setTextContent(String.valueOf(inputValidateInt()));


        Element sender_number = document.createElement("sender_number");
        System.out.println("--------------Sender_number-----------------");
        sender_number.setTextContent(createNumberCard());

        Element recipient_number = document.createElement("recipient_number");
        System.out.println("---------------Recipient_number---------------");
        recipient_number.setTextContent(createNumberCard());

        Element amount = document.createElement("amount");
        amount.setTextContent(formatDouble());

        Element currency = document.createElement("currency");
        currency.setTextContent(String.valueOf(inputCurrency()));

        Element dataTime = document.createElement("dataTime");
        dataTime.setTextContent(String.valueOf(inputDateTime()));

        Element purpose = document.createElement("purpose");
        System.out.println("Input the purpose of payment: ");
        purpose.setTextContent(inputValidateStr());

        Element status = document.createElement("status");
        status.setTextContent(String.valueOf(inputStatus()));


        // Добавляем внутренние элементы транзакции в элемент <element>
        element.appendChild(id);

        element.appendChild(sender_number);
        element.appendChild(recipient_number);
        element.appendChild(amount);
        element.appendChild(currency);
        element.appendChild(dataTime);
        element.appendChild(purpose);
        element.appendChild(status);

        log.info("Transaction successfully written to file");
        return element;
    }
}
