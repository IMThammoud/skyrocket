package com.skyrocket.services;

import com.skyrocket.model.UserAccount;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserAccountMethods {
    UserAccount userAccount;

    public UserAccount createUserAccount(UUID id,
                                  String userEmail,
                                         String userPw,
                                         Date creationDate){

        return userAccount.setId(id)
                .setUserEmail(userEmail)
                .setUserPw(userPw)
                .setCreationDate(creationDate)
                .build();
    }
}
