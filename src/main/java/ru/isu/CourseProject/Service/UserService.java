package ru.isu.CourseProject.Service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import ru.isu.CourseProject.model.User;

import java.util.ArrayList;
import java.util.List;

@Service( "userService" )
public class UserService {

    private List<User> users;

    public UserService(){
    }

    public List<User> findAll(){
        return this.users;
    }

    public User findOne( Integer userId ){
        return null;
    }

    public void save( User user ){
    }
}
