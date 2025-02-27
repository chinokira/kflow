package kayak.freestyle.competition.kflow.services;

import org.springframework.stereotype.Service;

import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.mappers.RunMapper;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.repositories.RunRepository;

@Service
public class RunService extends GenericService<Run, RunDto, RunRepository, RunMapper> {

    public RunService(RunRepository repository, RunMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public RunDto save(RunDto dto) {
        return super.save(dto);
    }
}
