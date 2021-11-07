package br.com.nielsonferreira.controller;

import br.com.nielsonferreira.data.vo.BookVO;
import br.com.nielsonferreira.services.BookServices;
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


@Api(tags = "BookEndpoint")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookServices services;

    @ApiOperation(value = "Find all books")
    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<PagedResources<BookVO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "limit", defaultValue = "12") int limit,
                                                         @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                         PagedResourcesAssembler assembler){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "title"));

        Page<BookVO> books = services.findAll(pageable);
        books.stream()
                .forEach(p -> p.add(
                        linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()
                ));
        return new ResponseEntity<>(assembler.toResource(books), HttpStatus.OK);
    }

    @ApiOperation(value = "Find all books")
    @GetMapping(value = ("/findBookByTitle/{title}"), produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<PagedResources<BookVO>> findBookByTitle(@PathVariable("title") String title,
                                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "limit", defaultValue = "12") int limit,
                                                                  @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                  PagedResourcesAssembler assembler){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "title"));

        Page<BookVO> books = services.findBookByTitle(title, pageable);
        books.stream()
                .forEach(p -> p.add(
                        linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()
                ));
        return new ResponseEntity<>(assembler.toResource(books), HttpStatus.OK);
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
