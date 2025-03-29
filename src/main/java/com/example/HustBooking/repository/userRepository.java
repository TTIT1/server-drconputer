package com.example.HustBooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HustBooking.model.userModel;

import jakarta.transaction.Transactional;

@Repository
public interface userRepository extends JpaRepository<userModel,Long> {
    // Basic JPA Methods
    Optional<userModel> findByUsername(String username);
    boolean existsByUsername(String username);// giúp ta kiểm tra nhanh username đã tồn tại trong database hay chưa
    
    
    // Custom queries using @Query
    @Query(value = "SELECT * FROM user WHERE password = :password", nativeQuery = true)
    List<userModel> findUserByPassword(@Param("password") String password);
    
    @Query("SELECT u FROM userModel u WHERE u.username LIKE %:keyword% OR u.password LIKE %:keyword%")
    List<userModel> searchUsers(@Param("keyword") String keyword);
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user (username, password) VALUES (:username, :password)", nativeQuery = true)
    void insertUser(@Param("username") String username, @Param("password") String password);
   
    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET username = :username, password = :password WHERE id = :id", nativeQuery = true)
    int updateUser(@Param("id") Long id, @Param("username") String username, @Param("password") String password);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM userModel u WHERE u.id = :id")
    int deleteUserById(@Param("id") Long id);
}