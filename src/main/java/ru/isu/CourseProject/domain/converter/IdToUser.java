package ru.isu.CourseProject.domain.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ru.isu.CourseProject.domain.model.User;
import ru.isu.CourseProject.domain.repository.UserRepository;

public class IdToUser implements Converter<Integer, User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User convert( Integer id ) {
        System.out.println( userRepository.searchById(id) );
        return userRepository.searchById( id );
    }
}
