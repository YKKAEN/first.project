package my.first.project.services;

import my.first.project.entities.Roles;

import java.util.List;

public interface RolesRervice {

    List<Roles> getAllRoles();

    void saveRoles(Roles roles);

    Roles getRolesById(Long id);

    void deleteRolesById(Long id);

    Roles createRole(Roles roles);
}
