package de.flaviait.crud.backoffice.models;

import java.util.List;

public class BlogPost extends de.flaviait.crud.jooq.tables.pojos.BlogPost {

  private List<Long> reviewers;

  public List<Long> getReviewers() {
    return reviewers;
  }

  public void setReviewers(List<Long> reviewers) {
    this.reviewers = reviewers;
  }
}
