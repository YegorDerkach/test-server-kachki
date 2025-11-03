# 🔧 Виправлення помилки Render: "Using Node.js version"

## Проблема

Render автоматично визначив проект як Node.js замість Java, тому білд падає з помилкою:
```
==> Using Node.js version 22.16.0 (default)
==> Running build command 'yarn cd web-kachki-server/server...'
error Couldn't find a package.json file
```

## ✅ Рішення

### Крок 1: Відкрийте налаштування сервісу в Render

1. Перейдіть на ваш Render Dashboard
2. Виберіть ваш сервіс `web-kachki-server`
3. Натисніть **"Settings"** (Налаштування)

### Крок 2: Змініть Environment на Java

1. Знайдіть секцію **"Environment"**
2. Поле **"Environment"** має бути встановлено на **`Java`** (НЕ Node.js!)
3. Якщо там вказано `Node` або `Node.js` - змініть на **`Java`**
4. Збережіть зміни

### Крок 3: Перевірте Build Command

**Build Command має бути:**
```bash
cd web-kachki-server/server && chmod +x mvnw && ./mvnw clean package -DskipTests
```

**Start Command має бути:**
```bash
cd web-kachki-server/server && java -jar target/web-kachki-server-0.0.1-SNAPSHOT.jar
```

### Крок 4: Альтернатива - Використовуйте Root Directory

Якщо команди з `cd` не працюють, спробуйте встановити **Root Directory**:

1. В Settings знайдіть **"Root Directory"**
2. Встановіть: `web-kachki-server/server`
3. Тоді Build Command буде просто:
   ```bash
   chmod +x mvnw && ./mvnw clean package -DskipTests
   ```
4. А Start Command:
   ```bash
   java -jar target/web-kachki-server-0.0.1-SNAPSHOT.jar
   ```

### Крок 5: Перезапустіть Deploy

1. Натисніть **"Manual Deploy"** → **"Deploy latest commit"**
2. Або просто зробіть новий commit і Render автоматично перезапустить deploy

## 📸 Скріншот правильних налаштувань

```
Environment: Java ✅ (НЕ Node.js!)
Build Command: cd web-kachki-server/server && chmod +x mvnw && ./mvnw clean package -DskipTests
Start Command: cd web-kachki-server/server && java -jar target/web-kachki-server-0.0.1-SNAPSHOT.jar
```

## ⚠️ Якщо проблема залишається

1. Перевірте, що в репозиторії є файл `pom.xml` (він є в `web-kachki-server/server/pom.xml`)
2. Перевірте, що `render.yaml` існує в корені репозиторію (він є)
3. Спробуйте видалити сервіс і створити його заново, явно вказавши Environment = Java під час створення

## 📝 Важливо

- **Environment** має бути **Java**, а не Node.js
- Render може автоматично визначити тип проекту неправильно
- Завжди перевіряйте Environment перед першим deploy

