package com.auction.application.service;

import com.auction.application.port.PasswordHasher;
import java.time.Clock;

public class AuthService {
    public AuthService(Object userRepository, PasswordHasher passwordHasher, Clock clock) {}
}