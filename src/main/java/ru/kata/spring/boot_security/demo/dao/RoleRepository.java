package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.security.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Override
    Role save(Role role);
}

