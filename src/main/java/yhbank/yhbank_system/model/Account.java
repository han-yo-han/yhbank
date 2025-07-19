package yhbank.yhbank_system.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
//원래 보통 한줄 띄고 시작? 나는 컨벤션하게 코드 작성하는 법도 배워야함. 필드시작할때 한줄띄기의 디테일정도의 컨벤션 알 필요 있음.
    @Id //db테이블에 pk에 해당하는 필드임을 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY) //my-sql에 auto-increment와 같은 기능
    private Long accountId;
//원래 보통 어노테이션달리면 필드값 사이  한줄띔? 컨벤셔널한가?
    @Column(unique = true, nullable = false) //my-sql에서 unique , notNull? 조건 제약과 같은 기능
    private String accountNo;


    private Long balance;

    private String bankName;

    @OneToOne
    @JoinColumn(name= "user_id", unique= true) //1대1맵핑을하고 user_id라는 User의 pk값으로 1대1맵핑 의존관계를 갖는다는건가? 즉, 의존하는 테이블이 의존을 주는 테이블에 대한 외래키를 설정하는건가? 좀더 컨벤셔널하게 표현해줘
    private User user;
}
