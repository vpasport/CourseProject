package ru.isu.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.isu.CourseProject.model.Message;
import ru.isu.CourseProject.model.User;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query( "from Message " )
    List<Message> getAll();
}
