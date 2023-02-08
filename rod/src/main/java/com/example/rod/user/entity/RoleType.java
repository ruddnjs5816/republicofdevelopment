package com.example.rod.user.entity;

public enum RoleType {

    BRONZE(Authority.BRONZE),
    SILVER(Authority.SILVER),
    GOLD(Authority.GOLD),
    PLATINUM(Authority.PLATINUM),
    DIAMOND(Authority.DIAMOND),
    MASTER(Authority.MASTER),
    GRANDMASTER(Authority.GRANDMASTER);

    private final String authority;

    RoleType(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public static class Authority{
        public static final String BRONZE = "ROLE_BRONZE";
        public static final String SILVER = "ROLE_SILVER";
        public static final String GOLD = "ROLE_GOLD";
        public static final String PLATINUM = "ROLE_PLATINUM";
        public static final String DIAMOND = "ROLE_DIAMOND";
        public static final String MASTER = "ROLE_MASTER";
        public static final String GRANDMASTER = "ROLE_GRANDMASTER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
