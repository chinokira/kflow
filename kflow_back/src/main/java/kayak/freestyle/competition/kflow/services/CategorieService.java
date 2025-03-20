package kayak.freestyle.competition.kflow.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.mappers.CategorieMapper;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.repositories.CategorieRepository;

@Service
public class CategorieService extends GenericService<Categorie, CategorieDto, CategorieRepository, CategorieMapper> {

    private final CategorieRepository repository;

    public CategorieService(CategorieRepository repository, CategorieMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Categorie getCategorieWithParticipants(Long id) {
        return repository.findCategorieWithParticipants(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée avec l'id: " + id));
    }
}
