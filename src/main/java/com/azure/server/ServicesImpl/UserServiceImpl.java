package com.azure.server.ServicesImpl;
import com.azure.server.CustomExceptions.UserFoundException;
import com.azure.server.CustomExceptions.UserNotFoundException;
import com.azure.server.Helper.Roles;
import com.azure.server.Helper.UserRequest;
import com.azure.server.Helper.UserResponse;
import com.azure.server.Model.Role;
import com.azure.server.Model.User;
import com.azure.server.Repository.UserRepository;
import com.azure.server.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User createUser(UserRequest userRequest) throws UserFoundException {
        User existingUser = this.userRepository.findByUsername(userRequest.getUsername());
        if (existingUser != null) {
            System.out.println("User Already exist in Database.");
            throw new UserFoundException("Username already exist.");
        } else {
            User user = new User();
            //user.setProfile(userRequest.getProfile());
            user.setPhone(userRequest.getPhone());
            user.setLastName(userRequest.getLastName());
            user.setFirstName(userRequest.getFirstName());
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
            user.setEmail(userRequest.getEmail());
            user.setUsername(userRequest.getUsername());
            user.setOccupation(userRequest.getOccupation());
            Role role = new Role(userRequest.getRole());
            user.setRole(role);
            return userRepository.save(user);
        }
    }

    @Override
    public User getUser(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public User updateUser(String userName, UserRequest userRequest) throws UserNotFoundException {
        User existingUser = userRepository.findByUsername(userName);
        if (existingUser == null) {
            System.out.println("User does not exist with this username.");
            throw new UserNotFoundException("User does not exist with username:" + userRequest.getUsername());
        } else {
            //existingUser.setProfile(userRequest.getProfile());
            existingUser.setPhone(userRequest.getPhone());
            existingUser.setLastName(userRequest.getLastName());
            existingUser.setFirstName(userRequest.getFirstName());
            existingUser.setPassword(userRequest.getPassword());
            existingUser.setEmail(userRequest.getEmail());
            existingUser.setUsername(userRequest.getUsername());

            Role role = new Role(userRequest.getRole());
            existingUser.setRole(role);
            return userRepository.save(existingUser);
        }
    }

    @Override
    public String deleteUser(String userName) throws UserNotFoundException {
        User existingUser = userRepository.findByUsername(userName);
        if (existingUser == null) {
            System.out.println("User does not exist with this username.");
            throw new UserNotFoundException("User does not exist with username:" + userName);
        } else {
            return userRepository.deleteByUsername(userName);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String saveUserAsBuyer(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            //log.info(user.getUsername()+ " already Exist");

            throw new RuntimeException(user.getUsername() + " already Exist");
        }
        Role role = new Role(Roles.BUYER.value());
        user.setRole(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User saved successfully!";
    }

    @Override
    public String generateToken(UserRequest user) {
        return null;
    }

    @Override
    public String validateToken(String token) {
        return null;
    }

    @Override
    public Object saveAppUserByAdmin(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            //log.info(user.getUsername()+ " already Exist");

            throw new RuntimeException(user.getUsername() + " already Exist");
        }

        if(!Roles.isValidRole(user.getRole())) {
            throw new RuntimeException(user.getRole() + "is not a valid role");
        }
        Role role = new Role(Roles.ADMIN.value());
        user.setRole(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        UserResponse appUserResponse = new UserResponse(
                user.getUsername(), user.getPassword(), user.getFirstName(),user.getLastName(),
                user.getEmail(),user.getPhone(),user.getOccupation(),user.getRole().getRole());
        return appUserResponse;
    }

    @Override
    public String saveUserAsSeller(User user) {
        return null;
    }
}
//    @Override
//    public List<User> getUserByRole(String role) {
//
//    }

