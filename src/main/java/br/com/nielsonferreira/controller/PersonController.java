package br.com.nielsonferreira.controller;

import br.com.nielsonferreira.data.vo.PersonVO;
import br.com.nielsonferreira.services.PersonServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//@Api(value = "Person Endpoint", tags = {"Person Endpoint"})
@Api(tags = "PersonEndpoint")
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices services;

    @ApiOperation(value = "Find all people")
    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<PagedResources<PersonVO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "limit", defaultValue = "12") int limit,
                                                            @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                            PagedResourcesAssembler assembler){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

        Page<PersonVO> persons = services.findAll(pageable);
        persons.stream()
                .forEach(p -> p.add(
                        linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()
                ));
        return new ResponseEntity<>(assembler.toResource(persons), HttpStatus.OK);
    }

    @ApiOperation(value = "Find all people")
    @GetMapping(value = "/findPersonByName/{firstName}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<PagedResources<PersonVO>> findPersonByName(@PathVariable("firstName") String firstName,
                                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "limit", defaultValue = "12") int limit,
                                                                     @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                     PagedResourcesAssembler assembler){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

        Page<PersonVO> persons = services.findPersonByName(firstName, pageable);
        persons.stream()
                .forEach(p -> p.add(
                        linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()
                ));
        return new ResponseEntity<>(assembler.toResource(persons), HttpStatus.OK);
    }

    @ApiOperation(value = "Find a specific person by your ID")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO findById(@PathVariable("id") Long id){
        PersonVO personVO = services.findById(id);
        personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return personVO;
    }

    @ApiOperation(value = "Create a new person")
    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO create(@RequestBody PersonVO person){
        PersonVO personVO = services.create(person);
        personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
        return personVO;
    }

    @ApiOperation(value = "Update a specific person")
    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO update(@RequestBody PersonVO person){
        PersonVO personVO = services.update(person);
        personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
        return personVO;
    }

    @ApiOperation(value = "Disable a specific person by your ID")
    @PatchMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO disablePerson(@PathVariable("id") Long id){
        PersonVO personVO = services.disablePerson(id);
        personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return personVO;
    }

    @ApiOperation(value = "Delete a specific person by your ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        services.delete(id);
        return ResponseEntity.ok().build();
    }
}
