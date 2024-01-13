package org.io.GreenGame.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterForm {

    String username;
    String email;
    String password;
    String repeatedPassword;
}
