package edu.zubkov.crudapp.dao;

import edu.zubkov.crudapp.models.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> listOfRoles() {
        return entityManager.createQuery("FROM Role").getResultList();
    }

    @Override
    public Role roleById(long id) {
        return entityManager.find(Role.class, id);
    }
}