package com.myhotels.authorizationserver.controllers;

import com.myhotels.authorizationserver.dtos.UserDto;
import com.myhotels.authorizationserver.entities.UserDetail;
import com.myhotels.authorizationserver.services.UserAuthenticationService;
import com.myhotels.authorizationserver.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationTokenController {
    private Logger logger = LoggerFactory.getLogger(AuthenticationTokenController.class);

    @Autowired
    private UserAuthenticationService service;

    @GetMapping("/token")
    public ResponseEntity getJwtToken() {
        logger.info("token fetched");

        HttpHeaders headers = new HttpHeaders();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> jwtPayload = new HashMap<>();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            jwtPayload.put("user_name",userDetails.getUsername());
            jwtPayload.put("authorities",userDetails.getAuthorities().toString());
        } else {
            // TODO: throw exception
        }
        headers.add("Authorization", "Bearer "+ JwtUtil.createJwt(jwtPayload, 60000L));
        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/user")
    public ResponseEntity<String> signUp(@RequestBody UserDto user) {
        UserDetail userEntity = new UserDetail();
        userEntity.setPassword(user.getPassword());
        userEntity.setPhoneNumber(user.getUserName());
        GrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_USER");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(userAuthority);
        userEntity.setGrantedAuthorities(authorities);
        service.create(userEntity);
        return ResponseEntity.created(URI.create("/auth/login")).build();
    }
}
