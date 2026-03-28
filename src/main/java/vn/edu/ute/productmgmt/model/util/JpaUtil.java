package vn.edu.ute.productmgmt.model.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {
    // Singleton: Chỉ tạo 1 Factory duy nhất để tiết kiệm RAM
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("student-pu");

    private JpaUtil() {} // Ngăn khởi tạo đối tượng

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    public static void shutdown() {
        if (EMF != null && EMF.isOpen()) {
            EMF.close();
        }
    }
}