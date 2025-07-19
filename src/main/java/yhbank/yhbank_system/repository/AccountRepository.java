package yhbank.yhbank_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yhbank.yhbank_system.model.Account;
import yhbank.yhbank_system.model.User;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> { //JpaRepository라는 클래스를 상속받는 자식 인터페이스인건알겠는데 왜 <>가 붙은거지? 아 쿼리파라미터로 받아서 안에서 정의혹은선언할때쓰는거군
    Optional<Account> findByAccountNo(String accountNo); //Optional 클래스 타입에 대해 궁금함. 이게 null값에 대한 예외처리를 생략할수있도록해주는걸로알고있는데 좀더 알필요있음. 이론적 구체화 및 효과에 대한 정리 필요
    Optional<Account> findByUser(User user);

}
