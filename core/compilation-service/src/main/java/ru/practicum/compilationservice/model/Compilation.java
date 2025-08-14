package ru.practicum.compilationservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

/**
 * Подборка событий.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "compilations", schema = "public")
public class Compilation {
    /**
     * Уникальный идентификатор подборки.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Заголовок подборки.
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Список идентификаторов событий, входящих в подборку.
     */
    @CollectionTable(name = "compilation_events", joinColumns = @JoinColumn(name = "compilation_id"))
    @Column(name = "event_id")
    @ElementCollection
    private Collection<Long> events;

    /**
     * Признак, закреплена ли подборка на главной странице сайта.
     */
    @Column(name = "pinned")
    private boolean pinned;
}
