package springapi.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springapi.api.dao.UserDao;
import springapi.api.domain.User;

import java.net.URI;
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
        return userDao.findOne(id);
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
}
