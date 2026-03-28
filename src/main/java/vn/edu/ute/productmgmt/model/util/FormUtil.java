package vn.edu.ute.productmgmt.model.util;

public final class FormUtil {
    private FormUtil() {}

    // Kiểm tra danh sách các chuỗi có cái nào trống không
    public static boolean areEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}