package org.io.GreenGame.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Users")
public class GreenGameUser {

    @Id
    @GeneratedValue
    private Long id;

    private String Username;

    private String Email;

    private LocalDateTime CreationDate;

    private LocalDateTime ChangeDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();
    @Embedded
    private Security SecurityData;


    @Override
    public String toString() {
        return "GreenGameUser{" +
                "id=" + id +
                ", Username='" + Username + '\'' +
                ", Email='" + Email + '\'' +
                ", CreationDate=" + CreationDate +
                ", ChangeDate=" + ChangeDate +
                ", roles=" + roles +
                ", SecurityData=" + SecurityData +
                ", passwdhash=" + SecurityData.getPasswordHash()     +
                '}';
    }
}
