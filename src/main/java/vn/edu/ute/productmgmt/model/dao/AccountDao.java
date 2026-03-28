package vn.edu.ute.productmgmt.model.dao;

import jakarta.persistence.EntityManager;
import vn.edu.ute.productmgmt.model.entity.Account;
import vn.edu.ute.productmgmt.model.util.JpaUtil;

public class AccountDao extends BaseDao<Account, Long> {
    public AccountDao() { super(Account.class); }

    public Account findByUsername(String username) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Account a WHERE a.username = :user", Account.class)
                    .setParameter("user", username)
                    .getSingleResult();
        } catch (Exception e) { return null; }
        finally { em.close(); }
    }
}