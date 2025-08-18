package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.Similarity;

import java.util.List;
import java.util.Set;

public interface SimilarityRepository extends JpaRepository<Similarity, Long> {
    @Query("""
            SELECT NEW Similarity(s.key, s.score, s.timestamp)
            FROM Similarity s
            WHERE s.key.eventId = :eventId
               OR s.key.otherEventId = :eventId
            """)
    List<Similarity> findAllContainsEventId(long eventId);

    @Query("""
            SELECT NEW Similarity(s.key, s.score, s.timestamp)
            FROM Similarity s
            WHERE s.key.eventId IN :eventId
               OR s.key.otherEventId IN :eventId
            """)
    List<Similarity> findNPairContainsEventIdsSortedDescScore(Set<Long> eventId, Pageable pageable);
}