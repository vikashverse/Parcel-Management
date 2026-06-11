package com.tcs.ilp.ParcelManagement.repository;

import com.tcs.ilp.ParcelManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String userId);
}