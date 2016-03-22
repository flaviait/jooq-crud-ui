package de.flaviait.crud.backoffice.repositories;

import de.flaviait.crud.jooq.tables.records.ReviewersRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static de.flaviait.crud.jooq.tables.Reviewers.REVIEWERS;

@Component
public class ReviewersRepository extends AbstractManyToManyRelationRepository<ReviewersRecord> {

  @Autowired
  public ReviewersRepository(DSLContext dslContext) {
    super(dslContext, REVIEWERS, REVIEWERS.BLOG_POST, REVIEWERS.PERSON);
  }

  public List<Long> getReviewers(Long blogPostId) {
    return getRelatedTo1(blogPostId);
  }

  public void updateReviewers(Long blogPostId, List<Long> personIds) {
    updateFor1(blogPostId, personIds);
  }

}
