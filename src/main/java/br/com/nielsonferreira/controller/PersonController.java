package br.com.nielsonferreira.controller;

import br.com.nielsonferreira.model.Person;
import br.com.nielsonferreira.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices services;

    @GetMapping
    public List<Person> findAll(){
        return services.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") Long id){
        return services.findById(id);
    }

    @PostMapping
    public Person create(@RequestBody Person person){
        return services.create(person);
    }

    @PutMapping
    public Person update(@RequestBody Person person){
        return services.update(person);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        services.delete(id);
        return ResponseEntity.ok().build();
    }
}
