package ru.isu.CourseProject.domain.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table( name = "orders" )
public class Order {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "order_id" )
    private Integer id;

    @NotBlank( message = "name error" )
    private String name;

    @NotBlank( message = "description error" )
    private String description;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate date;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate deadline;

    @ManyToOne( fetch = FetchType.EAGER )
    private Category category;

    @ManyToOne( fetch = FetchType.EAGER )
    private User customer;

    @ManyToOne( fetch = FetchType.EAGER )
    private Executors executors;

    @Min( value = 0 )
    private Double maxPrice;

    @ManyToOne( fetch = FetchType.EAGER )
    private User finalExecutor;
}
