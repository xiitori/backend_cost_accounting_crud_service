package ru.xiitori.crudservice.models;

import jakarta.persistence.*;
import ru.xiitori.crudservice.models.types.ExpenseType;

@Entity
@Table(name = "expense")
public class Expense {

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

    public Expense() {
    }

    public Expense(ExpenseType expenseType, String description) {
        this.expenseType = expenseType;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
