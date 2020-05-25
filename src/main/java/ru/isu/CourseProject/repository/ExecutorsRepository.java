package ru.isu.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.isu.CourseProject.model.Executors;
import ru.isu.CourseProject.model.User;

import java.util.List;

public interface ExecutorsRepository extends JpaRepository<Executors, Integer> {

    @Query( "select e.executors from Executors as e " +
            "where e.order.id = :id" )
    List<User> getAllExecutorsByOrderId( @Param( "id" ) Integer id );
}
