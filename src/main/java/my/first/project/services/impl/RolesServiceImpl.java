package my.first.project.services.impl;

import my.first.project.entities.Roles;
import my.first.project.repositories.RolesRepository;
import my.first.project.services.RolesRervice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesRervice {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public void saveRoles(Roles roles) {
        rolesRepository.save(roles);
    }

    @Override
    public Roles getRolesById(Long id) {
        return rolesRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteRolesById(Long id) {
        rolesRepository.deleteById(id);
    }

    @Override
    public Roles createRole(Roles roles) {
        return rolesRepository.save(roles);
    }
}
