package kayak.freestyle.competition.kflow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kayak.freestyle.competition.kflow.models.Competition;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long>{

    Optional<Competition> findByPlace(String place);

    Optional<Competition> findById(int id);
}
