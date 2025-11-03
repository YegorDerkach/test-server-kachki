#!/bin/bash

# Завантаження змінних оточення з .env файлу
if [ -f .env ]; then
    export $(cat .env | grep -v '^#' | xargs)
    echo "✅ Environment variables loaded from .env"
else
    echo "❌ .env file not found!"
    echo "💡 Copy .env.example to .env and configure it"
    exit 1
fi

# Запуск додатку
cd web-kachki-server/server
echo "🚀 Starting application..."
./mvnw spring-boot:run
