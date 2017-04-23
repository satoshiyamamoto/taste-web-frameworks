package satoshiyamamoto.tastewaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import satoshiyamamoto.tastewaf.entity.Todo;
import satoshiyamamoto.tastewaf.repository.TodoRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private TodoRepository repository;

    @Autowired
    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Todo> index(@RequestParam(name = "page", defaultValue = "0") Integer page,
                            @RequestParam(name = "size", defaultValue = "20") Integer size) {
        return repository.findAll(new PageRequest(page, size)).getContent();
    }

    @GetMapping("{id}")
    public ResponseEntity show(@PathVariable("id") Long id) {
        if (!repository.exists(id)) return ResponseEntity.notFound().build();

        Todo todo = repository.findOne(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid Todo todo,
                                 BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.unprocessableEntity().build();

        Todo entity = repository.save(todo);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id,
                                 @RequestBody @Valid Todo todo,
                                 BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.unprocessableEntity().build();
        if (!repository.exists(id)) return ResponseEntity.notFound().build();

        Todo entity = repository.findOne(id);
        entity.setTitle(todo.getTitle());
        entity.setCompleted(todo.isCompleted());
        repository.save(entity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity destroy(@PathVariable("id") Long id) {
        if (!repository.exists(id)) return ResponseEntity.notFound().build();

        repository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
