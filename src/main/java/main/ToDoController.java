package main;

import main.model.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.ToDo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping("/tasks/")
    public List<ToDo> getAll() {
        ArrayList<ToDo> listToDo = new ArrayList<>();
        Iterable<ToDo> toDoIterable = toDoRepository.findAll();
        for (ToDo toDo : toDoIterable) {
            listToDo.add(toDo);
        }
        return listToDo;
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable int id) {
        Optional<ToDo> optionalToDo = toDoRepository.findById(id);
        if (!optionalToDo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalToDo.get(), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/")
    public void deleteAll() {
        toDoRepository.deleteAll();
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        Optional<ToDo> optionalToDo = toDoRepository.findById(id);
        if (optionalToDo.isPresent()) {
            toDoRepository.deleteById(id);
            return new ResponseEntity(id, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }

    @PostMapping("/tasks/")
    public int add(ToDo toDo) {
        ToDo newToDo = toDoRepository.save(toDo);
        return newToDo.getId();
    }

    @PostMapping("/tasks/{id}")
    public ResponseEntity post(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity put(@PathVariable int id, String title, String description, boolean done) {
        Optional<ToDo> optionalToDo = toDoRepository.findById(id);
        if (optionalToDo.isPresent()) {
            ToDo toDo = optionalToDo.get();
            toDo.setTitle(title);
            toDo.setDescription(description);
            toDo.setDone(done);
            toDoRepository.save(toDo);
            return new ResponseEntity(toDo, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
