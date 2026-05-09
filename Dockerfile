# ─────────────────────────────────────────
# Dockerfile
# ─────────────────────────────────────────
# FROM openjdk:17-slim
# WORKDIR /app
# COPY target/tournament-1.0.0.jar app.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "app.jar"]


# ─────────────────────────────────────────
# docker-compose.yml
# ─────────────────────────────────────────
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/tournament_db
      SPRING_DATASOURCE_USERNAME: admin
      SPRING_DATASOURCE_PASSWORD: password
      SPRING_DATA_REDIS_HOST: redis
      SPRING_DATA_MONGODB_URI: mongodb://mongo:27017/tournament_logs
    depends_on:
      - postgres
      - redis
      - mongo
    restart: on-failure

  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: tournament_db
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

  redis:
    image: redis:alpine
    ports:
      - "6379:6379"

  mongo:
    image: mongo:6
    ports:
      - "27017:27017"
    volumes:
      - mongodata:/data/db

volumes:
  pgdata:
  mongodata: