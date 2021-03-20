package au.com.bueno.search.repository;

import java.util.Collection;

public interface Repository<T, I> {
  T findById(I id);

  Collection<T> findAll();
}
