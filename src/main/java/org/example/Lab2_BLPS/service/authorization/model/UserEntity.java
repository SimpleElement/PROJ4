package org.example.Lab2_BLPS.service.authorization.model;

import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.service.authorization.enm.RoleOfUser;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "user_ref")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_of_user")
    private RoleOfUser roleOfUser;
}
