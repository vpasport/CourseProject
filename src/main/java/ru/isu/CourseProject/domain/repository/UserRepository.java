package ru.isu.CourseProject.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.isu.CourseProject.domain.model.User;

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
            "where u.login = :login" )
    User getByLogin( @Param( "login" ) String login );

    @Query( "select u from User as u " +
            "where u.role = :role" )
    List<User> getAllByRole(
            @Param( "role" ) String role
    );

    @Modifying
    @Transactional
    @Query( "delete from User as u " +
            "where u.id = :id " )
    void deleteById(
      @Param( "id" ) Integer id
    );

    @Query( "select u from User as u " +
            "where u.token = :token" )
    User getUserByToken(
            @Param( "token" ) String token
    );

    @Modifying
    @Transactional
    @Query( "update User as u " +
            "set u.token = :token " +
            "where u.id = :id" )
    void setToken(
            @Param( "token" ) String token,
            @Param( "id" ) Integer id
    );

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
