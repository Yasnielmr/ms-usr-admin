package com.oriontek.ms_usr.controllers;

import com.oriontek.ms_usr.dtos.UserRequest;
import com.oriontek.ms_usr.services.contracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController.
 *
 * @author Yasniel Montano.
 * @version 1.0.0, 07-12-2025
 */
@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody final UserRequest request) {
        final var user = this.userService.createUser(request);
        return ResponseEntity.status(201).body(user);
    }
}
