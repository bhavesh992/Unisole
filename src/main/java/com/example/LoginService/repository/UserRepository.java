package com.example.LoginService.repository;

import com.example.LoginService.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByEmail(String email);
    @Modifying
    @Query("update User u set u.token =:token where u.email=:email")
    @Transactional
    void updateTokenByEmail(@Param("email") String email, @Param("token") String token);
    @Query("select token from User u where u.email=:email")
    @Transactional
    String getTokenByEmail(@Param("email") String email);
    @Query("select email from User u where u.token=:token")
    @Transactional
    String getEmailByToken(@Param("token") String token);

    @Transactional
    @Modifying
    @Query(value="update users set user_name=:username where email=:email",nativeQuery = true)
    void updateProfile(@Param("email")String email,@Param("username")String username);
}
