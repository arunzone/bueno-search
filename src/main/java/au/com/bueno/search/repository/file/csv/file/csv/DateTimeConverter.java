package au.com.bueno.search.repository.file.csv.file.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
public class DateTimeConverter extends AbstractBeanField {

  @Override
  protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
    return ZonedDateTime.parse(value);
  }

}