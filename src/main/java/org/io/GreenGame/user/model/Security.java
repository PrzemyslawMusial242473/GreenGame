package org.io.GreenGame.user.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Security {

    private LocalDateTime SecurityChangeDate;

    private String PasswordHash;

    private String TOTPSecret;
}
