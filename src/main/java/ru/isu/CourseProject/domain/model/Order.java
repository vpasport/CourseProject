package ru.isu.CourseProject.domain.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

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

    @Min( value = 0 )
    private Double maxPrice;

    @ManyToOne( fetch = FetchType.EAGER )
    private User finalExecutor;

    private String type;

    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable(
            name = "executors_order",
            joinColumns = @JoinColumn( name = "order_id" ),
            inverseJoinColumns = @JoinColumn( name = "executor_id" )
    )
    private Set<User> executors;

    private String status;
}
