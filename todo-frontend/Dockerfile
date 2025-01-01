# Вибір базового образу
FROM node:18

# Встановлення робочої директорії
WORKDIR /app

# Копіювання package.json та package-lock.json
COPY package*.json ./

# Встановлення залежностей
RUN npm install

# Копіювання всіх файлів додатку в контейнер
COPY . .

# Створення проєкту в production режимі
RUN npm run build

# Налаштування порту, на якому буде працювати додаток
EXPOSE 3000

# Команда для запуску додатку
CMD ["npm", "start"]
