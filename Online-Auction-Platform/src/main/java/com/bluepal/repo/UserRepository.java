package com.bluepal.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluepal.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

}
