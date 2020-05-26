package ru.isu.CourseProject.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.CourseProject.domain.model.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query( "from Message " )
    List<Message> getAll();

    @Query( "from Message as m where m.id = :id" )
    Message getMessageById( @Param( "id" ) Integer id );

    @Query( "select  m from Message as m " +
            "inner join m.to as u2 " +
            "inner join m.from as u1 " +
            "where u1.id = :from and u2.id = :to" )
    List<Message> getMessageBy2Id(
            @Param( "from" ) Integer from,
            @Param( "to" ) Integer to
    );
}
