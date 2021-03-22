package au.com.bueno.search.index.query;

import au.com.bueno.search.index.exception.ApplicationException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

import static java.lang.String.format;

@Component
public class TermBuilder {

  public <T> Term build(T reference, Field field) {
    String termName = format("%s.%s", field.getDeclaringClass().getSimpleName().toLowerCase(), field.getName());
    try {
      return new Term(termName, String.valueOf(field.get(reference)));
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      throw new ApplicationException("Contact support");
    }
  }
}
