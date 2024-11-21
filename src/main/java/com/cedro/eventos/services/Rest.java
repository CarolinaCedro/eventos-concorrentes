package com.cedro.eventos.services;

import java.util.List;
import java.util.Optional;

public interface Rest<T> {

    List<T> findAll();

    Optional<T> findById(String id);

    T create(T resource);

    T update(String id, T resource);

    void deleteById(String id);


}
