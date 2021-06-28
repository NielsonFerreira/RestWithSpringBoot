package br.com.nielsonferreira.controller;

import br.com.nielsonferreira.data.vo.PersonVO;
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
    public List<PersonVO> findAll(){
        return services.findAll();
    }

    @GetMapping("/{id}")
    public PersonVO findById(@PathVariable("id") Long id){
        return services.findById(id);
    }

    @PostMapping
    public PersonVO create(@RequestBody PersonVO person){
        return services.create(person);
    }

    @PutMapping
    public PersonVO update(@RequestBody PersonVO person){
        return services.update(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        services.delete(id);
        return ResponseEntity.ok().build();
    }
}
