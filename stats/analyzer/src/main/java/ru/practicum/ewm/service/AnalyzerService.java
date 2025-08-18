package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.configuration.UserActionWeightConfig;
import ru.practicum.ewm.model.Action;
import ru.practicum.ewm.model.ActionType;
import ru.practicum.ewm.model.Similarity;
import ru.practicum.grpc.stats.recommendation.InteractionsCountRequestProto;
import ru.practicum.grpc.stats.recommendation.RecommendedEventProto;
import ru.practicum.grpc.stats.recommendation.SimilarEventsRequestProto;
import ru.practicum.grpc.stats.recommendation.UserPredictionsRequestProto;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnalyzerService {
    private final ActionService actionService;
    private final SimilarityService similarityService;
    private final UserActionWeightConfig userActionWeightConfig;

    public List<RecommendedEventProto> getSimilarEvents(SimilarEventsRequestProto request) {
        List<Similarity> similarPair = similarityService.findAllContainsEventId(request.getEventId());

        Set<Long> ids = similarPair.stream().map(o -> o.getKey().getEventId()).collect(Collectors.toSet());
        Set<Long> otherIds = similarPair.stream().map(o -> o.getKey().getOtherEventId()).collect(Collectors.toSet());
        ids.addAll(otherIds);

        Set<Long> userEventIds = actionService.findAllByUserIdAndEventIdIn(request.getUserId(), ids, request.getMaxResults());

        similarPair.removeIf(o -> userEventIds.contains(o.getKey().getEventId()) && userEventIds.contains(o.getKey().getOtherEventId()));

        return similarPair.stream()
                .sorted(Comparator.comparing(Similarity::getScore, Comparator.reverseOrder()))
                .limit(request.getMaxResults())
                .map(o -> RecommendedEventProto.newBuilder()
                        .setEventId(o.getKey().getEventId() == request.getEventId() ? o.getKey().getOtherEventId() : o.getKey().getEventId())
                        .setScore(o.getScore())
                        .build()).toList();
    }

    public List<RecommendedEventProto> getRecommendationsForUser(UserPredictionsRequestProto request) {
        Set<Long> actionIds = actionService.findByUserIdOrderByTimestampDesc(request.getUserId(), request.getMaxResults());

        List<Similarity> similarities = similarityService.findNPairContainsEventIdsSortedDescScore(actionIds, request.getMaxResults());

        Map<Long, Double> eventIds = similarities.stream()
                .collect(Collectors.toMap(o -> actionIds.contains(o.getKey().getEventId()) ? o.getKey().getOtherEventId() : o.getKey().getEventId(),
                        Similarity::getScore, Double::max));

        return eventIds.entrySet().stream().map(o -> RecommendedEventProto.newBuilder()
                .setEventId(o.getKey())
                .setScore(o.getValue())
                .build()).toList();
    }

    public List<RecommendedEventProto> getInteractionsCount(InteractionsCountRequestProto request) {
        Set<Long> eventIds = new HashSet<>(request.getEventIdList());

        Map<Long, Double> actionMap = actionService.findAllByEventIds(eventIds).stream()
                .collect(Collectors.groupingBy(Action::getEventId,
                        Collectors.summingDouble(o -> getUserActionWeight(o.getActionType()))));

        return actionMap.entrySet().stream().map(o -> RecommendedEventProto.newBuilder()
                .setEventId(o.getKey())
                .setScore(o.getValue())
                .build()).toList();
    }

    private double getUserActionWeight(ActionType actionType) {
        return switch (actionType) {
            case VIEW -> userActionWeightConfig.getVIEW();
            case REGISTER -> userActionWeightConfig.getREGISTER();
            case LIKE -> userActionWeightConfig.getLIKE();
        };
    }
}
