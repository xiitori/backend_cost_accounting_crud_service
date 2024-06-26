package ru.xiitori.crudservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xiitori.crudservice.models.types.ExpenseType;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "expense")
public class Expense implements Note {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "expense_type")
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private double value;

    @Column(name = "made_at")
    private LocalDateTime madeAt;

    public Expense(ExpenseType expenseType, String description, double value) {
        this.expenseType = expenseType;
        this.description = description;
        this.value = value;
    }
}
