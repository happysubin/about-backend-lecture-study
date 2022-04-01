package springapi.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springapi.api.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

}
