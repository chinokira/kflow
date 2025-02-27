package kayak.freestyle.competition.kflow.services;

import org.springframework.stereotype.Service;

import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.mappers.StageMapper;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.repositories.StageRepository;

@Service
public class StageService extends GenericService<Stage, StageDto, StageRepository, StageMapper> {

    public StageService(StageRepository repository, StageMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public StageDto save(StageDto dto) {
        return super.save(dto);
    }
}
