package com.resource.Oauthresourceserver.controller;

import com.resource.Oauthresourceserver.payload.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/users")
    public ResponseEntity<?> getUser() {
        UserResponse userResponse=new UserResponse("Test");
        System.out.println(userResponse);
        return ResponseEntity.ok().body(userResponse);
    }
    @GetMapping("/api/free")
    public String[] getArticles() {
        return new String[] { "Article 1", "Article 2", "Article 3" };
    }

}
