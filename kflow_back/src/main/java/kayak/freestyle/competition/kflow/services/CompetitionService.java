package kayak.freestyle.competition.kflow.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.mappers.CompetitionMapper;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.repositories.CompetitionRepository;

@Service
public class CompetitionService extends GenericService<Competition, CompetitionDto, CompetitionRepository, CompetitionMapper> {

    public CompetitionService(CompetitionRepository repository, CompetitionMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public CompetitionDto save(CompetitionDto dto) {
        return super.save(dto);
    }

    @Transactional(readOnly = true)
    public CompetitionDto getCompetitionWithDetails(Long id) {
        Competition competition = repository.findCompetitionWithDetails(id)
            .orElseThrow(() -> new RuntimeException("Competition not found with id: " + id));
        return mapper.modelToDto(competition);
    }
}
