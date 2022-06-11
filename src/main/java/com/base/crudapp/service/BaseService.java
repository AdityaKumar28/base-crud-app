package com.base.crudapp.service;

import com.base.crudapp.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {

    T save(T entity);

    T update(T entity);

    List<T> findAll();

    Page<T> findAll(Pageable param);

    Optional<T> findById(ID entityId);

    T softDeleteById(ID entityId);

    void deleteById(ID entityId);
}
