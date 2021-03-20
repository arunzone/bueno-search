package au.com.bueno.search.index.query;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class SimpleAnyMatchQueryBuilder {
  public static final String DEFAULT_LIMIT = "10";
  public static final String DEFAULT_OFFSET = "0";
  private static final Pattern INTEGER_PATTERN = Pattern.compile("^\\d+$");

  public SimpleAnyMatchQuery queryFrom(Map<String, String> parameters) {
    Integer limit = getLimit(parameters);
    Integer offset = getOffset(parameters);
    List<Term> terms = getTerms(parameters);

    return new SimpleAnyMatchQuery(limit, offset, terms);
  }

  private List<Term> getTerms(Map<String, String> parameters) {
    return parameters.entrySet().stream()
        .filter(entry -> !(entry.getKey().equals("limit") || entry.getKey().equals("offset")))
        .map(entry -> new Term(entry.getKey(), entry.getValue()))
        .collect(Collectors.toList());
  }

  private Integer getLimit(Map<String, String> parameters) {
    String limit = parameters.getOrDefault("limit", DEFAULT_LIMIT);
    if (INTEGER_PATTERN.matcher(limit).matches()) {
      return Integer.valueOf(limit);
    } else {
      return Integer.valueOf(DEFAULT_LIMIT);
    }
  }

  private Integer getOffset(Map<String, String> parameters) {
    String offset = parameters.getOrDefault("offset", DEFAULT_OFFSET);
    if (INTEGER_PATTERN.matcher(offset).matches()) {
      return Integer.valueOf(offset);
    } else {
      return Integer.valueOf(DEFAULT_OFFSET);
    }
  }

}
