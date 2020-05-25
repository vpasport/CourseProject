package ru.isu.CourseProject.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table( name = "users" )
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "user_id" )
    private Integer id;

    private String firstName;

    private String secondName;

    @NotBlank( message = "login error")
    private String login;

    private String password;

    @NotBlank( message = "email error" )
    private String email;

    private LocalDate lastActivity;

    private String role;

    @NotBlank( message = "phone error" )
    private String phone;

    private Double rating;

    private String sex;

    @Min( value = 18 )
    private Integer age;

    private String specialty;

    private String status;

    private String description;
}
