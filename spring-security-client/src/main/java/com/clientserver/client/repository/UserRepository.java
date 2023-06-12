package com.clientserver.client.repository;
import com.clientserver.client.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    public User findUserByUsernameAndAppId(String username, String AppId);
}
