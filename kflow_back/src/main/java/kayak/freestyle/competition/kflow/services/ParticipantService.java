package kayak.freestyle.competition.kflow.services;

import org.springframework.stereotype.Service;

import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.mappers.ParticipantMapper;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.repositories.ParticipantRepository;

@Service
public class ParticipantService extends GenericService<Participant, ParticipantDto, ParticipantRepository, ParticipantMapper> {

    /*private final PasswordEncoder passwordEncoder;*/
    public ParticipantService(ParticipantRepository repository, ParticipantMapper mapper/* , PasswordEncoder passwordEncoder*/) {
        super(repository, mapper);
        /*this.passwordEncoder = passwordEncoder;*/
    }

    @Override
    public ParticipantDto save(ParticipantDto dto) {
        /*dto.setPassword(passwordEncoder.encode(dto.getPassword()));*/
        return super.save(dto);
    }
}
