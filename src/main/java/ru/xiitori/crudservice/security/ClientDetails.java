package ru.xiitori.crudservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.xiitori.crudservice.models.Client;

import java.util.Collection;
import java.util.List;


public class ClientDetails implements UserDetails {

    private final Client client;

    public ClientDetails(Client client) {
        this.client = client;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return client.getPassword();
    }

    @Override
    public String getUsername() {
        return client.getUsername();
    }

    public Client getClient() {
        return client;
    }
}
