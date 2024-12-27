package kayak.freestyle.competition.kflow.services;

import org.springframework.stereotype.Service;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.mappers.CategorieMapper;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.repositories.CategorieRepository;

@Service
public class CategorieService  extends GenericService<Categorie, CategorieDto, CategorieRepository, CategorieMapper> {
    
    public CategorieService(CategorieRepository repository, CategorieMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public CategorieDto save(CategorieDto dto) {
        return super.save(dto);
    }

}
