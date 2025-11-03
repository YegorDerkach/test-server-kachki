# Multi-stage build для оптимізації розміру образу

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Копіюємо pom.xml та завантажуємо залежності (кешування)
COPY web-kachki-server/server/pom.xml .
RUN mvn dependency:go-offline -B

# Копіюємо вихідний код
COPY web-kachki-server/server/src ./src

# Білд проекту
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Копіюємо JAR файл з build stage
COPY --from=build /app/target/web-kachki-server-0.0.1-SNAPSHOT.jar app.jar

# Створюємо не-root користувача для безпеки
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Expose port
EXPOSE 8080

# Health check (опціонально - Render має власний health check)
# Якщо потрібен, розкоментуйте:
# HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
#   CMD wget --no-verbose --tries=1 --spider http://localhost:8080/ || exit 1

# Запуск додатку
ENTRYPOINT ["java", "-jar", "app.jar"]

