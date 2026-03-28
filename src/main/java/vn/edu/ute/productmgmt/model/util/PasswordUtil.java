package vn.edu.ute.productmgmt.model.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class PasswordUtil {
    private PasswordUtil() {}

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi mã hóa mật khẩu", e);
        }
    }

    // Kiểm tra mật khẩu nhập vào có khớp với mã hash trong DB không
    public static boolean checkPassword(String input, String storedHash) {
        return hashPassword(input).equals(storedHash);
    }
}