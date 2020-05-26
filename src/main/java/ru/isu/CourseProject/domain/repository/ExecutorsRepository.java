package ru.isu.CourseProject.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.CourseProject.domain.model.Executors;
import ru.isu.CourseProject.domain.model.User;

import java.util.List;

@Repository
public interface ExecutorsRepository extends JpaRepository<Executors, Integer> {

    @Query( "select e.executors from Executors as e " +
            "where e.order.id = :id" )
    List<User> getAllExecutorsByOrderId( @Param( "id" ) Integer id );

    @Query( "select e from Executors as e " +
            "where e.id = :id" )
    Executors getById( @Param( "id" ) Integer id );
}
