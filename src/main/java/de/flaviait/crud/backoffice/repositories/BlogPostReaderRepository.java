package de.flaviait.crud.backoffice.repositories;

import de.flaviait.crud.jooq.tables.records.BlogPostReaderRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static de.flaviait.crud.jooq.tables.BlogPostReader.BLOG_POST_READER;

@Component
public class BlogPostReaderRepository extends AbstractManyToManyRelationRepository<BlogPostReaderRecord> {

  @Autowired
  public BlogPostReaderRepository(DSLContext dslContext) {
    super(dslContext, BLOG_POST_READER);
  }

  public List<Long> getReaders(Long blogPostId) {
    return getRelated(BLOG_POST_READER.BLOG_POST, BLOG_POST_READER.PERSON, blogPostId);
  }

  public void updateReaders(Long blogPostId, List<Long> personIds) {
    merge(BLOG_POST_READER.BLOG_POST, BLOG_POST_READER.PERSON, blogPostId, personIds);
  }

  public void deleteReaders(Long blogPostId) {
    deleteAll(BLOG_POST_READER.BLOG_POST, blogPostId);
  }

}
