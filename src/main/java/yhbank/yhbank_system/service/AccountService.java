package yhbank.yhbank_system.service;

import lombok.RequiredArgsConstructor;
import yhbank.yhbank_system.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yhbank.yhbank_system.model.Account;
import yhbank.yhbank_system.repository.AccountRepository;
import yhbank.yhbank_system.repository.UserRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor  //이건 필수파라미터가 있는 생성자 생성의 자동화 어노테이션?
public class AccountService {

    private final AccountRepository accountRepository; // final이기에 첫생성시 고정? 이게 의존성 주입? 구현체를 외부 파라미터로 받아서 완성함으로써 외부파라미터에게 맡긴다?
    private final UserRepository userRepository; //마찬가지로 의존성 주입? + 원래는 객체를 생성해서 주입해줘야하는데 그걸 편하게 한게 bean 관리? 그걸 컨테이너에 한번만생성해서 관리하는 싱글톤패턴? 즉, 의존성주입 + 빈생성및주입자동화 + 싱글톤패턴이라는 개념. 컨벤셔널하게 개념을 명료화해줘.
    private final PasswordEncoder passwordEncoder; // @Bean 등록 필요. 즉, 빈으로 등록이되어있어야 외부에서 파라미터 넣어줄 인스턴스가존재한거니깐? 클래스만존재하는건의미가없음?파라미터로넣으려면인스턴스를넣어줘야함?결국생성자메서드써야함?근데그걸빈이자동으로클래스에대한1개의인스턴스만들어주고파라미터넣어야할때그것도자동화해준다?맞나? 컨벤셔널하게 개념명료화해줘
//PassWordEncoder은 어느 파트에서 처리하는거지? was?(컨벤셔널하게 자바코드라고표현하나?) db?(컨벤셔널하게 db라고하는게맞나?) 이건 커스터마이징 빈 등록을 말하는건가?

    public Account createAccount(String username, String fullName, String accountNo, String bankName){

        // 1. 사용자 이름으로 사용자 조회
        User user = userRepository.findByUsername(username).orElseThrow(() ->new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        // 예외처리에 대한 이해가 필요한 대목. 예외 즉, 예외를 발생시키고 상위 클래스에 전가하는게 throw이고, 해당에러에대해 처리해주는 클래스가 catch로 처리하는거지?
        // throw와 catch로 크게 구분할 수있고, 김영한 자료에 의하면 예외처리가필요한건 예외 즉, 오류상황이 발생했을 때에도 정상 플로우로 동작할수잇도록하기위함.이걸 처리하기위해 존재. 체크예외(컴파일exception)과 언체크예외(RuntimeException)이 있음. Exception도 클래스임. Exception 상속받으면 체크예외고, RuntimeException은 하위자식이고 얘는 예외적으로 언체크예외다. 체크예외와 언체크예외의 throw 필수선언여부가다르다. 최상위 예외는 Throwable이고 Exception과 error이 있다. 메모리부족 등으로 발생하는 시스템 오류는 error이다. 어플리케이션 로직에서 사용할수있는 실질적인 최상위 예외는 Exception이다. 예외발생은 컴파일러가 체크한다. 자식클래스중 RunTimeException은 예외이다. 체크예외는 명시적으로 런타임 전에 확인해주고 처리해줘야한다. 예외는 폭탄돌리기와 같다. 처리할 수없으면 돌려야한다. 호출한 클래스에 . throw로 던진다. catch로 잡거나 throw로 던질 때 자식껏들까지 한꺼번에 처리한다. 체크예외에대한대비를 잘하는게 입문자에게있어서 필요한 역량이다. 언체크는 런타임에러를 어떻게 잡을지는 상황을ㄷ ㅓ 봐야할거같다. 모호하다. 일단 언체크는 보류
        // 대표적인 체크예외들이 만들어져있을것이다. 자바에는 아마 내장되어있을것이다.
        // IllegalArgumentException은 argument 형식 오류이다. 발생하면 호출한 클래스에 예외를 넘긴다.

        // 2. 1인 계좌 확인
        if(accountRepository.findByUser(user).isPresent()){
            throw new IllegalStateException("이미 계좌가 존재합니다");
        }

        //3. 계좌번호 중복 조회
        if(accountRepository.findByAccountNo(accountNo).isPresent()){//isPresent는 내장된함수인가?
            throw new IllegalArgumentException("이미 존재하는 계좌번호입니다");
        }


    /*
        //5. 유저의 비밀번호 암호화 및 저장
        String encodedPassword= passwordEncoder.encode(rawPassword);
        user.setEncodedPassword(encodedPassword);
        user.setFullName(fullName);*/

        userRepository.save(user); //패스워드 변경시점 반영. 인코딩(암호화) 후 저장 한다는걸 의미?

        //6. 계좌 생성
        Account account = new Account();
        account.setAccountNo(accountNo);
        account.setBalance(0L);
        account.setBankName(bankName);
        account.setUser(user);

        return accountRepository.save(account);

    }

    public Optional<Account> getAccountByUser(User user) {
        return accountRepository.findByUser(user);
    }




    private boolean isValidPassword(String password){
        return password !=null
                && password.matches("^(?=.*[a-z])(?=.*\\d)[a-z\\d]{11,}$"); //정규화식을 통한 검증맞나? 특수문자 뭐 이런거 검증하는거같은데 이렇게설정한 이유는? 비밀번호 컨벤셔널한 정규화식과 그 이유 제시해줘

    }
}

