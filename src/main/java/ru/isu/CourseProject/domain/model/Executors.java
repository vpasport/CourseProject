package ru.isu.CourseProject.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table( name = "executors" )
public class Executors {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @ManyToOne( fetch = FetchType.EAGER )
    private Order order;

    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable(
            name = "executors_order",
            joinColumns = @JoinColumn( name =  "executor_id" ),
            inverseJoinColumns = @JoinColumn( name = "order_id" )
    )
    private Set<User> executors;
}
