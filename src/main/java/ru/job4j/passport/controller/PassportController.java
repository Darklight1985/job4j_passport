package ru.job4j.passport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.passport.domain.Passport;
import ru.job4j.passport.handlers.Operation;
import ru.job4j.passport.repository.PassportRepository;
import ru.job4j.passport.service.PassportService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/passport")
public class PassportController {
  private PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> create(@Valid @RequestBody Passport passport) {
        return new ResponseEntity<>(
                this.passportService.create(passport),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/delete/{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@Valid @PathVariable int id) {
        ResponseEntity<Void> rsl = null;
        if (this.passportService.delete(id)) {
            rsl = ResponseEntity.ok().build();
        } else {
            rsl =  ResponseEntity.notFound().build();
        }
        return rsl;
    }

    @GetMapping("/find")
    public ResponseEntity<List<Passport>> findAll() {
        return new ResponseEntity<>(
                this.passportService.findAll(),
                HttpStatus.OK
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Passport passport) {
       return passportService.update(id, passport) ? ResponseEntity.ok().build()
               : ResponseEntity.notFound().build();
    }

    @GetMapping("/find/{series}")
    public ResponseEntity<List<Passport>> findSeries(@PathVariable int series) {
        return new ResponseEntity<>(
                this.passportService.findSeries(series),
                HttpStatus.OK
        );
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<Passport>> findExpired() {
        return new ResponseEntity<>(
                this.passportService.findExpired(),
                HttpStatus.OK
        );
    }

    @GetMapping("/find-replace")
    public ResponseEntity<List<Passport>> findReplaced() {
        return new ResponseEntity<>(
                this.passportService.findReplaced(),
                HttpStatus.OK
        );
    }
}
