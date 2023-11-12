package org.io.GreenGame.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String Username;

    private String Email;

    private LocalDateTime CreationDate;

    private LocalDateTime ChangeDate;

    @Embedded
    private Security SecurityData;
}
