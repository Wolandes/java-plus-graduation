package ru.practicum.ewm.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.ewm.configuration.UserActionWeightConfig;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AggregatorRepository {
    private final UserActionWeightConfig userActionWeightConfig;

    private final Map<Long, Map<Long, Double>> userActionsWeight = new HashMap<>();

    private final Map<Long, Double> eventsWeightsSum = new HashMap<>();

    private final Map<Long, Map<Long, Double>> minWeightsSum = new HashMap<>();

    public AggregatorRepository(UserActionWeightConfig userActionWeightConfig) {
        this.userActionWeightConfig = userActionWeightConfig;
    }

    public List<EventSimilarityAvro> updateEventSimilarity(UserActionAvro userAction) {
        long userId = userAction.getUserId();
        long eventId = userAction.getEventId();

        double oldWeight = userActionsWeight.computeIfAbsent(eventId, e -> new HashMap<>()).getOrDefault(userId, 0.0);
        double newWeight = getUserActionWeight(userAction.getActionType());

        if (oldWeight >= newWeight) {
            return List.of();
        }

        userActionsWeight.computeIfAbsent(eventId, e -> new HashMap<>()).merge(userId, newWeight, Math::max);

        double oldSum = eventsWeightsSum.getOrDefault(eventId, 0.0);
        double newSum = oldSum - oldWeight + newWeight;
        eventsWeightsSum.put(eventId, newSum);

        List<EventSimilarityAvro> eventSimilarityAvros = new ArrayList<>();

        for (long otherEventId : userActionsWeight.keySet()) {
            if (otherEventId == eventId ||
                    !userActionsWeight.get(otherEventId).containsKey(userId)) {
                continue;
            }
            double newSumMinPairWeight = updateMinWeightSum(eventId, otherEventId, userId, oldWeight, newWeight);
            double similarity = calcSimilarity(eventId, otherEventId, newSumMinPairWeight);
            eventSimilarityAvros.add(getEventSimilarityAvro(eventId, otherEventId, similarity, userAction.getTimestamp()));
        }
        return eventSimilarityAvros;
    }

    private EventSimilarityAvro getEventSimilarityAvro(long eventId, long otherEventId, double similarity, Instant timestamp) {
        long firstEventId = Math.min(eventId, otherEventId);
        long secondEventId = Math.max(eventId, otherEventId);

        return EventSimilarityAvro.newBuilder()
                .setEventA(firstEventId)
                .setEventB(secondEventId)
                .setTimestamp(timestamp)
                .setScore(similarity).build();
    }

    private double calcSimilarity(long eventId, long otherEventId, double newSumMinPairWeight) {
        if (newSumMinPairWeight == 0) return 0;

        double sumEventWeight = eventsWeightsSum.get(eventId);
        double sumOtherEventWeight = eventsWeightsSum.get(otherEventId);
        return newSumMinPairWeight / (Math.sqrt(sumEventWeight) * Math.sqrt(sumOtherEventWeight));
    }

    private double updateMinWeightSum(long eventId, long otherEventId, long userId, double oldWeight, double newWeight) {
        double oldWeightOtherEvent = userActionsWeight.get(otherEventId).get(userId);

        double oldMinPairWeight = Math.min(oldWeight, oldWeightOtherEvent);
        double newMinPairWeight = Math.min(newWeight, oldWeightOtherEvent);

        long firstEventId = Math.min(eventId, otherEventId);
        long secondEventId = Math.max(eventId, otherEventId);

        double oldSumMinPairWeight = minWeightsSum.computeIfAbsent(firstEventId,
                k -> new HashMap<>()).getOrDefault(secondEventId, 0.0);

        if (oldMinPairWeight == newMinPairWeight) return oldSumMinPairWeight;

        double newSumMinPairWeight = oldSumMinPairWeight - oldMinPairWeight + newMinPairWeight;
        minWeightsSum.computeIfAbsent(firstEventId, k -> new HashMap<>()).put(secondEventId, newSumMinPairWeight);
        return newSumMinPairWeight;
    }

    private double getUserActionWeight(ActionTypeAvro actionType) {
        return switch (actionType) {
            case VIEW -> userActionWeightConfig.getVIEW();
            case REGISTER -> userActionWeightConfig.getREGISTER();
            case LIKE -> userActionWeightConfig.getLIKE();
        };
    }
}
