package kayak.freestyle.competition.kflow.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kayak.freestyle.competition.kflow.models.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class UserDto implements HasId {

    @EqualsAndHashCode.Include
    private long id;

    @NotBlank
    private String name;

    @Email
    @JsonProperty("email")
    private String email;

    @NotBlank
    @Length(min = 8)
    @NotBlank(message = "Le mot de passe ne doit pas être vide")
    @JsonProperty("password")
    private String password;

    @NotBlank
    private Role role;

    @PostMapping
    public ResponseEntity<ParticipantDto> save(@RequestBody ParticipantDto userDto) {
        return ResponseEntity.ok(userDto);
    }
}
