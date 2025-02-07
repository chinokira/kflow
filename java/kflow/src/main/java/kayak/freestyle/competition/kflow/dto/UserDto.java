package kayak.freestyle.competition.kflow.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Run;
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

    private int bibNb;

    private List<Categorie> categories;

    private List<Run> runs;

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userDto);
    }
}
