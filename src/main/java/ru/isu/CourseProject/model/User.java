package ru.isu.CourseProject.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table( name = "users" )
public class User {

    @Id
    @GeneratedValue
    @Column( name = "user_id" )
    private Integer id;

    private String firstName;

    private String secondName;

    @NotBlank( message = "login error")
    private String login;

    private String password;
}
