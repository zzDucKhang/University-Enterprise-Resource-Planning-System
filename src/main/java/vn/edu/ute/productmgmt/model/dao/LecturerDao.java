package vn.edu.ute.productmgmt.model.dao;

import jakarta.persistence.EntityManager;
import vn.edu.ute.productmgmt.model.dto.LecturerDTO;
import vn.edu.ute.productmgmt.model.entity.Lecturer;
import vn.edu.ute.productmgmt.model.util.JpaUtil;
import java.util.List;

public class LecturerDao extends BaseDao<Lecturer, Long> {
    public LecturerDao() {
        super(Lecturer.class);
    }

    public List<LecturerDTO> findAllDTO() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT new vn.edu.ute.productmgmt.model.dto.LecturerDTO(" +
                    "l.id, l.lecturerCode, l.fullName, l.degree, l.gender, f.name) " +
                    "FROM Lecturer l JOIN l.faculty f";
            return em.createQuery(jpql, LecturerDTO.class).getResultList();
        } finally {
            em.close();
        }
    }
}