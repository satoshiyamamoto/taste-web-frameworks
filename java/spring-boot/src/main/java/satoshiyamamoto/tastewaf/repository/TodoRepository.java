package satoshiyamamoto.tastewaf.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import satoshiyamamoto.tastewaf.entity.Todo;

import java.util.List;

@RepositoryDefinition(domainClass = Todo.class, idClass = Long.class)
public interface TodoRepository extends PagingAndSortingRepository<Todo, Long> {

    List<Todo> findByCompleted(boolean complated);

}
