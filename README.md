# 🎮 Gamehok Tournament Backend System

A production-ready backend system for managing gaming tournaments with support for 1v1 to 5v5 bracket-based competitions.

---

## 🛠 Tech Stack
- **Java 17** + **Spring Boot 3.2**
- **PostgreSQL** – Main database
- **Redis** – Caching
- **MongoDB** – Match logs
- **Docker** – Containerization

---

## 🚀 How to Run

### Option 1: Docker (Recommended)
```bash
# Build the project
mvn clean package -DskipTests

# Run everything
docker-compose up --build
```

### Option 2: Locally
```bash
# Start PostgreSQL, Redis, MongoDB locally first
# Then run:
mvn spring-boot:run
```

App runs at: `http://localhost:8080`

---

## 📡 API Endpoints

### Users
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/users` | Create user |
| GET | `/api/users` | List all users |

### Teams
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/teams` | Create team |
| GET | `/api/teams` | List all teams |
| GET | `/api/teams/{id}` | Get team by ID |

### Tournaments
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/tournaments` | Create tournament |
| GET | `/api/tournaments` | List all tournaments |
| GET | `/api/tournaments/{id}` | Get by ID |
| POST | `/api/tournaments/{id}/register/{teamId}` | Register team |
| POST | `/api/tournaments/{id}/start` | Start + generate bracket |
| GET | `/api/tournaments/{id}/bracket` | View bracket |

### Matches
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/matches/{id}` | Get match |
| GET | `/api/matches/tournament/{id}` | All matches in tournament |
| POST | `/api/matches/{id}/result` | Submit match result |

---

## 🏆 Tournament Flow

```
1. Create Users
2. Create Teams (assign users)
3. Create Tournament (type: ONE_VS_ONE / TWO_VS_TWO / FIVE_VS_FIVE)
4. Register Teams
5. Start Tournament → Bracket auto-generated
6. Submit Match Results → Next round auto-advances
7. Final winner declared automatically
```

---

## 🗃️ Database Schema

```
users          → id, name, email
teams          → id, name, team_size
team_members   → team_id, user_id
tournaments    → id, name, type, status, max_teams
participants   → id, tournament_id, team_id, eliminated
matches        → id, tournament_id, team1_id, team2_id, winner_id, round, status
```

---

## 📦 Tournament Types Supported
- `ONE_VS_ONE` – 1v1
- `TWO_VS_TWO` – 2v2
- `THREE_VS_THREE` – 3v3
- `FOUR_VS_FOUR` – 4v4
- `FIVE_VS_FIVE` – 5v5

---

## 👨‍💻 Author
Built as part of Gamehok Backend Developer Assignment.