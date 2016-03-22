package de.flaviait.crud.backoffice.controllers;

import de.flaviait.crud.backoffice.models.BlogPost;
import de.flaviait.crud.backoffice.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crud/blogPosts")
public class BlogPostController extends AbstractBackofficeController<BlogPost> {

  @Autowired
  public BlogPostController(BlogPostRepository blogPostRepository) {
    super(blogPostRepository);
  }

  @Override
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public List<BlogPost> page(@RequestParam(value = "_page", defaultValue = "1") Integer page,
                           @RequestParam(value = "_perPage", defaultValue = "30") Integer pageSize,
                           @RequestParam(value = "_sortDir", defaultValue = "DESC") String sortOrder,
                           @RequestParam(value = "_sortField", defaultValue = "id") String sortField) {
    return super.page(page, pageSize, sortOrder, sortField);
  }

  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public BlogPost get(@PathVariable("id") Long id) {
    return super.get(id);
  }

  @Override
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public BlogPost create(@RequestBody BlogPost blogPost) {
    return super.create(blogPost);
  }

  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public BlogPost update(@PathVariable Long id, @RequestBody BlogPost blogPost) {
    return super.update(id, blogPost);
  }

  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    return super.delete(id);
  }

}
