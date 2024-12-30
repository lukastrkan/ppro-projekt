package cz.trkan.pproprojekt.utils;

import cz.trkan.pproprojekt.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class JteUtils {
    public static MyUserDetails getCurrentUser(){
        var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof MyUserDetails) {
            return (MyUserDetails) user;
        }
        return null;
    }
}
