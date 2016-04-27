package de.flaviait.crud.backoffice.repositories;

import de.flaviait.crud.backoffice.models.PersonDTO;
import de.flaviait.crud.jooq.tables.records.PersonRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static de.flaviait.crud.jooq.tables.Person.PERSON;

@Component
public class PersonRepository extends AbstractCRUDRepository<PersonRecord, Long, PersonDTO> {

  @Autowired
  public PersonRepository(DSLContext dslContext) {
    super(dslContext, PERSON, PERSON.ID, PersonDTO.class);
  }

  @Override
  protected Condition filter(Map<String, Object> filterQuery) {
    if(filterQuery.containsKey("name")) {
      return PERSON.NAME.likeIgnoreCase("%" + filterQuery.getOrDefault("name", "") + "%");
    } else {
      return super.filter(filterQuery);
    }
  }
}
