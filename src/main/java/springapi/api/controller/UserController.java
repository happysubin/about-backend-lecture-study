package springapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springapi.api.dao.UserDao;
import springapi.api.domain.User;
import springapi.api.exception.UserNotFoundException;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

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
    public User retrieveUser(@PathVariable Long id){
        User user = userDao.findOne(id);
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity createsUser(@RequestBody User user){
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
