package com.resource.Oauthresourceserver.controller;

import com.resource.Oauthresourceserver.payload.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/api")
@RestController
public class UserController {
    @GetMapping("/users")
    public ResponseEntity<?> getUser() {
        UserResponse userResponse=new UserResponse("Test");
        System.out.println(userResponse);
        return ResponseEntity.ok().body(userResponse);
    }
    @GetMapping("/free")
    public String[] getArticles() {
        return new String[] { "Article 1", "Article 2", "Article 3" };
    }

}
