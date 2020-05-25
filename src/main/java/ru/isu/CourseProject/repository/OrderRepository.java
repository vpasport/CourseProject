package ru.isu.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.CourseProject.model.Executors;
import ru.isu.CourseProject.model.Order;
import ru.isu.CourseProject.model.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query( "from Order " )
    List<Order> getAll();

    @Query( "select o from Order as o " +
            "where o.id = :id" )
    Order searchById( @Param( "id" ) Integer id );

    @Query( "from Executors " )
    List<Executors> getAllExecutors();

    @Modifying
    @Query( "update Order as o set o.finalExecutor = :executor " +
            "where o.id = :order" )
    boolean updateExecutor(
            @Param( "order" ) Integer order,
            @Param( "executor" ) User executor
            );
}
