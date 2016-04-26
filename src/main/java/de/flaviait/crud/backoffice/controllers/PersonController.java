package de.flaviait.crud.backoffice.controllers;

import de.flaviait.crud.backoffice.models.PersonDTO;
import de.flaviait.crud.backoffice.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crud/persons")
public class PersonController extends AbstractCRUDBackofficeController<Long, PersonDTO> {

  @Autowired
  public PersonController(PersonRepository personRepository) {
    super(personRepository);
  }

}
