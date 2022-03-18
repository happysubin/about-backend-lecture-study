package springapi.api.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import springapi.api.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class UserDao {
    private final static List<User> users= new ArrayList<>();
    private  static Long userCount=3L;

    static{
        users.add(new User(1L,"user1",new Date()));
        users.add(new User(2L,"user2",new Date()));
        users.add(new User(3L,"user3",new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if(user.getId() == null){
            user.setId(++userCount);
            user.setJoinDate(new Date());
        }

        users.add(user);
        return user;

    }

    public User findOne(Long id){
       return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst().orElse(null);
    }
}
