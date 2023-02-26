package workingWithFiles.xmlDoc.myParser;

import workingWithFiles.bankTransactions.model.Bank;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
@Slf4j
public class SaxMyParser {

    public static Bank parseSax(String path){

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SaxParserHandler handler = new SaxParserHandler();
        SAXParser parser;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            log.info("Open Sax parser error " + e);
            return null;
        }
        File file = new File(path);
        try {
            assert parser != null;
            parser.parse(file, handler);
        } catch (SAXException | IOException e) {
            log.info("Parsing error " + e);
            return null;
        }

        return handler.getBank();
    }
}
