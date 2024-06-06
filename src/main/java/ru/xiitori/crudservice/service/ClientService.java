package ru.xiitori.crudservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.repositories.ClientRepository;

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

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClient(int id) {
        return clientRepository.findById(id);
    }

    @Transactional
    public void updateClient(Client client, int id) {
        client.setId(id);
        clientRepository.save(client);
    }

    @Transactional
    public void saveClient(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
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

}
