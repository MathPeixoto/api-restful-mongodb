package br.com.matheuspeixoto.apimongodb.web.resources;

import br.com.matheuspeixoto.apimongodb.service.PostService;
import br.com.matheuspeixoto.apimongodb.web.domain.Post;
import br.com.matheuspeixoto.apimongodb.web.resources.util.Url;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostResources {
  private final PostService postService;

  public PostResources(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> findById(@PathVariable String id) {
    Post post = postService.findById(id);
    return new ResponseEntity<>(post, HttpStatus.OK);
  }

  @GetMapping("/titlesearch")
  public ResponseEntity<List<Post>> findByTitle(
      @RequestParam(value = "text", defaultValue = "") String text) {
    String textDecoded = Url.decodeParam(text);
    List<Post> posts = postService.searchTitle(textDecoded);
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @GetMapping("/fullsearch")
  public ResponseEntity<List<Post>> fullSearch(
      @RequestParam(value = "text", defaultValue = "") String text,
      @RequestParam(value = "minDate", defaultValue = "") String minDate,
      @RequestParam(value = "maxDate", defaultValue = "") String maxDate) {
    String textDecoded = Url.decodeParam(text);
    Date min = Url.convertDate(minDate, new Date(0L));
    Date max = Url.convertDate(maxDate, new Date());
    List<Post> posts = postService.fullSearch(textDecoded, min, max);
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }
}
