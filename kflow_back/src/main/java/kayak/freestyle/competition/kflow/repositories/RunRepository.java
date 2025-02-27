package kayak.freestyle.competition.kflow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kayak.freestyle.competition.kflow.models.Run;

@Repository
public interface RunRepository extends JpaRepository<Run, Long> {

    Optional<Run> findById(int id);
}
