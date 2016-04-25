package de.flaviait.crud.backoffice.controllers;

import de.flaviait.crud.backoffice.models.IdentifiableDTO;
import de.flaviait.crud.backoffice.repositories.AbstractCRUDRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractCRUDBackofficeController<Resource extends IdentifiableDTO> extends AbstractBackofficeController<Resource> {

  AbstractCRUDBackofficeController(AbstractCRUDRepository<?, Resource> repository) {
    super(repository);
  }

  @Override
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public List<Resource> page(@RequestParam(value = "_page", defaultValue = "1") Integer page,
                             @RequestParam(value = "_perPage", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "_sortDir", defaultValue = "DESC") String sortOrder,
                             @RequestParam(value = "_sortField", defaultValue = "id") String sortField) {
    return super.page(page, pageSize, sortOrder, sortField);
  }

  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Resource get(@PathVariable("id") Long id) {
    return super.get(id);
  }

  @Override
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Void> create(@RequestBody Resource dto, @RequestHeader String host) {
    return super.create(dto, host);
  }

  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Resource dto) {
    return super.update(id, dto);
  }

  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    return super.delete(id);
  }

}
