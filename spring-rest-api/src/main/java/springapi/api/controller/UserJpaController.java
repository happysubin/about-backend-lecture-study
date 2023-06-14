package springapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springapi.api.domain.Post;
import springapi.api.domain.User;
import springapi.api.exception.UserNotFoundException;
import springapi.api.repository.PostRepository;
import springapi.api.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.sound.sampled.FloatControl;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

    private UserRepository userRepository;
    private PostRepository postRepository;

    @Autowired
    public UserJpaController(UserRepository userRepository,PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users/{id}/post")
    public List<Post> retrieveAllPstsByUser(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("해당 아이디"+ id+ " 를 가진 유저가 없습니다.");
        }

        return user.get().getPost();

    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Long id,@Validated @RequestBody Post post ) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException("유저가 없습니다");
        }
        post.setUser(user.get()); //연관관계의 주인
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }


    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException("해당 아이디"+ id+ " 를 가진 유저가 없습니다.");
        }
        

        EntityModel<User> model = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        model.add(linkTo.withRel("all-users"));

        return model;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Validated @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id){
        System.out.println("id = " + id);
        userRepository.deleteById(id);
    }

    @PostConstruct
    public void createDummyData(){
        userRepository.save(new User());
        userRepository.save(new User());
        userRepository.save(new User());
    }


}
