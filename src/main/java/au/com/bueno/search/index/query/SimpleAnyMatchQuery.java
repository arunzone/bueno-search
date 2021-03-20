package au.com.bueno.search.index.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class SimpleAnyMatchQuery {
  @Getter
  private final Integer limit;
  @Getter
  private final List<Term> terms;
}
