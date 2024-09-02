package kr.luciddevlog.reservation.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kr.luciddevlog.reservation.user.entity.UserItem;
import kr.luciddevlog.reservation.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class RegisterForm {
    @NotBlank(message = "아이디를 입력해 주세요.")
    @Length(min = 4, max = 20, message = "아이디는 4자에서 20자 사이여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문자와 숫자만 사용 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 8, max = 20, message = "비밀번호는 8자에서 20자 사이여야 합니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "비밀번호는 숫자, 소문자, 대문자, 특수문자를 모두 포함해야 합니다.")
    private String password;

    @NotBlank(message="이름을 입력하세요")
    @Length(max = 50, message = "이름은 50자를 초과할 수 없습니다.")
    private String name;

    @NotBlank(message = "전화번호를 입력하세요")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
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

    // toString 메서드 추가 (디버깅용)
    @Override
    public String toString() {
        return "RegisterForm{" +
                "username='" + username + '\'' +
                ", password='" + (password != null ? "PRESENT" : "NULL") + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                '}';
    }

}
