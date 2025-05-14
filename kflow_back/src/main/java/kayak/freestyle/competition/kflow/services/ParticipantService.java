package kayak.freestyle.competition.kflow.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.mappers.ParticipantMapper;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.repositories.ParticipantRepository;
import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.mappers.RunMapper;

@Service
public class ParticipantService extends GenericService<Participant, ParticipantDto, ParticipantRepository, ParticipantMapper> {

    private final RunMapper runMapper;

    public ParticipantService(ParticipantRepository repository, ParticipantMapper mapper, RunMapper runMapper) {
        super(repository, mapper);
        this.runMapper = runMapper;
    }

    @Override
    public ParticipantDto save(ParticipantDto dto) {
        return super.save(dto);
    }

    @Transactional(readOnly = true)
    public List<RunDto> getRunsByParticipantId(Long participantId) {
        Participant participant = repository.findByIdAndFetchRunsEagerly(participantId)
            .orElseThrow(() -> new RuntimeException("Participant non trouvé avec l'id: " + participantId)); // Vous voudrez peut-être une exception plus spécifique
        return participant.getRuns().stream()
            .map(runMapper::modelToDto)
            .collect(Collectors.toList());
    }
}
