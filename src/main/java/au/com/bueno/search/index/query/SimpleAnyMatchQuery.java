package au.com.bueno.search.index.query;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;

import java.util.List;

@Generated
@EqualsAndHashCode
@AllArgsConstructor
public class SimpleAnyMatchQuery {
  @Getter
  private final Integer limit;
  @Getter
  private final Integer offset;
  @Getter
  private final List<Term> terms;
}
