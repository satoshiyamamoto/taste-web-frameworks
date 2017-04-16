package satoshiyamamoto.tastewaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import satoshiyamamoto.tastewaf.entity.Todo;
import satoshiyamamoto.tastewaf.repository.TodoRepository;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private TodoRepository repository;

    @Autowired
    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        return repository.save(todo);
    }

    @PutMapping("{id}")
    public Todo update(@PathVariable("id") Long id,
                       @RequestBody Todo todo) {
        if (!repository.exists(id)) throw new NotFoundException();

        Todo entity = repository.findOne(id);
        entity.setTitle(todo.getTitle());
        entity.setCompleted(todo.isCompleted());
        return repository.save(entity);
    }

    @DeleteMapping("{id}")
    public void destroy(@PathVariable("id") Long id) {
        if (!repository.exists(id)) throw new NotFoundException();

        repository.delete(id);
    }

    @GetMapping
    public List<Todo> index(@RequestParam(name = "page", defaultValue = "0") Integer page,
                            @RequestParam(name = "size", defaultValue = "20") Integer size) {
        return repository.findAll(new PageRequest(page, size)).getContent();
    }

    @GetMapping("{id}")
    public Todo show(@PathVariable("id") Long id) {
        if (!repository.exists(id)) throw new NotFoundException();

        return repository.findOne(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class NotFoundException extends RuntimeException {
    }
}
