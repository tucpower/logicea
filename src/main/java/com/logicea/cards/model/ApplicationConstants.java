package com.logicea.cards.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class ApplicationConstants {

    public static final long JWT_EXPIRATION = 60 * 60 * 1000;
    public static final String JWT_SECRET = "my_secret";
    public static final String ROLE_ADMIN = "Admin";
    public static final String ROLE_MEMBER = "Member";
    public static final String CARD_STATUS_TO_DO = "To Do";
    public static final String CARD_STATUS_IN_PROGRESS = "In Progress";
    public static final String CARD_STATUS_DONE = "Done";
    public static final SimpleGrantedAuthority ADMIN_GRANTED_AUTHORITY = new SimpleGrantedAuthority(ApplicationConstants.ROLE_ADMIN);
    public static final SimpleGrantedAuthority MEMBER_GRANTED_AUTHORITY = new SimpleGrantedAuthority(ApplicationConstants.ROLE_MEMBER);
}
