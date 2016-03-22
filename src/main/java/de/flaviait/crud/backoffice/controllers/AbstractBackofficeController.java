package de.flaviait.crud.backoffice.controllers;

import de.flaviait.crud.backoffice.repositories.AbstractCRUDRepository;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractBackofficeController<Resource extends Serializable> {

  protected final AbstractCRUDRepository<?, Resource> repository;

  protected AbstractBackofficeController(AbstractCRUDRepository<?, Resource> repository) {
    this.repository = repository;
  }

  public List<Resource> page(Integer page, Integer pageSize, String sortOrder, String sortField) {
    return repository.getPage(page, pageSize, sortField, sortOrder);
  }

  public Resource get(Long id) {
    Resource resource = repository.getById(id);
    if (resource == null) {
      throw new ResourceNotFoundException();
    }
    return resource;
  }

  public Resource update(Long id, Resource resource) {
    return repository.update(id, resource);
  }

  public Resource create(Resource resource) {
    return repository.create(resource);
  }

  public ResponseEntity<Void> delete(Long id) {
    repository.delete(id);
    return ResponseEntity.ok().build();
  }

  private static class ResourceNotFoundException extends RuntimeException {
  }
}
