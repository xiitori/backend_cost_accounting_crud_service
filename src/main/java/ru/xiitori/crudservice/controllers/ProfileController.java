package ru.xiitori.crudservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.xiitori.crudservice.dto.client.ClientInfoDTO;
import ru.xiitori.crudservice.dto.expense.ExpenseAddDTO;
import ru.xiitori.crudservice.dto.expense.ExpenseDTO;
import ru.xiitori.crudservice.dto.income.IncomeAddDTO;
import ru.xiitori.crudservice.dto.income.IncomeDTO;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.models.Expense;
import ru.xiitori.crudservice.models.Income;
import ru.xiitori.crudservice.security.ClientDetails;
import ru.xiitori.crudservice.service.ExpenseService;
import ru.xiitori.crudservice.service.IncomeService;
import ru.xiitori.crudservice.utils.ExceptionResponse;
import ru.xiitori.crudservice.utils.exceptions.EntityNotFoundException;
import ru.xiitori.crudservice.utils.exceptions.ExpenseNotFoundException;
import ru.xiitori.crudservice.utils.exceptions.IncomeNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ModelMapper modelMapper;

    private final ExpenseService expenseService;

    private final IncomeService incomeService;

    @Autowired
    public ProfileController(ModelMapper modelMapper, ExpenseService expenseService, IncomeService incomeService) {
        this.modelMapper = modelMapper;
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }

    @GetMapping()
    public ClientInfoDTO getClientInfo() {
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = clientDetails.getClient();

        return modelMapper.map(client, ClientInfoDTO.class);
    }

    @GetMapping("/expenses")
    public List<ExpenseDTO> getExpenses() {
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int clientId = clientDetails.getClient().getId();
        return expenseService.getExpensesByClientId(clientId).stream()
                .map(expense -> modelMapper.map(expense, ExpenseDTO.class)).toList();
    }

    @GetMapping("/incomes")
    public List<IncomeDTO> getIncomes() {
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int clientId = clientDetails.getClient().getId();
        return incomeService.getIncomesByClientId(clientId).stream()
                .map(expense -> modelMapper.map(expense, IncomeDTO.class)).toList();
    }

    @PostMapping("/expenses")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseAddDTO expenseAddDTO) {
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = clientDetails.getClient();

        Expense expense = modelMapper.map(expenseAddDTO, Expense.class);
        expense.setClient(client);

        expenseService.saveExpense(expense);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/incomes")
    public ResponseEntity<?> addIncome(@RequestBody IncomeAddDTO incomeAddDTO) {
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = clientDetails.getClient();

        Income income = modelMapper.map(incomeAddDTO, Income.class);
        income.setClient(client);

        incomeService.saveIncome(income);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/expenses/{id}")
    public ExpenseDTO getExpense(@PathVariable("id") int id) {
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = clientDetails.getClient();
        Optional<Expense> optional = expenseService.getExpenseById(id);

        if (optional.isEmpty() || optional.get().getClient().getId() != client.getId()) {
            throw new ExpenseNotFoundException("Expense with id " + id + " not found!");
        }

        return modelMapper.map(optional.get(), ExpenseDTO.class);
    }

    @GetMapping("/incomes/{id}")
    public IncomeDTO getIncome(@PathVariable("id") int id) {
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = clientDetails.getClient();
        Optional<Income> optional = incomeService.getIncomeById(id);

        if (optional.isEmpty() || optional.get().getClient().getId() != client.getId()) {
            throw new IncomeNotFoundException("Income with id " + id + " not found!");
        }

        return modelMapper.map(optional.get(), IncomeDTO.class);
    }

    @PostMapping("/expenses/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable("id") int id, @RequestBody ExpenseAddDTO expenseAddDTO) {
        Expense expense = modelMapper.map(expenseAddDTO, Expense.class);
        expense.setId(id);

        expenseService.updateExpense(expense);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/incomes/{id}")
    public ResponseEntity<?> updateIncome(@PathVariable("id") int id, @RequestBody IncomeAddDTO incomeAddDTO) {
        Income income = modelMapper.map(incomeAddDTO, Income.class);
        income.setId(id);

        incomeService.updateIncome(income);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable("id") int id) {
        expenseService.deleteExpenseById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/incomes/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable("id") int id) {
        incomeService.deleteIncomeById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }
}
