package yhbank.yhbank_system;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import yhbank.yhbank_system.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    @Getter
    private final User user;  //커스텀유저디테일은 커스텀클래스 유저에대해 생성자 만들었음

    @JsonCreator
    public CustomUserDetails(@JsonProperty("user") User user) {
        this.user = user;
    }
    /*
    public User getUser(){ //이건 왜 lombok안쓰지?
        return this.user;
    }
    */


/*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){ //컬렉션으로 받는 이유는 부모메서드 반환타입이라서? <>도 마찬가지고?
        return Collections.singletonList(() -> "USER"); //singletonList의 의미는 간단하게어떤기능? 목적은?
    } 이건 인가와 관련없는건가?
*/

    @Override
    public String getPassword(){
        return user.getEncodedPassword();

    }

    @Override
    public String getUsername(){ //Username이 컨벤셔널한가 UserName이 컨벤셔널한가?
        return user.getUsername();
    }

    @Override  //지금 보면 그냥 특별한 재정의가 없는데 왜 이런식으로 만든거지? 아니면 부모에 없는 메서드를 만드는게 override인가?
    public boolean isAccountNonExpired(){ return true;}

    @Override
    public boolean isAccountNonLocked(){ return true;}

    @Override
    public boolean isCredentialsNonExpired(){ return true;}

    @Override
    public boolean isEnabled(){ return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

}
