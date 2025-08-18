package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.Action;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ActionRepository extends JpaRepository<Action, Long> {
    @Query("""
            SELECT DISTINCT a.eventId
            FROM Action a
            WHERE a.userId = :userId
              AND a.eventId IN :otherEventId
            """)
    List<Long> findEventIdsByUserIdAndEventIdIn(long userId, Set<Long> otherEventId, Pageable pageable);

    Optional<Action> findByUserIdAndEventId(long userId, long eventId);

    @Query("""
            SELECT DISTINCT a.eventId
            FROM Action a
            WHERE a.userId = :userId
              AND a.eventId IN :otherEventId
            """)
    List<Long> findByUserIdOrderByTimestampDesc(long userId, int maxResult);

    List<Action> findAllByEventIdIn(Set<Long> eventIds);
}
