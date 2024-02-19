# Billing system for Kindergarden

Simple billing system made as job interview assigment.
The application features two endpoints for calculating monthly parent billing and monthly school billing. These calculations are based on hour price and the duration children spend at school.
Additionally, there is a period of no-fee time during which children can stay at no cost.

The project uses **DDD (Domain-Driven Design)** and **Hexagonal Architecture** to ensure a clear separation of concerns. This helps with decoupling domain logic from external dependencies.

**Technologie used**
* Java 17
* Spring Boot 3.2
* Docker
* Postgres 12
* Liquibase
* Hibernate
* Spock
* Testcontainers

## How to run
### Prerequisite
* Java 17
* Docker

1. Inside project run following command to start postgres service `docker-compose up -d`
2. You can run Spring Boot app using your IDE
3. Before manual testing you can fill database with test data using below sql script:
``` sql
INSERT INTO parent(firstname, lastname)
VALUES ('John', 'Kowalski'),
       ('Janusz', 'Smith');

INSERT INTO school(name, hour_price)
VALUES ('School 1', 250);

INSERT INTO child(firstname, lastname, parent_id, school_id)
VALUES ('Marek', 'Kowalski', 1, 1),
       ('Elżbieta', 'Kowalska', 1, 1),
       ('Alice', 'Smith', 2, 1);


INSERT INTO attendance(child_id, entry_date, exit_date) VALUES
(1, to_timestamp('01.02.2024 07:00', 'DD.MM.YYYY HH24:MI'), to_timestamp('01.02.2024 12:00', 'DD.MM.YYYY HH24:MI')),
(1, to_timestamp('02.02.2024 07:00', 'DD.MM.YYYY HH24:MI'), to_timestamp('02.02.2024 14:00', 'DD.MM.YYYY HH24:MI')),
(1, to_timestamp('03.02.2024 06:59', 'DD.MM.YYYY HH24:MI'), to_timestamp('03.02.2024 12:01', 'DD.MM.YYYY HH24:MI')),

(2, to_timestamp('01.02.2024 07:00', 'DD.MM.YYYY HH24:MI'), to_timestamp('01.02.2024 12:00', 'DD.MM.YYYY HH24:MI')),
(2, to_timestamp('02.02.2024 07:00', 'DD.MM.YYYY HH24:MI'), to_timestamp('02.02.2024 14:00', 'DD.MM.YYYY HH24:MI')),

(3, to_timestamp('01.02.2024 07:30', 'DD.MM.YYYY HH24:MI'), to_timestamp('01.02.2024 12:00', 'DD.MM.YYYY HH24:MI')),
(3, to_timestamp('02.02.2024 07:00', 'DD.MM.YYYY HH24:MI'), to_timestamp('02.02.2024 12:59', 'DD.MM.YYYY HH24:MI')),
(3, to_timestamp('03.02.2024 06:30', 'DD.MM.YYYY HH24:MI'), to_timestamp('03.02.2024 12:30', 'DD.MM.YYYY HH24:MI'));
```

## Endpoint
**Monthly parent billing**

` curl "localhost:8080/v1/billing/parent?id=1&month=2&year=2024" `

Example response
``` json
{
  "startDate": "2024-02-01T00:00:00Z",
  "endDate": "2024-03-01T00:00:00Z",
  "billingSubject": {
    "id": 1,
    "firstname": "John",
    "lastname": "Kowalski",
    "totalCostInCents": 1500,
    "children": [
      {
        "id": 1,
        "firstname": "Marek",
        "lastname": "Kowalski",
        "totalCostInCents": 1000,
        "totalSpendHours": 19,
        "payedHours": 4
      },
      {
        "id": 2,
        "firstname": "Elżbieta",
        "lastname": "Kowalska",
        "totalCostInCents": 500,
        "totalSpendHours": 12,
        "payedHours": 2
      }
    ]
  }
}
```

**Monthly school billing**

` curl "localhost:8080/v1/billing/school?id=1&month=2&year=2024" `

Example response
``` json
{
  "startDate": "2024-02-01T00:00:00Z",
  "endDate": "2024-03-01T00:00:00Z",
  "billingSubject": {
    "id": 1,
    "name": "SchoolOfRock",
    "totalCostInCents": 2250,
    "parents": [
      {
        "id": 1,
        "firstname": "John",
        "lastname": "Kowalski",
        "totalCostInCents": 1500,
        "children": [
          {
            "id": 1,
            "firstname": "Marek",
            "lastname": "Kowalski",
            "totalCostInCents": 1000,
            "totalSpendHours": 19,
            "payedHours": 4
          },
          {
            "id": 2,
            "firstname": "Elżbieta",
            "lastname": "Kowalska",
            "totalCostInCents": 500,
            "totalSpendHours": 12,
            "payedHours": 2
          }
        ]
      },
      {
        "id": 2,
        "firstname": "Janusz",
        "lastname": "Smith",
        "totalCostInCents": 750,
        "children": [
          {
            "id": 3,
            "firstname": "Alice",
            "lastname": "Smith",
            "totalCostInCents": 750,
            "totalSpendHours": 18,
            "payedHours": 3
          }
        ]
      }
    ]
  }
}
```
