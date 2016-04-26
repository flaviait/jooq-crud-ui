package de.flaviait.crud.backoffice.controllers;

import de.flaviait.crud.backoffice.models.BlogPostDTO;
import de.flaviait.crud.backoffice.repositories.BlogPostReaderRepository;
import de.flaviait.crud.backoffice.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crud/blogPosts")
public class BlogPostController extends AbstractCRUDBackofficeController<Long, BlogPostDTO> {

  private final BlogPostReaderRepository blogPostReaderRepository;

  @Autowired
  public BlogPostController(BlogPostRepository blogPostRepository, BlogPostReaderRepository blogPostReaderRepository) {
    super(blogPostRepository);
    this.blogPostReaderRepository = blogPostReaderRepository;
  }

  @Override
  public List<BlogPostDTO> page(@RequestParam(value = "_page", defaultValue = "1") Integer page,
                                @RequestParam(value = "_perPage", defaultValue = "30") Integer pageSize,
                                @RequestParam(value = "_sortDir", defaultValue = "DESC") String sortOrder,
                                @RequestParam(value = "_sortField", defaultValue = "id") String sortField,
                                @RequestParam(value = "_filters", required = false) String query) {
    List<BlogPostDTO> dtos = super.page(page, pageSize, sortOrder, sortField, query);
    dtos.forEach(this::populateReaders);
    return dtos;
  }

  @Override
  public BlogPostDTO get(@PathVariable("id") Long id) {
    return populateReaders(super.get(id));
  }

  @Override
  public ResponseEntity<Void> create(@RequestBody BlogPostDTO dto, @RequestHeader String host) {
    BlogPostDTO persisted = repository.create(dto);
    blogPostReaderRepository.updateReaders(persisted.getId(), dto.getReaders());
    return buildCreateResponse(host, persisted);
  }

  @Override
  public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody BlogPostDTO dto) {
    repository.update(id, dto);
    blogPostReaderRepository.updateReaders(id, dto.getReaders());
    return buildUpdateResponse();
  }

  @Override
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    repository.delete(id);
    blogPostReaderRepository.deleteReaders(id);
    return buildDeleteResponse();
  }

  private BlogPostDTO populateReaders(BlogPostDTO dto) {
    dto.setReaders(blogPostReaderRepository.getReaders(dto.getId()));
    return dto;
  }
}
