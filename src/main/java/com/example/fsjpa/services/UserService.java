package com.example.fsjpa.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.fsjpa.entity.User;
import com.example.fsjpa.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    public User getUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) return user.get();
        return null;
    }

}
