package kayak.freestyle.competition.kflow.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kayak.freestyle.competition.kflow.models.Competition;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    Optional<Competition> findByPlace(String place);

    Optional<Competition> findById(int id);

    @Query("SELECT c FROM Competition c LEFT JOIN FETCH c.categories WHERE c.id = :id")
    Optional<Competition> findCompetitionWithCategories(@Param("id") Long id);

    default Optional<Competition> findCompetitionWithDetails(Long id) {
        Optional<Competition> competitionOpt = findCompetitionWithCategories(id);
        if (competitionOpt.isPresent()) {
            Competition competition = competitionOpt.get();
            // Initialiser les stages pour chaque catégorie
            competition.getCategories().forEach(categorie -> {
                categorie.getStages().size(); // Force le chargement des stages
                // Initialiser les runs pour chaque stage
                categorie.getStages().forEach(stage -> {
                    stage.getRuns().size(); // Force le chargement des runs
                });
            });
        }
        return competitionOpt;
    }
}
