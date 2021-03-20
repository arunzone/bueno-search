package au.com.bueno.search.index.query;

import au.com.bueno.search.index.exception.ApplicationException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

import static java.lang.String.format;

@Component
public class TermBuilder<T> {

  private T reference;
  private Field field;

  public TermBuilder<T> withReference(T device) {
    this.reference = device;
    return this;
  }

  public TermBuilder<T> withField(Field field) {
    this.field = field;
    return this;
  }

  public Term build() {
    String termName = format("%s.%s", field.getDeclaringClass().getSimpleName().toLowerCase(), field.getName());
    try {
      return new Term(termName, String.valueOf(field.get(reference)));
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      throw new ApplicationException("Contact support");
    }
  }
}
