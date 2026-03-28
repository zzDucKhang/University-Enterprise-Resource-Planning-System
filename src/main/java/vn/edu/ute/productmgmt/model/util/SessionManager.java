package vn.edu.ute.productmgmt.model.util;

import vn.edu.ute.productmgmt.model.dto.AccountDTO;

//Lưu thông tin người dùng đang đăng nhập
public final class SessionManager {
    private static AccountDTO currentAccount;

    private SessionManager() {}

    public static void login(AccountDTO account) {
        currentAccount = account;
    }

    public static void logout() {
        currentAccount = null;
    }

    public static AccountDTO getCurrentAccount() {
        return currentAccount;
    }

    public static boolean isLoggedIn() {
        return currentAccount != null;
    }
}