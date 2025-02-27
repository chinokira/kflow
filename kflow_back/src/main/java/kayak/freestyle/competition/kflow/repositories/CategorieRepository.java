package kayak.freestyle.competition.kflow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Competition;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    
    Optional<Competition> findByName(String name);

    Optional<Competition> findById(int id);
}
