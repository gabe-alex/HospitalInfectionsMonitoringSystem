package com.badomega.hims.controllers;


import com.badomega.hims.dtos.PasswordChangeDTO;
import com.badomega.hims.entities.User;
import com.badomega.hims.services.UserService;
import com.badomega.hims.session.AuthedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class MiscController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/changepass", method = RequestMethod.GET)
    public String showPasswordChangeForm(PasswordChangeDTO passwordChangeDTO) {
        return "change_password";
    }

    @RequestMapping(value = "/changepass", method = RequestMethod.POST)
    public String changePassword(@Valid PasswordChangeDTO passwordChangeDTO, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            AuthedUser auth = (AuthedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = auth.getUser();

            user = userService.changeUserPassword(passwordChangeDTO, user);
            if (user == null) {
                bindingResult.rejectValue("old_password", "message.changePassError");
            }
        }

        if (bindingResult.hasErrors()) {
            return "change_password";
        }

        return "change_password_success";
    }
}
