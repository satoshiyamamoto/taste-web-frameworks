package satoshiyamamoto.tastewaf.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import satoshiyamamoto.tastewaf.entity.Todo;
import satoshiyamamoto.tastewaf.repository.TodoRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Todo> index(@PageableDefault Pageable page) {
        return repository.findAll(page).getContent();
    }

    @GetMapping("{id}")
    public ResponseEntity show(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
                                 @Validated @RequestBody Todo todo) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setTitle(todo.getTitle());
                    entity.setCompleted(todo.isCompleted());
                    repository.save(entity);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity destroy(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
