package au.com.bueno.search.index.query;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import au.com.bueno.search.entity.Device;
import au.com.bueno.search.index.exception.ApplicationException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

class TermBuilderTest {

  @Test
  void shouldReturnTermWithNameAndStringValue() throws NoSuchFieldException {
    Device device = new Device("abcd", "Aircon", List.of());
    Field field = device.getClass().getDeclaredField("id");
    field.setAccessible(true);
    Term term = new TermBuilder().build(device, field);

    assertThat(term, is(new Term("device.id", "abcd")));
  }

  @Test
  void shouldHandleExceptionOnPermissionIssue() throws NoSuchFieldException {
    Device device = new Device("abcd", "Aircon", List.of());
    Field field = device.getClass().getDeclaredField("id");

    ApplicationException applicationException = assertThrows(
        ApplicationException.class,
        () ->
            new TermBuilder().build(device, field));

    assertThat(applicationException.getMessage(), is("Contact support"));
  }
}