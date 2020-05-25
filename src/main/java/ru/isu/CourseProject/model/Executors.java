package ru.isu.CourseProject.model;

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
    private Order orderId;

    @ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinTable(
            name = "executors_order",
            joinColumns = @JoinColumn( name =  "executor_id" ),
            inverseJoinColumns = @JoinColumn( name = "order_id" )
    )
    private Set<User> executorId;
}
