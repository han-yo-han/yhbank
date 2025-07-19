package yhbank.yhbank_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yhbank.yhbank_system.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  { //Interface는 메서드 API만 모아두는 역할을 하는건가?

    Optional<User> findByUsername(String username); //userName보다 username이 좀더 컨벤셔널한가?
}
