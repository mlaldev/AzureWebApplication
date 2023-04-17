package com.azure.server.Helper;

import com.azure.server.Model.Role;

public enum Roles {
    BUYER("ROLE_BUYER"),
    SELLER("ROLE_SELLER"),
    ADMIN("ROLE_ADMIN");

    private String value;

    Roles(String value){
        this.value = value;
    }
    public String value() {
        return value;
    }

    public static boolean isValidRole(Role role) {
        for(Roles validRole : Roles.values()) {
            if(role.getRole().equals(validRole.value())) {
                return true;
            }
        }
        return false;
    }
}
