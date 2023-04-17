package com.azure.server.Repository;

import com.azure.server.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String userName);

    String deleteByUsername(String userName);
}
