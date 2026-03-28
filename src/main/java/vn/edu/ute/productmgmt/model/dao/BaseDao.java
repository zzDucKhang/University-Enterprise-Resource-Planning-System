package vn.edu.ute.productmgmt.model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.edu.ute.productmgmt.model.util.JpaUtil;
import java.util.List;

public abstract class BaseDao<T, ID> {
    private final Class<T> entityClass;

    protected BaseDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // Hàm lưu mới hoặc cập nhật
    public void save(T entity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(entity); // merge dùng cho cả thêm mới và cập nhật
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Tìm theo ID
    public T findById(ID id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    // Lấy tất cả danh sách
    public List<T> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(jpql, entityClass).getResultList();
        } finally {
            em.close();
        }
    }

    // Xóa theo ID
    public void deleteById(ID id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Hàm truy vấn nâng cao:
    // Bạn sẽ cần các hàm như findByProperty(String prop, Object value) để tìm sinh viên theo mã, hoặc tìm lớp theo học kỳ.
}