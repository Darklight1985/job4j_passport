package ru.job4j.request.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.passport.domain.Passport;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {
    private static final String URI = "http://localhost:8080/passport/";

    @Autowired
    private RestTemplate rest;

    @PostMapping("/")
    public ResponseEntity<Passport> create(@RequestBody Passport passport) {
        Passport rsl = rest.postForObject(URI + "save", passport, Passport.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(URI + "delete/" + id, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Passport passport) {
        rest.put(URI + "update/" + id, passport, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public List<Passport> findAll() {
        return getList(String.format(
                "%s/find", URI
        ));
    }

    private List<Passport> getList(String url) {
        List<Passport> body = rest.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
        return body == null ? Collections.emptyList() : body;
    }
}
