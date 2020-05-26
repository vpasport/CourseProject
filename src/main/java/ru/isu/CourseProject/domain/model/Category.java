package ru.isu.CourseProject.domain.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table( name = "category" )
public class Category {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "category_id" )
    private Integer id;

    @NotBlank( message = "name error" )
    private String name;

    @NotBlank( message = "description error" )
    private String description;
}
