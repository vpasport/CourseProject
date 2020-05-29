package ru.isu.CourseProject.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.isu.CourseProject.domain.model.Executors;
import ru.isu.CourseProject.domain.model.Order;
import ru.isu.CourseProject.domain.model.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query( "from Order " )
    Page<Order> getAll(Pageable page);

    @Query( "from Order " )
    List<Order> getAll();

    @Query( "select o from Order as o " +
            "where o.type = :type" )
    List<Order> getByType(
            @Param( "type" ) String type
    );

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

    @Modifying
    @Transactional
    @Query( "delete from Order as o " +
            "where o.id = :id" )
    void deleteById(
            @Param( "id" ) Integer id
    );

    @Query( "select max(o.id) from Order as o " )
    Integer getMaxId();

    @Query( "select o from Order as o " +
            "where o.customer.id = :id" )
    List<Order> getByCustomerId(
            @Param( "id" ) Integer id
    );

    @Query( "select o from Order as o " +
            "inner join o.executors as e " +
            "where o.status = :status and  e.id = :id" )
    List<Order> getOrdersWithStatusForExecutor(
            @Param( "id" ) Integer id,
            @Param( "status" ) String status
    );

    @Query( "select o from Order as o " +
            "where o.type = :type and o.status is null " )
    List<Order> getOrdersByTypeWithStatusNull(
            @Param( "type" ) String type
    );

    @Query( "select o from Order as o " +
            "inner join o.executors as e " +
            "where e.id = :id and o.status is not null " )
    List<Order> getOrdersByExecutorIdWithStatusNotNull(
            @Param( "id" ) Integer id
    );

    @Query( "select o from Order as o " +
            "where o.type = :type and o.status is null and o.finalExecutor = :id" )
    List<Order> getOrdersByExecutorIdAndTypeWithStatusNull(
            @Param( "type" ) String type,
            @Param( "id" ) Integer ib
    );
}
