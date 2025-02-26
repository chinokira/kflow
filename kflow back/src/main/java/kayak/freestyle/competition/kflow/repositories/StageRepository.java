package kayak.freestyle.competition.kflow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kayak.freestyle.competition.kflow.models.Stage;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

    Optional<Stage> findById(int id);

    Optional<Stage> findByRules(String rules);
}
