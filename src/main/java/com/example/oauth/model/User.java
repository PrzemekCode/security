package com.example.oauth.model;

import com.example.oauth.user.UserDTO;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"User\"")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    private String password;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "user")
    private Set<FingerPrint> fingerPrintSet = new HashSet<>();

    public void addFingerPrint(FingerPrint fingerPrint) {
        getFingerPrintSet().add(fingerPrint);
    }

    public Set<FingerPrint> getFingerPrintSet() {
        if(isNull(fingerPrintSet)) {
            fingerPrintSet = new HashSet<>();
        }
        return fingerPrintSet;
    }

    public static User map(UserDTO userDTO) {
        return User
                .builder()
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .build();
    }
}
