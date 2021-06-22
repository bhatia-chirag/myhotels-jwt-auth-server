package com.myhotels.authorizationserver.repos;

import com.myhotels.authorizationserver.entities.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepo extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByPhoneNumber(Long phoneNumber);
}
