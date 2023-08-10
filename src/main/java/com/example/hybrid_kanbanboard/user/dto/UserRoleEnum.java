package com.example.hybrid_kanbanboard.user.dto;

public enum UserRoleEnum {
    USER(Authority.USER),
    ADMIN(Authority.ADMIN),
    BOARDMEMBER(Authority.BOARDMEMBER);

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String BOARDMEMBER = "ROLE_BOARDMEMBER";
    }
}
