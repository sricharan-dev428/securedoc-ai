## Your Notes for DTOs So Far

After you create this file, write these notes in your `securedoc-notes.md`:
```
## DTOs — What I Learned

WHY DTOs exist:
- Keeps database entity (User.java) separate from API data
- Prevents security risk of user setting their own role
- Controls exactly what comes in and what goes out

Three DTOs for authentication:
1. RegisterRequest  — fields: firstName, lastName, email, password
2. LoginRequest     — fields: email, password  
3. AuthResponse     — fields: token, email, firstName, role (built next)

Key validation decisions:
- lastName has no @NotBlank because it's optional
- LoginRequest password has no @Size(min=8) — security reason
  (don't give attackers info about our password rules)
- @Email checks FORMAT only, not if inbox actually exists

Lombok on DTOs:
@Getter only (no @Setter — data shouldn't change after Jackson fills it)
@Builder, @NoArgsConstructor, @AllArgsConstructor always together


AuthResponse — what we send back after register or login

Fields: token, email, firstName, role

Why no validation annotations:
- Validation is for incoming data (requests)
- AuthResponse goes OUT — we build it ourselves
- Nothing to validate when you control the data

Why same response for both register and login:
- Client needs a token after both actions
- No reason to create two different response classes
- They need the same information either way

Key rule:
NEVER include password in any response DTO
NEVER include internal fields (id, enabled, timestamps)
Only send what the client needs to function

## Current Project Structure
```
src/main/java/com/securedoc/ai/
├── auth/
│   ├── entity/
│   │   ├── Role.java                  ✅
│   │   └── User.java                  ✅
│   ├── repository/
│   │   └── UserRepository.java        ✅
│   ├── dto/
│   │   ├── RegisterRequest.java       ✅
│   │   ├── LoginRequest.java          ✅
│   │   └── AuthResponse.java          ✅
│   ├── controller/                    (empty)
│   └── service/                       (empty)
├── common/
│   └── security/                      (empty)
└── SecuredocAiApplication.java



JWT has 3 parts separated by dots:
Header   → algorithm used (HS256)
Payload  → data stored (email, issued time, expiry time)
NOT encrypted — anyone can read it — never put passwords here
Signature → mathematical proof nobody tampered with the token

JwtService jobs:
1. generateToken(user) → creates JWT with email + expiry
2. extractEmail(token) → reads email from token payload
3. isTokenValid(token, user) → checks signature + expiry + email match

Secret key lives in application.yml — never hardcoded in Java
@Value annotation reads config values into Java fields

Key security rule:
If someone gets your secret key → they can forge tokens
Secret key must NEVER go to GitHub in production




## AuthService

Two methods:
register → check email exists, hash password, save user,
generate token, return AuthResponse
login    → verify credentials via authenticationManager,
load user, generate token, return AuthResponse

Key decisions:
- passwordEncoder.encode() not new BCryptPasswordEncoder()
  (use the Spring managed bean, never create it manually)
- role is always set to Role.USER in register
  (user never decides their own role — security)
- @Transactional on register (writes to DB)
- NO @Transactional on login (only reads from DB)
- orElseThrow() always include a message for debugging

authenticationManager.authenticate():
- if credentials wrong → throws BadCredentialsException automatically
- if correct → continues normally
- we don't check the return value
