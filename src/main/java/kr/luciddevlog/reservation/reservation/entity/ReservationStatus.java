package kr.luciddevlog.reservation.reservation.entity;

import kr.luciddevlog.reservation.common.enumConverterForJPA.AbstractCodedEnumConverter;
import kr.luciddevlog.reservation.common.enumConverterForJPA.CodedEnum;

public enum ReservationStatus implements CodedEnum<Integer> {
    BEFORE_DEPOSIT(0),
    BEFORE_CHECKIN(1),
    CHECKED_IN(2),
    CHECKOUT(3),
    CANCELED(-1);

    private final int code;

    ReservationStatus(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends AbstractCodedEnumConverter<ReservationStatus, Integer> {
        public Converter() {
            super(ReservationStatus.class);
        }
    }

}
