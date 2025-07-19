package yhbank.yhbank_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YhbankSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(YhbankSystemApplication.class, args);
	}

}

//전체진행상황에대한 의문사항
//1-1, 1-2, 1-3. 1-4를 보면 Entity 생성하고 Repository 생성한다.
//즉, DB 세팅부터 해야한다는 건가?