package com.azure.server.ServicesImpl;

import com.azure.server.CustomExceptions.ApplicationBadCredentialsException;
import com.azure.server.Model.User;
import com.azure.server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws ApplicationBadCredentialsException {
        User user=userRepository.findByUsername(username);
        if(user==null){
            System.out.println("Invalid Credentials!!");
            throw  new ApplicationBadCredentialsException("Invalid Credentials!!");
        }
        return user;
    }
}
