package ru.practicum.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.api.dto.requestservice.RequestStatus;
import ru.practicum.requestservice.model.Request;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Collection<Request> findByRequesterId(Long requesterId);

    Collection<Request> findByEventId(Long eventId);

    Optional<Request> findByRequesterIdAndEventId(Long requesterId, Long eventId);

    List<Request> findByEventIdInAndStatus(List<Long> eventIds, RequestStatus requestStatus);
}
