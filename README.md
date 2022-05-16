# flight-api-fa

## Requirements

- `Java`
- `PostgreSQL`/ `MariaDB` / any RDBMS

## Setup

- `git clone` this repository
- Set values for environment variables  `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` with respective values.
- Execute `mvn spring-boot:run` from the root of the project

## Seed data & Testing

Sample users - `fa-user0@example.com, fa-user1@example.com,`...\
API endpoint is at `localhost:8080/v1/api/`

- To login - `localhost:8080/v1/api/auth/signin`
- To create booking with flights - `localhost:8080/v1/api/1/bookings`
- To get flight details - `localhost:8080/v1/api/passengers?flightNumber=ZOD813&departureDate=2022/06/23`
- To get flight details for specific passenger - `localhost:8080/v1/api/passengers/{passengerID}`
