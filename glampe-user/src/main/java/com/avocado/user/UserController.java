package com.avocado.user;

import com.avocado.config.ApiResponse;
import com.avocado.user.dto.req.UserVerifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
        System.out.println(userService.getUserById(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Fetch User Successfully")
                                .data(userService.getUserById(id))
                                .build()
                );
    }

    @PostMapping("/batch")
    public ResponseEntity<?> getUsersByIds(@RequestBody List<Long> ids){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Fetch Users Successfully")
                                .data(userService.getUsersByIds(ids))
                                .build()
                );
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody UserVerifyRequest userVerifyRequest){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Verify Successfully")
                        .data(userService.verifyUser(userVerifyRequest))
                        .build()
        );
    }

}
