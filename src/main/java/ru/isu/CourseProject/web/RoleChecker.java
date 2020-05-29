package ru.isu.CourseProject.web;

import ru.isu.CourseProject.domain.model.User;

public class RoleChecker {
    public static String check( User user ){
        if( user == null ) return "{ \"status\" : \"error\" }";
        if( ! user.hasAnyRole("ADMIN,CUSTOMER,EXECUTOR") ) return "{ \"status\" : \"error\" }";
        return "";
    }
}
