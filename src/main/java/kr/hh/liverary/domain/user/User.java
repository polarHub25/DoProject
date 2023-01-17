package kr.hh.liverary.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자 만들어줌
@DynamicUpdate
@Entity
@Getter
@Table(name="tb_User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(name = "userName"  , nullable = false)
    private String name;

    @Column(name = "email"  , nullable = false)
    private String email;

    @Column(name = "provider"  , nullable = false)
    private String provider;

    @Column(name = "userNick"  , nullable = true , unique = true)
    private String nickName;

    @Builder
    public User(Long id, String name, String email, String provider, String nickName){
        this.id = id;
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.nickName = nickName;
    }

    public User update(String name, String email){
        this.name = name;
        this.email = email;

        return this;
    }

}
