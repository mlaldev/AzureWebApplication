package com.azure.server.Controller;

import com.azure.server.CustomExceptions.ApplicationBadCredentialsException;
import com.azure.server.CustomExceptions.ApplicationDisabledException;
import com.azure.server.Helper.JwtRequest;
import com.azure.server.Helper.JwtResponse;
import com.azure.server.Model.User;
import com.azure.server.ServicesImpl.UserDetailsServiceImpl;
import com.azure.server.securityConfig.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceimpl;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/generate-token")
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequest jwtRequest) {
        authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
        UserDetails userDetails = this.userDetailsServiceimpl.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    public void authenticate(String username,String password) {
       try{
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
       }catch (BadCredentialsException e){
           throw new ApplicationBadCredentialsException("Bad Credentials!!");
       }
    }

    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){
        return (User) userDetailsServiceimpl.loadUserByUsername(principal.getName());
    }
}
