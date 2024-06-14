package ru.xiitori.crudservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xiitori.crudservice.models.types.IncomeType;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "income")
public class Income {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "income_type")
    @Enumerated(EnumType.STRING)
    private IncomeType incomeType;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private double value;

    @Column(name = "made_at")
    private Date madeAt;

    public Income(IncomeType incomeType, String description) {
        this.incomeType = incomeType;
        this.description = description;
    }
}
