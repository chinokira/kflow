package kayak.freestyle.competition.kflow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kayak.freestyle.competition.kflow.models.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findById(int id);

    @Query("SELECT p FROM Participant p LEFT JOIN FETCH p.runs WHERE p.id = :id")
    Optional<Participant> findByIdAndFetchRunsEagerly(@Param("id") Long id);
}
