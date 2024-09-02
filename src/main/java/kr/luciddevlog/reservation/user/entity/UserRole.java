package kr.luciddevlog.reservation.user.entity;

import kr.luciddevlog.reservation.common.enumConverterForJPA.AbstractCodedEnumConverter;
import kr.luciddevlog.reservation.common.enumConverterForJPA.CodedEnum;

public enum UserRole implements CodedEnum<String> {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String code;

    UserRole(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends AbstractCodedEnumConverter<UserRole, String> {
        public Converter() {
            super(UserRole.class);
        }
    }
}
