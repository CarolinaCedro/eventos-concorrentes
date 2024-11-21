package com.cedro.eventos.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class RestServiceAbstractImpl<T> implements Rest<T> {

    protected abstract JpaRepository<T, String> getRepository();

    @Override
    public List<T> findAll() {
        return this.getRepository().findAll();
    }

    @Override
    public Optional<T> findById(String id) {
        return this.getRepository().findById(id);
    }

    @Override
    public T create(T resource) {
        return this.getRepository().save(resource);
    }

    @Override
    public T update(String id, T resource) {
        Optional<T> existingResource = this.getRepository().findById(id);
        if (existingResource.isPresent()) {
            return this.getRepository().save(resource);
        } else {
            throw new RuntimeException("Recurso com id " + id + " não encontrado");
        }
    }

    @Override
    public void deleteById(String id) {
        Optional<T> existingResource = this.getRepository().findById(id);
        if (existingResource.isPresent()) {
            this.getRepository().deleteById(id);
        } else {
            throw new RuntimeException("Recurso com id " + id + " não encontrado");
        }
    }
}
