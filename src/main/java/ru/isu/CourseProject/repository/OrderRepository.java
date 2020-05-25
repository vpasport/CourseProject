package ru.isu.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.isu.CourseProject.model.Order;
import ru.isu.CourseProject.model.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query( "from Order " )
    List<Order> getAll();
}
