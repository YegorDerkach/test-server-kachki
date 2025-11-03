# Інструкція для хостингу Web Kachki Server

## ✅ Проект готовий до хостингу!

JAR файл знаходиться в: `server/target/web-kachki-server-0.0.1-SNAPSHOT.jar`

## 🚀 Запуск на сервері

### 1. Вимаги
- Java 17 або вище
- MongoDB доступний (вже налаштований)
- Google Cloud Storage credentials (для завантаження файлів)

### 2. Запуск з JAR файлу

```bash
java -jar server/target/web-kachki-server-0.0.1-SNAPSHOT.jar
```

За замовчуванням сервер запуститься на порту **8080**.

### 3. Налаштування змінних оточення (рекомендовано для продакшену)

Для безпеки краще використовувати змінні оточення замість хардкоду в `application.properties`:

```bash
export SERVER_PORT=8080
export MONGODB_URI=mongodb+srv://user:password@host/database
export JWT_SECRET=your-secret-key-here
export GCP_CREDENTIALS_LOCATION=/path/to/credentials.json
# або
export GOOGLE_APPLICATION_CREDENTIALS=/path/to/credentials.json
export GCS_BUCKET_VIDEO=video-kachki
export GCS_BUCKET_PHOTO=photo-kachki
export GCP_PROJECT_ID=web-kachki

java -jar server/target/web-kachki-server-0.0.1-SNAPSHOT.jar
```

### 4. Налаштування GCP Credentials

**Варіант 1 (рекомендовано):** Використовувати змінну оточення `GOOGLE_APPLICATION_CREDENTIALS`
```bash
export GOOGLE_APPLICATION_CREDENTIALS=/path/to/web-kachki-a21e863f3870.json
```

**Варіант 2:** Вказати шлях в `application.properties` або через змінну оточення:
```bash
export GCP_CREDENTIALS_LOCATION=/path/to/web-kachki-a21e863f3870.json
```

### 5. Запуск на іншому порту

```bash
java -jar -DSERVER_PORT=3000 server/target/web-kachki-server-0.0.1-SNAPSHOT.jar
```

або

```bash
export SERVER_PORT=3000
java -jar server/target/web-kachki-server-0.0.1-SNAPSHOT.jar
```

### 6. Запуск як сервіс (systemd - Linux)

Створіть файл `/etc/systemd/system/web-kachki-server.service`:

```ini
[Unit]
Description=Web Kachki Server
After=network.target

[Service]
Type=simple
User=your-user
WorkingDirectory=/path/to/web-kachki-server/server
ExecStart=/usr/bin/java -jar /path/to/web-kachki-server/server/target/web-kachki-server-0.0.1-SNAPSHOT.jar
Restart=always
RestartSec=10

Environment="SERVER_PORT=8080"
Environment="GOOGLE_APPLICATION_CREDENTIALS=/path/to/credentials.json"
Environment="JWT_SECRET=your-secret-key"
Environment="MONGODB_URI=mongodb+srv://user:password@host/database"

[Install]
WantedBy=multi-user.target
```

Запуск:
```bash
sudo systemctl daemon-reload
sudo systemctl enable web-kachki-server
sudo systemctl start web-kachki-server
sudo systemctl status web-kachki-server
```

### 7. Перевірка роботи

Після запуску сервер буде доступний на:
- HTTP: `http://localhost:8080`
- Або на вашому домені/IP, якщо налаштований

## 📝 Примітки

- За замовчуванням сервер використовує порт **8080**
- MongoDB вже налаштований в `application.properties`
- Всі конфігурації можна перевизначити через змінні оточення
- Для продакшену обов'язково змініть `JWT_SECRET` на безпечний ключ

## 🔧 Troubleshooting

1. **Помилка з GCP credentials:**
   - Переконайтеся, що файл credentials існує і доступний
   - Перевірте права доступу до файлу

2. **Помилка з MongoDB:**
   - Перевірте, чи MongoDB доступний з вашого сервера
   - Перевірте URI в `MONGODB_URI`

3. **Порт зайнятий:**
   - Змініть порт через `SERVER_PORT` змінну оточення
   - Або в `application.properties` встановіть `server.port=3000`

