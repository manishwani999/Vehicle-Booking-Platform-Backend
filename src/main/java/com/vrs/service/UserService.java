package com.vrs.service;

import com.vrs.model.User;

public interface UserService {

    User registerUser(User user);

    User registerAdmin(User user);

    User getByEmail(String email);

    User getUserById(Long id);   // ✅ ADD
}
