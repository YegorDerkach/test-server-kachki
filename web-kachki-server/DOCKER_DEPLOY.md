# 🐳 Docker Deploy на Render.com

## 📋 Передумови

Проект налаштований для деплою через Docker на Render.com.

## 🚀 Швидкий старт

### Крок 1: Створення Web Service на Render

1. Перейдіть на [Render.com](https://render.com) та увійдіть в акаунт
2. Натисніть **"New +"** → **"Web Service"**
3. Підключіть ваш GitHub репозиторій: `YegorDerkach/test-server-kachki`

### Крок 2: Налаштування Docker

**Name:** `web-kachki-server` (або будь-яка назва)

**Environment:** **`Docker`**

**Dockerfile Path:** `web-kachki-server/server/Dockerfile`

**Docker Context:** `web-kachki-server/server`

Render автоматично використає `render.yaml` з репозиторію, але перевірте налаштування:

### Крок 3: Налаштування змінних оточення

Перейдіть в секцію **"Environment"** та додайте наступні змінні:

#### Обов'язкові змінні:

1. **PORT** (Render автоматично встановлює, але можна додати явно)
   ```
   8080
   ```
   Або просто залиште порожнім - Render автоматично призначить порт

2. **MONGODB_URI**
   ```
   mongodb+srv://guzzzlik:pisunf6@database.lkulppx.mongodb.net/server?retryWrites=true&w=majority&appName=database
   ```
   (або ваш MongoDB connection string)

3. **JWT_SECRET**
   ```
   ваш-секретний-ключ-для-jwt
   ```
   (використовуйте сильний випадковий ключ)

4. **GOOGLE_APPLICATION_CREDENTIALS** (для GCP)
   
   **Варіант 1:** Завантажте credentials файл через Render Secret Files:
   - Перейдіть в **"Environment"** → **"Secret Files"**
   - Додайте файл `gcp-credentials.json` з вмістом вашого GCP credentials
   - Встановіть змінну: `GOOGLE_APPLICATION_CREDENTIALS=/opt/render/project/src/gcp-credentials.json`
   
   **Варіант 2:** Використовуйте змінну оточення `GCP_CREDENTIALS_LOCATION`

#### Додаткові змінні:

5. **GCS_BUCKET_VIDEO**
   ```
   video-kachki
   ```

6. **GCS_BUCKET_PHOTO**
   ```
   photo-kachki
   ```

7. **GCP_PROJECT_ID**
   ```
   web-kachki
   ```

### Крок 4: Deploy

1. Натисніть **"Create Web Service"**
2. Render почне білд Docker образу
3. Після успішного білду сервіс автоматично запуститься

## 🔧 Налаштування через render.yaml

Якщо ви хочете використовувати `render.yaml` (автоматично):

```yaml
services:
  - type: web
    name: web-kachki-server
    env: docker
    dockerfilePath: ./web-kachki-server/server/Dockerfile
    dockerContext: ./web-kachki-server/server
```

Render автоматично використає ці налаштування при підключенні репозиторію.

## 📝 Dockerfile структура

Проект використовує multi-stage build:

1. **Build Stage:** Maven білд проекту
2. **Runtime Stage:** JRE-only образ для мінімального розміру

## 🧪 Локальне тестування Docker

Перед деплоєм можна протестувати локально:

```bash
# Білд образу
cd web-kachki-server/server
docker build -t web-kachki-server .

# Запуск контейнера
docker run -p 8080:8080 \
  -e MONGODB_URI="your-mongodb-uri" \
  -e JWT_SECRET="your-secret" \
  -e GOOGLE_APPLICATION_CREDENTIALS="/path/to/credentials.json" \
  web-kachki-server
```

## 🔍 Troubleshooting

### Помилка: "Docker build failed"

1. Перевірте логи білду в Render Dashboard
2. Переконайтеся, що Dockerfile знаходиться в правильному місці
3. Перевірте, що `pom.xml` доступний

### Помилка: "MongoDB connection failed"

1. Перевірте MongoDB URI
2. Перевірте Network Access в MongoDB Atlas (додайте `0.0.0.0/0`)
3. Перевірте, чи MongoDB доступний з інтернету

### Помилка: "GCP credentials not found"

1. Перевірте, чи файл credentials завантажено через Secret Files
2. Перевірте шлях в змінній `GOOGLE_APPLICATION_CREDENTIALS`
3. Перевірте права доступу до файлу

### Помилка: "Port already in use"

1. Render автоматично призначає порт через змінну `$PORT`
2. Переконайтеся, що `application.properties` використовує `${PORT:8080}`
3. Не хардкодьте порт в коді

## 📊 Моніторинг

- **Health Check:** Dockerfile включає health check на `/actuator/health`
- **Логи:** Перевіряйте логи в Render Dashboard → Logs
- **Metrics:** Render автоматично збирає метрики

## 🔐 Безпека

- Dockerfile використовує non-root користувача
- Credentials не закомічені в git
- Використовуйте Secret Files для чутливих даних

## 📚 Додаткові ресурси

- [Render Docker Documentation](https://render.com/docs/docker)
- [Render Environment Variables](https://render.com/docs/environment-variables)
- [Render Secret Files](https://render.com/docs/secret-files)

