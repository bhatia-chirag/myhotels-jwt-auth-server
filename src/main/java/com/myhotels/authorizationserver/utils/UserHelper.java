package com.myhotels.authorizationserver.utils;

import com.myhotels.authorizationserver.entities.UserDetail;
import org.springframework.security.core.userdetails.User;

public class UserHelper extends User {

    public UserHelper(UserDetail user) {
        super(
                user.getPhoneNumber().toString(),
                user.getPassword(),
                user.getGrantedAuthorities()
        );
    }
}
