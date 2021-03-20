package au.com.bueno.search.repository.file.csv.file.csv;

import au.com.bueno.search.repository.file.csv.InvalidInputFileException;
import au.com.bueno.search.repository.file.csv.file.csv.dto.Device;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class CsvFileInputReader {

  private final Environment env;

  public CsvFileInputReader(@Autowired Environment env) {
    this.env = env;
  }

  public List<Device> read() {
    try {
      Reader reader = Files.newBufferedReader(Path.of(fileName()));
      CsvToBean<Device> csvReader = new CsvToBeanBuilder(reader)
          .withType(Device.class)
          .withSeparator(',')
          .withIgnoreLeadingWhiteSpace(true)
          .withIgnoreEmptyLine(true)
          .build();

      return csvReader.parse();
    } catch (Exception e) {
      throw new InvalidInputFileException(String.format("Invalid input data file: %s", fileName()));
    }
  }

  private String fileName() {
    return env.getProperty("DATA_FILE");
  }


}
