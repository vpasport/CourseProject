package ru.isu.CourseProject.model;

import java.time.LocalDate;

public class Order {
    private Integer id;

    private String name;

    private String description;

    private LocalDate date;

    private LocalDate deadline;

    private Integer categoryId;

    private Integer customerId;

    private Integer executorId;

    private Double maxPrice;

    private Integer finalExecutor;
}
