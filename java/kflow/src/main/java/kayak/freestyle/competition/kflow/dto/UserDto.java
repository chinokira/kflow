package kayak.freestyle.competition.kflow.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    private String email;

    @NotBlank
    @Length(min = 8)
    private String password;

    private int pollsNumber;

}
