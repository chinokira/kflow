package kayak.freestyle.competition.kflow.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kayak.freestyle.competition.kflow.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements HasId {

    private Long id;

    @NotBlank(message = "Le nom ne doit pas être vide")
    private String name;

    @NotBlank(message = "L'email ne doit pas être vide")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe ne doit pas être vide")
    @Length(min = 8, message = "Le mot de passe doit faire au moins 8 caractères")
    private String password;

    @NotNull(message = "Le rôle ne doit pas être null")
    private Role role;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
