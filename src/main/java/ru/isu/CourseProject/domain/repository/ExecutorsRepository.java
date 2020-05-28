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
//"inner join m.from as u1 " +

    @Query( "select u from Executors as e " +
            "inner join e.orders as o " +
            "inner join e.executors as u " +
            "where o.id = :id" )
    List<User> getAllExecutorsByOrderId( @Param( "id" ) Integer id );
//        "where u1.id = :from and u2.id = :to"

    @Query( "select e from Executors as e " +
            "where e.id = :id" )
    Executors getById( @Param( "id" ) Integer id );
}
