package recipes.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record RegisterDto(
        @NotBlank @Pattern(regexp = ".+@.+\\..+") String email,
        @NotBlank @Pattern(regexp = "^.{8,}$+") String password) {
}
