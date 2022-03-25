package springapi.api.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import springapi.api.domain.User;

import java.util.*;


@Service
public class UserDao {
    private final static List<User> users= new ArrayList<>();
    private  static Long userCount=3L;

    static{
        users.add(new User(1L,"user1",new Date(),"pass1","123456-123456"));
        users.add(new User(2L,"user2",new Date(),"pass2","654321-654321"));
        users.add(new User(3L,"user3",new Date(),"pass3","321654-321654"));
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

    public User deleteById(Long id){
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()){
            User user = iterator.next();

            if(user.getId() == id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }

    public User edit(Long id,User user){
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()){
            User findUser = iterator.next();
            if(findUser.getId() == id){
                findUser.setName(user.getName());
                return findUser;
            }
        }
        return null;
    }

}
