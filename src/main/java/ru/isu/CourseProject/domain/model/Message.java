package ru.isu.CourseProject.domain.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

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

    @ManyToOne( fetch = FetchType.EAGER)
    private User from;

    @ManyToOne( fetch = FetchType.EAGER)
    private User to;

    @NotBlank( message = "text error" )
    private String text;

    private LocalDate date;

    private LocalTime time;
}
