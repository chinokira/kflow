package kayak.freestyle.competition.kflow.mappers;

import kayak.freestyle.competition.kflow.dto.HasId;

public interface GenericMapper<MODEL, DTO extends HasId> {

    DTO modelToDto(MODEL m);

    MODEL dtoToModel(DTO d);
}
