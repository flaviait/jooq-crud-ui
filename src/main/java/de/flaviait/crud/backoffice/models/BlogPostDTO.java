package de.flaviait.crud.backoffice.models;

import java.util.List;

public class BlogPostDTO extends de.flaviait.crud.jooq.tables.pojos.BlogPost implements IdentifiableDTO {

  private List<Long> readers;

  public List<Long> getReaders() {
    return readers;
  }

  public void setReaders(List<Long> readers) {
    this.readers = readers;
  }
}
