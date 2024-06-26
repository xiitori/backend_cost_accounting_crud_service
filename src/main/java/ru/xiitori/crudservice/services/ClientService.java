package ru.xiitori.crudservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.repositories.ClientRepository;
import ru.xiitori.crudservice.utils.exceptions.UpdateException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientService {

    private final PasswordEncoder passwordEncoder;

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(PasswordEncoder passwordEncoder, ClientRepository clientRepository) {
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Transactional
    public void saveClient(Client client) {
        client.setRole("ROLE_USER");
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
    }

    public Optional<Client> getClientById(int id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> getClientByUsername(String name) {
        return clientRepository.findByUsername(name);
    }

    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public boolean existsClientByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    public boolean existsClientByUsername(String username) {
        return clientRepository.existsByUsername(username);
    }

    @Transactional
    public void deleteClient(String username) {
        clientRepository.deleteByUsername(username);
    }

    @Transactional
    public void deleteClient(int id) {
        clientRepository.deleteById(id);
    }

    @Transactional
    public void changeClientUsername(int id, String username) {
        Client client = clientRepository.findById(id).get();

        if (!isValidUsername(username)) {
            throw new UpdateException("Username is already taken");
        }

        client.setUsername(username);
        clientRepository.save(client);
    }

    @Transactional
    public void changeClientPassword(int id, String newPassword) {
        Client client = clientRepository.findById(id).get();

        if (!isValidPassword(newPassword)) {
            throw new UpdateException("Password may be 6-30 characters long");
        }

        client.setPassword(passwordEncoder.encode(newPassword));
        clientRepository.save(client);
    }

    private boolean isValidUsername(String username) {
        return !clientRepository.existsByUsername(username);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6 && password.length() <= 30;
    }
}
