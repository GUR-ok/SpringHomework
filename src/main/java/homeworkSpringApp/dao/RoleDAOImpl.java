package homeworkSpringApp.dao;

import homeworkSpringApp.model.Role;
import homeworkSpringApp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class RoleDAOImpl implements RoleDAO{

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }
}
