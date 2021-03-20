package au.com.bueno.search.repository;

public interface Repository<T, I> {
  T findById(I id);
}
