package com.myhotels.authorizationserver.services;

import com.myhotels.authorizationserver.entities.UserDetail;
import com.myhotels.authorizationserver.repos.UserDetailRepo;
import com.myhotels.authorizationserver.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthenticationService implements UserDetailsService {
    @Autowired
    private UserDetailRepo repo;

    @Override
    public UserHelper loadUserByUsername(String username) throws UsernameNotFoundException, NumberFormatException {
        Optional<UserDetail> user = repo.findByPhoneNumber(Long.parseLong(username));
        if(user.isPresent()){
            return new UserHelper(user.get());
        } else {
            throw new UsernameNotFoundException("User not found with phone number"+username);
        }
    }

    public UserDetail create(UserDetail user) {
        return repo.save(user);
    }
}
