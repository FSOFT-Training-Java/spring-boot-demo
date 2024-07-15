package com.example.fsjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fsjpa.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
}
