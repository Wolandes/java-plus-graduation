# Проект Explore With Me

Он позволяет многим пользователям делиться информацией об интересных событиях и находить компанию для них.

Проект сделан на основе микросервисной архитектуры и состоит из следующих модулей и сервисов

## Модуль `core`

Модуль содержит сервисы, отвечающие за бизнес-логику приложения:

1. **category-service** - управление категориями
2. **compilation-service** - управление подборками событий
3. **event-service** - управление событиями
4. **interaction-api** - содержит в себе общие DTO, типы ошибок, API клиентов для межсервисного взаимодействия
5. **request-service** - управление заявками на участие в событиях
6. **user-service** - управление пользователями

## Модуль `infra`

Модуль содержит сервисы, которая обеспечивает работу приложения

1. **config-server** — централизованное хранение конфигураций для всех сервисов. Конфигурации находятся в пакете
   `resources` модуля `config-server`
2. **gateway** — API Gateway, который обеспечивает единую точку входа для всех запросов к сервисам
3. **discovery-server** — сервис Discovery (Eureka), который регистрирует все микросервисы и обеспечивает их обнаружение

## Модуль `stats`

Модуль содержит информацию о статистике просмотров событий:

1. **stats-client** — обеспечивает взаимодействие с сервисами модуля `core` при помощи использования `RestClient` и
   `DiscoveryClient`
2. **stats-dto** — модуль для DTO
3. **collector** — сервис для приема сообщений о действиях пользователей, используя gRPC
4. **aggregator** — сервис для расчета сходства мероприятий
5. **analyzer** — сервис для обработки запросов по gRPC и выдачи рекомендаций

## Спецификации внешнего API

Спецификации внешнего API можно найти по следующим ссылкам:

1. [Спецификация основного сервиса](https://github.com/Wolandes/java-plus-graduation/blob/main/ewm-main-service-spec.json)
2. [Спецификация сервиса статистики](https://github.com/Wolandes/java-plus-graduation/blob/main/ewm-stats-service-spec.json)

## Спецификация внутреннего API

Префиксы внутренних эндпоинтов (роутов): /interaction/{service}
Взаимосвязь сервисов:

| Сервис              | Используемые сервисы                                      |
|---------------------|-----------------------------------------------------------|
| category-service    | event-service                                             |
| compilation-service | ---                                                       |
| event-service       | user-service, category-service, request-service, analyzer |
| request-service     | event-service, user-service, collector                    |
| user-service        | ---                                                       |
| analyzer            | ---                                                       |
| collector           | ---                                                       |
| aggregator          | ---                                                       |

Описание внутренних API:

| Category API                                                   | Описание                                           |
|----------------------------------------------------------------|----------------------------------------------------|
| GET /interaction/categories                                    | Получить коллекцию категорий по их идентификаторам |
| GET /interaction/categories/{categoryId}                       | Получить категорию по её идентификатору            |
| GET /interaction/categories/check/existence/by/id/{categoryId} | Проверить существует ли категория                  |

| Event API                                                          | Описание                                            |
|--------------------------------------------------------------------|-----------------------------------------------------|
| GET /interaction/events                                            | Получить коллекцию событий.                         |
| GET /interaction/events/{eventId}                                  | Получить событие                                    |
| GET /interaction/events/{eventId}/check/existence/by/id/{eventId}  | Проверить существует ли событие                     |
| GET /interaction/events/check/existence/with/category/{categoryId} | Проверить существуют ли события с данной категорией |
| GET /interaction/events/check/publication/{eventId}                | Проверить опубликовано ли событие                   |
| GET /interaction/events/{eventId}/participation/confirm            | Подтвердить участие в событии                       |
| GET /interaction/events/{eventId}/participation/reject             | Отменить участие в событии                          |

| User API                                              | Описание                                    |
|-------------------------------------------------------|---------------------------------------------|
| GET /interaction/users                                | Получить коллекцию пользователей            |
| GET /interaction/users/{userId}                       | Получить пользователя по его идентификатору |
| GET /interaction/users/check/existence/by/id/{userId} | Проверить существует ли пользователь        |

| Request API                                          | Описание                                 |
|------------------------------------------------------|------------------------------------------|
| GET /interaction/request/find-by-requester-and-event | Получить по инициатору запроса и события |
| GET /interaction/request/find-by-events              | Получить события                         |
