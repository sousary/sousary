package com.auth.oauthserver.repository;


import com.auth.oauthserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    public User findUserByUsernameAndAppId(String username, String AppId);
}
