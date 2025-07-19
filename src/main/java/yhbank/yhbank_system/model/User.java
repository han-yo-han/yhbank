package yhbank.yhbank_system.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity  //엔티티 객체 해당? model 패키지 파서 보관? domain이랑 같은 의미?(너는 domain패키지라고했음. 나는 model)
@Getter //롬복 라이브러리. Getter 자동 생성?
@Setter //롬복 라이브러리. Setter 자동 생성?
@NoArgsConstructor //롬복라이브러리 기본 생성자 자동 생성?
@AllArgsConstructor //롬복라이브러리 모든 필드 파라미터 생성자 자동생성?
@Table(name = "users") // 'user'는 예약어라 'users'로 바꿈 //이러면됌?
public class User {
    @Id // db에서 pk값이 되도록 세팅? 원래는 db설정으로알고있음. 근데 was 환경(?)에서 세팅하게끔 해주는 역할? 오직 jpa 환경 내에서 설정하는건가?
    @GeneratedValue(strategy = GenerationType.IDENTITY) //db에 값 입력안하고 자동생성. 순번 ++하도록? 이것도 원래는 db auto-increment인데 이걸 하도록 1대1 기능 맵핑된건가?
    private Long userId;

    @Column(unique = true, nullable = false)  //이것도 db특정컬럼에대한세팅으로보임. 중복x not null o 이걸 was환경(적절한표현?)에서 동작하도록 진행. 이게 가능하도록 하는 핵심 1대1맵핑 이론원리는 ORM인가? 간략하게라도 이게 어떻게가능한지 아키텍쳐적으로 이해하고싶다.
    private String username;

    private String fullName;

    private String encodedPassword; // 인코딩된(암호화?) 문자가 저장된다.? 애초에 프론트에서 form제출받을때부터 encoded되어있다는거군. 처음부터 암호화라는거지?

    @Column(nullable = false)
    private String role; // ex: "ROLE_USER" or "ROLE_ADMIN"


    @OneToOne(mappedBy = "user")    //이것도 erd에서 1대1 종속관계 의존관계 말하는거같은데 맞나? 이론적 구체화. 1대다 다대다 이건가? 좀더 면접대비용으로 이론적 정리가 필요하다. 어쨌든 db관련설정을 entity에서 즉, 테이블에서 db 다루듯 다루네. 굉장히 직관적이다. 뭔가 JPA ORM 정신같아. JDBC는 순수하게 쿼리로 이동작을 기능하게했던걸로알고있는데. 좀더 이부분에 대한 면접대비용의 이론적 구체화가 필요하다.
    private Account account;


}
