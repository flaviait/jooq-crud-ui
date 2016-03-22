package de.flaviait.crud.backoffice.controllers;

import de.flaviait.crud.backoffice.models.Person;
import de.flaviait.crud.backoffice.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crud/persons")
public class PersonController extends AbstractBackofficeController<Person> {

  @Autowired
  public PersonController(PersonRepository personRepository) {
    super(personRepository);
  }

  @Override
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public List<Person> page(@RequestParam(value = "_page", defaultValue = "1") Integer page,
                           @RequestParam(value = "_perPage", defaultValue = "30") Integer pageSize,
                           @RequestParam(value = "_sortDir", defaultValue = "DESC") String sortOrder,
                           @RequestParam(value = "_sortField", defaultValue = "id") String sortField) {
    return super.page(page, pageSize, sortOrder, sortField);
  }

  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Person get(@PathVariable("id") Long id) {
    return super.get(id);
  }

  @Override
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public Person create(@RequestBody Person person) {
    return super.create(person);
  }

  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public Person update(@PathVariable Long id, @RequestBody Person person) {
    return super.update(id, person);
  }

  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    return super.delete(id);
  }
}
