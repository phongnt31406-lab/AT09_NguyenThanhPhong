package com.rise.dataproviders;

/**
 * Tên file Excel (mỗi module 1 file) và sheet (mỗi nhóm dữ liệu 1 sheet) trong src/test/resources/testdata.
 */
public final class TestDataFiles {

    public static final String LOGIN = "Login.xlsx";
    public static final String LOGIN_VALID = "LoginValid";
    public static final String LOGIN_INVALID = "LoginInvalid";

    public static final String CLIENTS = "Clients.xlsx";
    public static final String CLIENT = "Client";
    public static final String CLIENT_VALIDATION = "ClientValidation";
    public static final String CLIENT_SEARCH = "ClientSearch";
    public static final String CLIENT_EDIT = "ClientEdit";
    public static final String CLIENT_DELETE = "ClientDelete";
    public static final String CONTACT = "Contact";
    public static final String CONTACT_VALIDATION = "ContactValidation";

    public static final String PROJECTS = "Projects.xlsx";
    public static final String TASKS = "Tasks.xlsx";
    public static final String E2E = "E2E.xlsx";

    private TestDataFiles() {
    }
}
