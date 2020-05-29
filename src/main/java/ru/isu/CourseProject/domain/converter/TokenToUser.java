package ru.isu.CourseProject.domain.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ru.isu.CourseProject.domain.model.User;
import ru.isu.CourseProject.domain.repository.UserRepository;

public class TokenToUser implements Converter<String, User>{

    @Autowired
    UserRepository userRepository;

    @Override
    public User convert( String token ) {
        return userRepository.getUserByToken( token );
    }
}
