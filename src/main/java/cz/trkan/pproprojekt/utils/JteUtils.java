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

    public static boolean isEditor(){
        var user = getCurrentUser();
        if (user != null) {
            return user.getRole().equals("EDITOR") || user.getRole().equals("ADMIN");
        }
        return false;
    }
}
