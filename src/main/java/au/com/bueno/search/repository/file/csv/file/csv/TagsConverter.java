package au.com.bueno.search.repository.file.csv.file.csv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;

@NoArgsConstructor
public class TagsConverter extends AbstractBeanField {

  @Override
  protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(value, List.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return emptyList();
  }
}
