package org.io.GreenGame.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ModelAttribute;

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
