package ru.xiitori.crudservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.xiitori.crudservice.dto.ExpenseAddDTO;
import ru.xiitori.crudservice.dto.ExpenseDTO;
import ru.xiitori.crudservice.models.Expense;
import ru.xiitori.crudservice.security.ClientDetails;
import ru.xiitori.crudservice.service.ExpenseService;
import ru.xiitori.crudservice.utils.exceptions.ExpenseNotFoundException;

import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ModelMapper mapper;

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ModelMapper mapper, ExpenseService expenseService) {
        this.mapper = mapper;
        this.expenseService = expenseService;
    }

    @PostMapping("/add")
    public void addExpense(@RequestBody ExpenseAddDTO expenseAddDTO) {
        Expense expense = mapper.map(expenseAddDTO, Expense.class);

        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        expense.setClient(clientDetails.getClient());

        expenseService.saveExpense(expense);
    }

    @GetMapping("/{id}")
    public ExpenseDTO getExpenseById(@PathVariable("id") int id) {
        ClientDetails clientDetails = (ClientDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        int clientId = clientDetails.getClient().getId();

        Optional<Expense> expense = expenseService.getExpenseById(id);

        if (expense.isEmpty()) {
            throw new ExpenseNotFoundException("There is no expense with id " + id);
        }

        if (clientId != expense.get().getClient().getId()) {
            throw new AccessDeniedException("You are not authorized to access this expense");
        }

        return mapper.map(expense.get(), ExpenseDTO.class);
    }


    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable("id") int id) {
        expenseService.deleteExpenseById(id);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
