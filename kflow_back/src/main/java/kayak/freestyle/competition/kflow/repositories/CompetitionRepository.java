package kayak.freestyle.competition.kflow.repositories;

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

    @Query("SELECT DISTINCT c FROM Competition c "
            + "LEFT JOIN FETCH c.categories cat "
            + "WHERE c.id = :id")
    Optional<Competition> findCompetitionWithDetails(@Param("id") Long id);
}
