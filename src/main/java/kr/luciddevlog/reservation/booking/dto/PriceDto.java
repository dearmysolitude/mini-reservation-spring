package kr.luciddevlog.reservation.booking.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PriceDto {
    private long stayDuration;
    private BigDecimal totalPrice;

    public PriceDto(long stayDuration, BigDecimal totalPrice) {
        this.stayDuration = stayDuration;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "StayDuration: " + this.stayDuration + "TotalPrice: " + this.totalPrice;
    }
}
