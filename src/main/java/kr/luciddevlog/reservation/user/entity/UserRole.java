package kr.luciddevlog.reservation.user.entity;

import kr.luciddevlog.reservation.common.enumConverterForJPA.AbstractCodedEnumConverter;
import kr.luciddevlog.reservation.common.enumConverterForJPA.CodedEnum;

public enum UserRole implements CodedEnum<Integer> {
    USER_ADMIN(1),
    USER_ROLE(2);

    private final int code;

    UserRole(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends AbstractCodedEnumConverter<UserRole, Integer> {
        public Converter() {
            super(UserRole.class);
        }
    }
}
