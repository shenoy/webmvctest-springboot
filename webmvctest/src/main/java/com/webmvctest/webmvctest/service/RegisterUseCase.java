package com.webmvctest.webmvctest.service;

import com.webmvctest.webmvctest.entity.User;
import com.webmvctest.webmvctest.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@Setter
@Service
public class RegisterUseCase {

    @Autowired
    UserRepository userRepository;

    public Long registerUser(User user, boolean sendWelcomeMail) {
      User u = userRepository.save(user);
      return u.getId();
    }
}
