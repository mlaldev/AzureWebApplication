package com.azure.server.Services;

import com.azure.server.CustomExceptions.UserFoundException;
import com.azure.server.CustomExceptions.UserNotFoundException;
import com.azure.server.Helper.UserRequest;
import com.azure.server.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

     User createUser(UserRequest userRequest) throws UserFoundException;

     User getUser(String userName);

     User updateUser(String userName, UserRequest userRequest) throws UserNotFoundException;

     String deleteUser(String userName) throws UserNotFoundException;

     List<User> getAllUsers();

     public String saveUserAsBuyer(User user);

     public String generateToken(UserRequest user);

     public String validateToken(String token);

     public Object saveAppUserByAdmin(User appUser);

     public String saveUserAsSeller(User user);
     //List<User> getUserByRole(String role);

}
