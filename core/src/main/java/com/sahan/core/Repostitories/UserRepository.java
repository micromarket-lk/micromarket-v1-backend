package com.sahan.core.Repostitories;

import com.sahan.core.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUserName(String username);
    User findTopUserByUserName(String username);
}