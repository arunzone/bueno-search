package au.com.bueno.search.index.query;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

class SimpleAnyMatchQueryBuilderTest {

  @Test
  void shouldReturnDefaultQueryOnEmptyInput() {
    SimpleAnyMatchQueryBuilder builder = new SimpleAnyMatchQueryBuilder();
    Map<String, String> parameters = Map.of();

    SimpleAnyMatchQuery query = builder.queryFrom(parameters);

    SimpleAnyMatchQuery defaultQuery = new SimpleAnyMatchQuery(10, 0, List.of());
    assertThat(query, is(defaultQuery));
  }

  @Test
  void shouldReturnDefaultLimitTo10ForNonNumeric() {
    SimpleAnyMatchQueryBuilder builder = new SimpleAnyMatchQueryBuilder();
    Map<String, String> parameters = Map.of("limit", "ab");

    SimpleAnyMatchQuery query = builder.queryFrom(parameters);

    assertThat(query.getLimit(), is(10));
  }

  @Test
  void shouldReturnDefaultOffsetTo0ForNonNumeric() {
    SimpleAnyMatchQueryBuilder builder = new SimpleAnyMatchQueryBuilder();
    Map<String, String> parameters = Map.of("offset", "ab");

    SimpleAnyMatchQuery query = builder.queryFrom(parameters);

    assertThat(query.getOffset(), is(0));
  }

  @Test
  void shouldReturnAdjustedOffsetByLimit() {
    SimpleAnyMatchQueryBuilder builder = new SimpleAnyMatchQueryBuilder();
    Map<String, String> parameters = Map.of("offset", "3");

    SimpleAnyMatchQuery query = builder.queryFrom(parameters);

    assertThat(query.getOffset(), is(3));
  }

  @Test
  void shouldReturnDefaultTermsWhenNoTermInput() {
    SimpleAnyMatchQueryBuilder builder = new SimpleAnyMatchQueryBuilder();
    Map<String, String> parameters = Map.of("device.name", "abc", "sensor.type", "NUMBER");

    SimpleAnyMatchQuery query = builder.queryFrom(parameters);

    assertThat(query.getTerms(), containsInAnyOrder(
        new Term("device.name", "abc"),
        new Term("sensor.type", "NUMBER")
    ));
  }

  @Test
  void shouldReturnGivenTerms() {
    SimpleAnyMatchQueryBuilder builder = new SimpleAnyMatchQueryBuilder();
    Map<String, String> parameters = Map.of("offset", "10", "limit", "10");

    SimpleAnyMatchQuery query = builder.queryFrom(parameters);

    assertThat(query.getTerms(), is(empty()));
  }

}