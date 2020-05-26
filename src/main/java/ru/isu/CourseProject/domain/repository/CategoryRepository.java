package ru.isu.CourseProject.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.CourseProject.domain.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query( "from Category" )
    List<Category> getAll();

    @Query( "select c from Category as c " +
            "where c.name like :name" )
    Category getByName( @Param( "name" ) String name );

    @Query( "select c from Category as c " +
            "where c.id = :id" )
    Category getById( @Param( "id" ) Integer id );

    @Query( "select c.name from Category as c " )
    List<String> getAllNames();
}
