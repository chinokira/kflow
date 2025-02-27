package kayak.freestyle.competition.kflow.services;

import org.springframework.stereotype.Service;

import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.mappers.ParticipantMapper;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.repositories.ParticipantRepository;

@Service
public class ParticipantService extends GenericService<Participant, ParticipantDto, ParticipantRepository, ParticipantMapper> {

    public ParticipantService(ParticipantRepository repository, ParticipantMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ParticipantDto save(ParticipantDto dto) {
        return super.save(dto);
    }
}
