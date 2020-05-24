package ru.isu.CourseProject.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

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

    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_message",
        joinColumns = @JoinColumn( name = "user" ),
        inverseJoinColumns = @JoinColumn( name = "message_id")
    )
    private Set<User> from;

    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
            name = "message_user",
            joinColumns = @JoinColumn( name = "user" ),
            inverseJoinColumns = @JoinColumn( name = "message_id")
    )
    private Set<User> to;

    @NotBlank( message = "text error" )
    private String text;

    private LocalDate date;

    private LocalTime time;
}
