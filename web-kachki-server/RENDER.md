# Деплой на Render.com

## 🚀 Швидкий старт

### Крок 1: Створення нового Web Service

1. Перейдіть на [Render.com](https://render.com) та увійдіть в акаунт
2. Натисніть **"New +"** → **"Web Service"**
3. Підключіть ваш GitHub репозиторій: `YegorDerkach/test-server-kachki`

### Крок 2: Налаштування Build & Deploy

**⚠️ ВАЖЛИВО:** Після підключення репозиторію, Render може автоматично визначити проект як Node.js. **Обов'язково змініть Environment на Java!**

**Name:** `web-kachki-server` (або будь-яка назва)

**Environment:** **`Java`** (НЕ Node.js! Якщо бачите Node.js - змініть на Java!)

**Root Directory:** (залиште порожнім або вкажіть `web-kachki-server/server` якщо потрібно)

**Build Command:**
```bash
cd web-kachki-server/server && chmod +x mvnw && ./mvnw clean package -DskipTests
```

**Start Command:**
```bash
cd web-kachki-server/server && java -jar target/web-kachki-server-0.0.1-SNAPSHOT.jar
```

**Якщо використовуєте Root Directory = `web-kachki-server/server`:**
- Build Command: `chmod +x mvnw && ./mvnw clean package -DskipTests`
- Start Command: `java -jar target/web-kachki-server-0.0.1-SNAPSHOT.jar`

### Крок 3: Налаштування змінних оточення (Environment Variables)

Перейдіть в секцію **"Environment"** та додайте наступні змінні:

#### Обов'язкові змінні:

1. **MONGODB_URI**
   ```
   mongodb+srv://guzzzlik:pisunf6@database.lkulppx.mongodb.net/server?retryWrites=true&w=majority&appName=database
   ```
   (або ваш MongoDB connection string)

2. **JWT_SECRET**
   ```
   ваш-секретний-ключ-для-jwt
   ```
   (використовуйте сильний випадковий ключ)

3. **GOOGLE_APPLICATION_CREDENTIALS** (для GCP)
   ```
   /opt/render/project/src/web-kachki-server/server/gcp-credentials.json
   ```
   АБО використовуйте змінну як JSON:
   
   **GCP_CREDENTIALS_JSON** (альтернатива) - вставте весь вміст вашого JSON файлу credentials

#### Додаткові змінні (опціонально):

4. **SERVER_PORT** (за замовчуванням 8080, Render автоматично призначить PORT)
   ```
   $PORT
   ```
   Render автоматично встановлює змінну `$PORT`, яку використовує ваш додаток

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

### Крок 4: Налаштування GCP Credentials

**Варіант 1: Через файл (рекомендовано)**

1. Створіть файл `gcp-credentials.json` в корені проекту (НЕ комітьте його в git!)
2. Додайте в Environment Variables:
   ```
   GOOGLE_APPLICATION_CREDENTIALS=/opt/render/project/src/web-kachki-server/server/gcp-credentials.json
   ```
3. Або завантажте файл через Render Dashboard → Environment → Secret Files

**Варіант 2: Через змінну оточення (якщо файл не працює)**

1. Відкрийте ваш GCP credentials JSON файл
2. Скопіюйте весь вміст
3. Створіть змінну оточення **GCP_CREDENTIALS_JSON** з вмістом JSON
4. Оновіть `GcsConfig.java` для підтримки цього варіанту

### Крок 5: Налаштування порту

Render автоматично встановлює змінну `PORT`. Оновіть `application.properties`:

```properties
server.port=${PORT:8080}
```

Це вже налаштовано в вашому проекті!

### Крок 6: Deploy

1. Натисніть **"Create Web Service"**
2. Render почне білд проекту
3. Після успішного білду сервіс автоматично запуститься

## 📝 Важливі примітки

### Портування

Render автоматично призначає порт через змінну `$PORT`. Ваш проект вже налаштований для використання `SERVER_PORT` або `PORT`.

### MongoDB

Переконайтеся, що ваш MongoDB Atlas дозволяє підключення з IP адрес Render. Додайте `0.0.0.0/0` в Network Access MongoDB Atlas.

### GCP Credentials

- **НЕ комітьте** credentials файл в git!
- Використовуйте Secret Files в Render або Environment Variables
- Переконайтеся, що credentials мають правильні права доступу до GCS buckets

### Логи

Перевіряйте логи в Render Dashboard → Logs для виявлення помилок.

### Health Check

Render автоматично перевіряє health endpoint. Переконайтеся, що ваш додаток має доступний endpoint на `/` або `/health`.

## 🔧 Troubleshooting

### Помилка: "MongoDB connection failed"
- Перевірте MongoDB URI
- Перевірте Network Access в MongoDB Atlas
- Перевірте, чи MongoDB доступний з інтернету

### Помилка: "GCP credentials not found"
- Перевірте шлях до credentials файлу
- Перевірте, чи файл завантажено в Render
- Перевірте змінні оточення

### Помилка: "Build failed"
- Перевірте логи білду
- Переконайтеся, що Java 17 доступний
- Перевірте, чи всі залежності в `pom.xml` правильні

### Помилка: "Port already in use"
- Використовуйте `${PORT}` в application.properties
- Render автоматично призначає порт

## 📚 Додаткові ресурси

- [Render Java Documentation](https://render.com/docs/java)
- [Render Environment Variables](https://render.com/docs/environment-variables)
- [Render Secret Files](https://render.com/docs/secret-files)

