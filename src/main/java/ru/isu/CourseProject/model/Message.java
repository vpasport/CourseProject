package ru.isu.CourseProject.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table( name = "messages" )
public class Message {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "message_id" )
    private Integer id;

//    @ManyToOne
    private Integer fromId;

    private Integer toId;

    private String message;

    private LocalDate date;
}
