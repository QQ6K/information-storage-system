package ru.task.iss.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.task.iss.security.models.Role;
import ru.task.iss.security.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
