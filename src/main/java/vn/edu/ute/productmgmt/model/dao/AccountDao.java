package vn.edu.ute.productmgmt.model.dao;

import jakarta.persistence.EntityManager;
import vn.edu.ute.productmgmt.model.entity.Account;
import vn.edu.ute.productmgmt.model.util.JpaUtil;

public class AccountDao extends BaseDao<Account, Long> {
    public AccountDao() { super(Account.class); }

    public Account findByUsername(String username) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // Sử dụng JOIN FETCH để lấy luôn thông tin Student và Lecturer đi kèm
            String jpql = "SELECT a FROM Account a " +
                    "LEFT JOIN FETCH a.student " +
                    "LEFT JOIN FETCH a.lecturer " +
                    "WHERE a.username = :user";

            return em.createQuery(jpql, Account.class)
                    .setParameter("user", username)
                    .getSingleResult();
        } catch (Exception e) {
            // Nếu không tìm thấy hoặc lỗi, trả về null
            return null;
        } finally {
            em.close();
        }
    }
}