package com.badomega.hims.session;

import com.badomega.hims.entities.User;
import com.badomega.hims.enums.Role;
import org.springframework.security.core.authority.AuthorityUtils;

public class AuthedUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public AuthedUser(User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getPerson().getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return user.getPerson().getRole();
    }
}