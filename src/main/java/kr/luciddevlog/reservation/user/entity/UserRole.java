package kr.luciddevlog.reservation.user.entity;

import kr.luciddevlog.reservation.common.enumConverter.AbstractCodedEnumConverter;
import kr.luciddevlog.reservation.common.enumConverter.CodedEnum;

public enum UserRole implements CodedEnum<Integer> {
    USER_ROLE(1),
    USER_ADMIN(2);

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
