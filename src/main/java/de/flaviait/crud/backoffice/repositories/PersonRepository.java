package de.flaviait.crud.backoffice.repositories;

import de.flaviait.crud.backoffice.models.PersonDTO;
import de.flaviait.crud.jooq.tables.records.PersonRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.flaviait.crud.jooq.tables.Person.PERSON;

@Component
public class PersonRepository extends AbstractCRUDRepository<PersonRecord, Long, PersonDTO> {

  @Autowired
  public PersonRepository(DSLContext dslContext) {
    super(dslContext, PERSON, PERSON.ID, PersonDTO.class);
  }

}
