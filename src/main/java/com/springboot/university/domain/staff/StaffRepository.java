package com.springboot.university.domain.staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByUserId(String userId);
    Optional<Staff> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Staff s SET s.password = :password WHERE s.email = :email")
    void updatePassword(@Param("email") String email, @Param("password") String password);
}