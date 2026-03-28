package vn.edu.ute.productmgmt.model.util;

import java.util.regex.Pattern;

public final class ValidationUtil {
    private ValidationUtil() {}

    // Email chuẩn: example@student.ute.edu.vn
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

    // Giả sử MSSV UTE có 8 chữ số
    private static final String STUDENT_CODE_PATTERN = "^\\d{8}$";

    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }

    public static boolean isValidStudentCode(String code) {
        return Pattern.compile(STUDENT_CODE_PATTERN).matcher(code).matches();
    }
}