package de.flaviait.crud.backoffice.repositories;

import de.flaviait.crud.backoffice.models.BlogPost;
import de.flaviait.crud.jooq.tables.records.BlogPostRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static de.flaviait.crud.jooq.tables.BlogPost.BLOG_POST;

@Component
public class BlogPostRepository extends AbstractCRUDRepository<BlogPostRecord, BlogPost> {

  private final ReviewersRepository reviewersRepository;

  @Autowired
  public BlogPostRepository(DSLContext dslContext, ReviewersRepository reviewersRepository) {
    super(dslContext, BLOG_POST, BlogPost.class);
    this.reviewersRepository = reviewersRepository;
  }

  @Override
  public List<BlogPost> getPage(Integer page, Integer pageSize, String sortField, String sortOrder) {
    return super.getPage(page, pageSize, sortField, sortOrder)
      .stream()
      .map(blogPost -> {
        blogPost.setReviewers(reviewersRepository.getReviewers(blogPost.getId()));
        return blogPost;
      })
      .collect(Collectors.toList());
  }

  @Override
  public BlogPost getById(Long id) {
    BlogPost blogPost = super.getById(id);
    blogPost.setReviewers(reviewersRepository.getReviewers(blogPost.getId()));
    return blogPost;
  }

  @Override
  public BlogPost create(BlogPost pojo) {
    BlogPost blogPost = super.create(pojo);
    pojo.getReviewers().stream().forEach(reviewerId -> {

    });
    return blogPost;
  }

  @Override
  public BlogPost update(Long id, BlogPost pojo) {
    BlogPost updated = super.update(id, pojo);
    reviewersRepository.updateReviewers(pojo.getId(), pojo.getReviewers());
    return updated;
  }

}
