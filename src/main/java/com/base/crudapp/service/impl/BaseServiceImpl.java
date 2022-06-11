package com.base.crudapp.service.impl;

import com.base.crudapp.entity.BaseEntity;
import com.base.crudapp.repository.BaseRepository;
import com.base.crudapp.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class BaseServiceImpl<T extends BaseEntity<ID>, ID extends Serializable> implements BaseService<T, ID> {

    private final BaseRepository<T, ID> baseRepository;

    @Autowired
    public BaseServiceImpl(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public T save(T entity) {
        return baseRepository.save(entity);
    }

    @Override
    public T update(T entity) {
        return baseRepository.findById(entity.getId()).map(savedEntity -> {
            entity.setId(savedEntity.getId());
            entity.setVersion(savedEntity.getVersion());
            return baseRepository.save(entity);
        }).orElseGet(() -> baseRepository.save(entity));
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable param) {
        return baseRepository.findAll(param);
    }

    @Override
    public Optional<T> findById(ID entityId) {
        return baseRepository.findById(entityId);
    }

    @Override
    public T softDeleteById(ID entityId) {
        T alreadyPresentEntity = this.findById(entityId).orElseThrow(() -> new EntityNotFoundException("No entry found for " + entityId));
        alreadyPresentEntity.setIsActive(Boolean.FALSE);
        return save(alreadyPresentEntity);
    }

    @Override
    public void deleteById(ID entityId) {
        boolean exists = baseRepository.existsById(entityId);
        if (exists) {
            baseRepository.deleteById(entityId);
            return;
        }
        throw new EntityNotFoundException("No entry found for " + entityId);
    }
}
