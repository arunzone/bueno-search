package au.com.bueno.search.repository.file.csv.file.csv;

import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

class TagsConverterTest {
  @Test
  void shouldReturnTagsAsList() throws CsvDataTypeMismatchException, CsvConstraintViolationException {
    TagsConverter tagsConverter = new TagsConverter();
    List<String> tags = (List<String>) tagsConverter.convert("[\"a\", \"b\", \"c\"]");

    assertThat(tags, contains("a", "b", "c"));
  }

  @Test
  void shouldReturnTagsAsEmptyList() throws CsvDataTypeMismatchException, CsvConstraintViolationException {
    TagsConverter tagsConverter = new TagsConverter();
    List<String> tags = (List<String>) tagsConverter.convert(null);

    assertThat(tags, Is.is(empty()));
  }
}