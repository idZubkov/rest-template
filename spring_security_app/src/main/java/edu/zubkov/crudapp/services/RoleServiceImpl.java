package edu.zubkov.crudapp.services;

import edu.zubkov.crudapp.dao.RoleDAO;
import edu.zubkov.crudapp.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public List<Role> listOfRoles() {
        return roleDAO.listOfRoles();
    }

    @Override
    public Role roleById(long id) {
        return roleDAO.roleById(id);
    }

    @Override
    public Set<Role> getAllRoles(List<String> idOfRoles) {
        Set<Role> setOfRoles = new HashSet<>();
        for (String id : idOfRoles) {
            setOfRoles.add(roleDAO.roleById(Long.parseLong(id)));
        }
        return setOfRoles;
    }
}