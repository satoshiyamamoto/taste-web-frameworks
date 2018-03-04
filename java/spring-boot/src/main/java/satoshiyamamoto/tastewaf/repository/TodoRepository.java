package satoshiyamamoto.tastewaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import satoshiyamamoto.tastewaf.entity.Todo;

import java.util.List;

@RepositoryDefinition(domainClass = Todo.class, idClass = Long.class)
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByCompleted(boolean completed);

}
