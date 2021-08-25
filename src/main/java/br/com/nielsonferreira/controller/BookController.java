package br.com.nielsonferreira.controller;

import br.com.nielsonferreira.data.vo.BookVO;
import br.com.nielsonferreira.services.BookServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Api(tags = "BookEndpoint")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookServices services;

    @ApiOperation(value = "Find all books")
    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public List<BookVO> findAll(){
        List<BookVO> books = services.findAll();
        books.stream().forEach(p -> p.add(
                        linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()
                ));
        return books;
    }

    @ApiOperation(value = "Find a specific book by your ID")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public BookVO findById(@PathVariable("id") Long id){
        BookVO bookVO = services.findById(id);
        bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return bookVO;
    }

    @ApiOperation(value = "Create a new book")
    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public BookVO create(@RequestBody BookVO book){
        BookVO bookVO = services.create(book);
        bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
        return bookVO;
    }

    @ApiOperation(value = "Update a specific book")
    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public BookVO update(@RequestBody BookVO book){
        BookVO bookVO = services.update(book);
        bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
        return bookVO;
    }

    @ApiOperation(value = "Delete a specific book by your ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        services.delete(id);
        return ResponseEntity.ok().build();
    }
}
