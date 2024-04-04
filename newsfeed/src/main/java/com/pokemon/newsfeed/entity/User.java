package com.pokemon.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNum;
    @Column(nullable = false, unique = true, length = 50)
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) // 이넘 타입을 데이터베이스에 저장할 때 사용
    private UserRoleEnum role;

    public User(String userId, String password, String email, String name, UserRoleEnum role) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {  //user 객체의 num 과 userid 속성이 같은 경우에만 두 객체를 동등하다고 판단하는 로직
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getUserNum(), user.getUserNum()) && Objects.equals(getUserId(), user.getUserId());
    }

    public void updateProfile(String name, String userId, String email, String password) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.password = password;

    }
}
