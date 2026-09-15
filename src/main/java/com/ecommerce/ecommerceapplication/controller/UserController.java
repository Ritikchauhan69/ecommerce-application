// package com.ecommerce.ecommerceapplication.controller;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.ecommerce.ecommerceapplication.dto.RegisterRequest;
// import com.ecommerce.ecommerceapplication.model.User;
// import com.ecommerce.ecommerceapplication.service.UserService;
// import com.ecommerce.ecommerceapplication.entity.User;

// @RestController
// @RequestMapping("/api/users")
// public class UserController {

//     private final UserService userService;

//     public UserController(UserService userService) {
//         this.userService = userService;
//     }

//     @PostMapping("/register")
//     public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
//         User user = userService.registerUser(
//             request.getName(),
//             request.getEmail(),
//             request.getPassword()
//         );

//         return ResponseEntity.status(HttpStatus.CREATED).body(user);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<User> getUserById(@PathVariable Long id) {
//         User user = userService.getUserById(id);
//         return ResponseEntity.ok(user);
//     }
// }