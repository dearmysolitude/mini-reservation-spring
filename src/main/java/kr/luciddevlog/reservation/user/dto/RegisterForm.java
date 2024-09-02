package kr.luciddevlog.reservation.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kr.luciddevlog.reservation.user.entity.UserItem;
import kr.luciddevlog.reservation.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterForm {
    @NotBlank(message = "아이디를 입력해 주세요.")
    private String username;

    @Size(min = 4, message = "비밀 번호는 4자 이상이어야 합니다.")
    private String password;

    @NotBlank(message="이름을 입력하세요")
    private String name;

    private String phoneNumber;

    private UserRole role;

    public RegisterForm(String username, String password, String phoneNumber, String name) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    @Builder
    public UserItem toEntity() {
        return UserItem.builder()
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .username(this.username)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
