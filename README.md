# ResQ-Learn — Backend

Built for Smart India Hackathon 2025 (SIH25008). A disaster preparedness education platform for schools, teachers, students and individuals.

## Tech Stack
- Java 17, Spring Boot 3.2.2
- Spring Security + JWT Authentication
- MySQL 8.0, JPA / Hibernate
- Maven, Swagger

## User Roles
- SCHOOL — Manages teachers and students, gets unique codes
- TEACHER — Enables/disables disaster modules for students
- STUDENT — Learns through Education → Assessment → Drill
- INDIVIDUAL — Free access to all modules
- SUPER ADMIN — Full platform user management

## 11 Disaster Modules
Fire · CPR · First Aid Kit · Flood · Earthquake · Building Structure Collapse · Hurricane · Tsunami · Lift Accidents · Stampede · Heat Syncope

## Key Features
- JWT based authentication and role based access control
- Paragraph unlock education system with progress saved in database
- Assessment penalty scoring system (25→20→15→10)
- Leaderboard with tiebreaker logic
- Virtual drill with personal best score tracking
- Teacher module toggle per school
- Super Admin dashboard with delete controls

## Running Locally
1. Clone the repo
2. Create a .env file with your MySQL credentials
3. Run ./mvnw spring-boot:run
4. API docs at http://localhost:8080/swagger-ui.html

## Frontend Repository
https://github.com/Basith80/resqlearn-frontend

## Team
Built by a team of 4 from Dhaanish Ahmed Institute of Technology, Coimbatore.
