package de.flaviait.crud.backoffice.controllers;

import de.flaviait.crud.backoffice.models.IdentifiableDTO;
import de.flaviait.crud.backoffice.repositories.AbstractCRUDRepository;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.List;
import java.util.Map;

abstract class AbstractBackofficeController<ID, Resource extends IdentifiableDTO<ID>> {

  final AbstractCRUDRepository<?, ID, Resource> repository;
  JsonParser jsonParser;

  AbstractBackofficeController(AbstractCRUDRepository<?, ID, Resource> repository) {
    this.repository = repository;
    this.jsonParser = new BasicJsonParser();
  }

  protected List<Resource> page(Integer page, Integer pageSize, String sortOrder, String sortField, String queryString) {
    Map<String, Object> query = queryString != null ? jsonParser.parseMap(queryString) : null;
    return repository.getPage(page, pageSize, sortField, sortOrder, query);
  }

  protected Resource get(ID id) {
    Resource resource = repository.getById(id);
    if (resource == null) {
      throw new ResourceNotFoundException();
    }
    return resource;
  }

  protected ResponseEntity<Void> update(ID id, Resource resource) {
    repository.update(id, resource);
    return buildUpdateResponse();
  }

  protected ResponseEntity<Void> create(Resource resource, String host) {
    Resource persistedResource = repository.create(resource);
    return buildCreateResponse(host, persistedResource);
  }

  protected ResponseEntity<Void> delete(ID id) {
    repository.delete(id);
    return buildDeleteResponse();
  }

  protected ResponseEntity<Void> buildUpdateResponse() {
    return ResponseEntity.noContent().build();
  }

  protected ResponseEntity<Void> buildCreateResponse(String host, Resource persistedResource) {
    return ResponseEntity.created(URI.create(getResourceLocation(persistedResource, host))).build();
  }

  protected ResponseEntity<Void> buildDeleteResponse() {
    return ResponseEntity.noContent().build();
  }

  protected String getResourceLocation(Resource persistedResource, String host) {
    String resourcePath = this.getClass().getAnnotation(RequestMapping.class).value()[0];
    return host + resourcePath + "/" + persistedResource.getId();
  }

  public static class ResourceNotFoundException extends RuntimeException {
  }
}
