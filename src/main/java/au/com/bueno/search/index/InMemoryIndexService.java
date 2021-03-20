package au.com.bueno.search.index;

import au.com.bueno.search.entity.Device;
import au.com.bueno.search.index.query.SimpleAnyMatchQuery;
import au.com.bueno.search.index.query.Term;
import au.com.bueno.search.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InMemoryIndexService {

  private final Repository<Device, String> repository;
  private final InMemoryIndexBuilder inMemoryIndexBuilder;
  private final Map<Term, Set<String>> index = new HashMap<>();

  public InMemoryIndexService(@Autowired Repository<Device, String> repository, @Autowired InMemoryIndexBuilder inMemoryIndexBuilder) {
    this.repository = repository;
    this.inMemoryIndexBuilder = inMemoryIndexBuilder;
  }

  public Set<String> search(SimpleAnyMatchQuery query) {
    indexIfNeeded();

    if (query.getTerms().isEmpty()) {
      return repository.findAll().stream()
          .skip(query.getOffset())
          .limit(query.getLimit())
          .map(Device::getId)
          .collect(Collectors.toSet());
    }

    return query.getTerms().stream()
        .map(index::get)
        .flatMap(Collection::stream)
        .skip(query.getOffset())
        .limit(query.getLimit())
        .collect(Collectors.toSet());
  }

  private void indexIfNeeded() {
    if (index.isEmpty()) {
      inMemoryIndexBuilder
          .withDevices(repository.findAll()).
          withIndex(index)
          .build();
    }
  }

}
