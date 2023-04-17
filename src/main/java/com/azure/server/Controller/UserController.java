package com.azure.server.Controller;

import com.azure.server.CustomExceptions.UserFoundException;
import com.azure.server.CustomExceptions.UserNotFoundException;
import com.azure.server.Helper.UserRequest;
import com.azure.server.Model.User;
import com.azure.server.ServicesImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceimpl;

    //register new user as Buyer
    @PostMapping("/registerbuyer")
    public ResponseEntity<?> saveUserAsBuyer(@RequestBody User user){
        return new ResponseEntity<>(userServiceimpl.saveUserAsBuyer(user), HttpStatus.CREATED);
    }

    //register new user as Seller
    @PostMapping("/registerseller")
    public ResponseEntity<?> saveUserAsSeller(@RequestBody User user){
        return new ResponseEntity<>(userServiceimpl.saveUserAsSeller(user), HttpStatus.CREATED);
    }

    //register new user by admin
    @PostMapping("registerbyadmin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> saveAppUserByAdmin(@RequestBody User appUser ){
        return ResponseEntity.ok().body(userServiceimpl.saveAppUserByAdmin(appUser));
    }


//    @GetMapping("/validate")
//    public ResponseEntity<?> validateToken(@RequestParam("token") String token){
//        return new ResponseEntity<>(authService.validateToken(token), HttpStatus.OK);
//    }



    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) throws UserFoundException {
        return new ResponseEntity<>(userServiceimpl.createUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userServiceimpl.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUser(@PathVariable String userName) {
        return new ResponseEntity<>(userServiceimpl.getUser(userName),HttpStatus.FOUND);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<User> updateUser(@PathVariable String userName, @RequestBody UserRequest userRequest) throws UserNotFoundException {
        return new ResponseEntity<>(userServiceimpl.updateUser(userName,userRequest),HttpStatus.OK);
    }

    @DeleteMapping("{userName}")
    public String deleteUser(@PathVariable String userName) throws UserNotFoundException {
        return userServiceimpl.deleteUser(userName);
    }
}
