package com.bikkadit.electronicstore.repository;

import com.bikkadit.electronicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String>
{
         Optional<User> findByEmail(String email);
         //when we use optional then we can check if it available or not

         Optional<User> findByEmailAndPassword(String email,String password);

         List<User> findByNameContaining(String keywords);
}
