package ru.isu.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.isu.CourseProject.model.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query( "select u from User as u where u.id = :id" )
    User searchById(@Param("id") Integer id);

    @Query( "from User" )
    List<User> getAll();

    @Query( "select u.id from User as u" )
    List<Integer> getAllId();

    @Query( "select u from User as u " +
            "where u.login like :login" )
    User getByLogin( @Param( "login" ) String login );

    @Query( "select u from User as u " +
            "where u.role like 'Customer'" )
    List<User> getAllCustomers();

    @Query( "select u from User as u " +
            "where u.role like 'Executor'" )
    List<User> getAllExecutors();

    @Query( "select u from User as u " +
            "where u.role like 'Admin'" )
    List<User> getAllAdmins();

//    ( id, firstName, secondName, login, password, email, lastActivity, role, phone, rating, sex, age, specialty )

    @Modifying
    @Transactional
    @Query( value = "insert into Users " +
            "values (, :firstName, :secondName, :login, :password, :email, :lastActivity, :role, :phone, :rating, :sex, :age, :specialty )",
            nativeQuery = true )
    void createUser(
                     @Param( "firstName" ) String firstName,
                     @Param( "secondName" ) String secondName,
                     @Param( "login" ) String login,
                     @Param( "password" ) String password,
                     @Param( "email" ) String email,
                     @Param( "lastActivity" ) LocalDate lastActivity,
                     @Param( "role" ) String role,
                     @Param( "phone" ) String phone,
                     @Param( "rating" ) Double rating,
                     @Param( "sex" ) String sex,
                     @Param( "age" ) Integer age,
                     @Param( "specialty" ) String specialty
    );
}
