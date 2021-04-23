package edu.zubkov.crudapp.services;

import edu.zubkov.crudapp.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> listOfRoles();

    Role roleById(long id);

    Set<Role> getAllRoles(List<String> idOfRoles);
}