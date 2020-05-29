package ru.isu.CourseProject.domain.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ru.isu.CourseProject.domain.model.Category;
import ru.isu.CourseProject.domain.repository.CategoryRepository;

public class IdToCategory implements Converter<Integer, Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category convert( Integer id ) {
        return categoryRepository.getById( id );
    }
}
