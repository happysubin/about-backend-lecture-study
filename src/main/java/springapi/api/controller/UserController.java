package springapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springapi.api.dao.UserDao;
import springapi.api.domain.User;
import springapi.api.exception.UserNotFoundException;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {
    private UserDao userDao;

    @Autowired
    public UserController(UserDao userDao){
        this.userDao=userDao;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userDao.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable Long id){
        User user = userDao.findOne(id);
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        //HATEOAS
        EntityModel<User> model = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        model.add(linkTo.withRel("all-users"));
        return model;
    }
    /** 이렇게 추가됨. du
     * {
     *     "id": 1,
     *     "name": "user1",
     *     "joinDate": 1648639749095,
     *     "password": "pass1",
     *     "ssn": "123456-123456",
     *     "links": [
     *         {
     *             "rel": "all-users",
     *             "href": "http://localhost:8088/users"
     *         }
     *     ]
     * }
     */

    // 전체 사용자 목록
    @GetMapping("/users2")
    public ResponseEntity<CollectionModel<EntityModel<User>>> retrieveUserList2() {
        List<EntityModel<User>> result = new ArrayList<>();
        List<User> users = userDao.findAll();

        for (User user : users) {
            EntityModel entityModel = EntityModel.of(user);
            entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers()).withSelfRel());

            result.add(entityModel);
        }

        return ResponseEntity.ok(CollectionModel.of(result, linkTo(methodOn(this.getClass()).retrieveAllUsers()).withSelfRel()));
    }



    @PostMapping("/users")
    public ResponseEntity createsUser(@Validated  @RequestBody User user){
        User savedUser= userDao.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build(); //이러면 200코드가 아닌 201코드가 돌아온다.
    }

    @DeleteMapping("/users/{id}")
    public User deleteById(@PathVariable Long id){
       User findUser = userDao.deleteById(id);
        if(findUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
        return findUser;
    }

    @PutMapping("/users/{id}")
    public User editUser(@PathVariable Long id, @RequestBody User user){
        User findUser = userDao.findOne(id);
        if(findUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        User editUser = userDao.edit(id, user);

        return editUser;
    }
}
