# Coffee API

## Конфигурация проекта
1. SDK - corretto-17
2. Spring Boot 3.1.5
3. Spring Data JPA
4. Spring AOP
5. Lombok
6. Spring Web
7. PostgreSQL 13
8. OpenAPI WebMVC-UI

## Установка и запуск проекта
1. Установите [Docker](https://www.docker.com).
2. Создайте образ приложения Spring `spring-boot:build-image` 
или командой в терминале `mvn spring-boot:build-image`.
3. Запустите команду из директории проекта `docker-compose up`.
4. Откройте документацию [SwaggerUI](http://localhost:8081/swagger-ui/index.html).

## Возможные ошибки
* Контейнер postgres может не запуститься, если на VM уже запущена PostgreSQL.