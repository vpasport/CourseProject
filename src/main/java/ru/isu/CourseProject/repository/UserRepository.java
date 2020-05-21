package ru.isu.CourseProject.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.isu.CourseProject.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query( "select u from User as u where u.id = :id" )
    User searchById(@Param("id") Integer id);

    @Query( "from User" )
    List<User> getAll();

//    @Modifying
//    @Transactional
//    @Query( value = "insert into Users (id, login, password, firstName, secondName ) values (, :login, :password, :firstName, :secondName )",
//            nativeQuery = true )
//    void createUser( @Param( "login" ) String login, @Param( "password" ) String password,
//                     @Param( "firstName" ) String firstName, @Param( "secondName" ) String secondName );
}
