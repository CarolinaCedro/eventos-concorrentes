package com.cedro.eventos.controller;

import com.cedro.eventos.services.RestServiceAbstractImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public abstract class AbstractController<T> {

    protected abstract RestServiceAbstractImpl<T> getService();

    @GetMapping()
    public ResponseEntity<List<T>> findAll() {
        List<T> result = this.getService().findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<T>> findById(@PathVariable String id) {
        Optional<T> result = this.getService().findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T resource) {
        T createdResource = this.getService().create(resource);
        return ResponseEntity.ok(createdResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable String id, @RequestBody T resource) {
        T updatedResource = this.getService().update(id, resource);
        return ResponseEntity.ok(updatedResource);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.getService().deleteById(id);
    }


}
