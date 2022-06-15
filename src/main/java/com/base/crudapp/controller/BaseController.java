package com.base.crudapp.controller;

import com.base.crudapp.entity.BaseEntity;
import com.base.crudapp.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BaseController<T extends BaseEntity<ID>, ID extends Serializable> {
    private final BaseService<T, ID> baseService;

    public BaseController(BaseService<T, ID> baseService) {
        this.baseService = baseService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create new object")
    public ResponseEntity<T> create(@RequestBody @Valid T entity) {
        return ResponseEntity.ok(baseService.save(entity));
    }

    @PutMapping("/update")
    @Operation(summary = "Update existing object")
    public ResponseEntity<T> update(@RequestBody @Valid T entity) {
        return ResponseEntity.ok(baseService.update(entity));
    }

    @DeleteMapping("/soft-delete/{id}")
    @Operation(summary = "Soft delete object by ID. Entity will be marked inactive and kept in DB.")
    public ResponseEntity<T> softDeleteById(@PathVariable ID id) {
        T result = baseService.softDeleteById(id);
        if (Objects.isNull(result)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete object by ID.")
    public ResponseEntity<T> deleteById(@PathVariable ID id) {
        baseService.deleteById(id);
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/get")
    @Operation(summary = "Get all objects")
    public ResponseEntity<List<T>> all() {
        return ResponseEntity.ok(baseService.findAll());
    }

    @GetMapping("/get/page")
    @Operation(summary = "Get all objects")
    public ResponseEntity<Page<T>> page(Pageable param) {
        return ResponseEntity.ok(baseService.findAll(param));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get a object by id")
    public ResponseEntity<T> getById(@PathVariable ID id) {
        Optional<T> optional = baseService.findById(id);
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



}
