package workingWithFiles.yamlDoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import workingWithFiles.bankTransactions.model.Bank;
import workingWithFiles.bankTransactions.model.Transaction;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.nio.file.Files.newInputStream;
@Slf4j
public class YamlReader {

     public static Bank readerYAML(String path) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            File file = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
            // Создание нового ObjectMapper как YAMLFactory
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

         try {
             return mapper.readValue(file, Bank.class);
         } catch (IOException e) {
             log.info("Error " + e.getMessage());
         }
         return null;
     }
}
