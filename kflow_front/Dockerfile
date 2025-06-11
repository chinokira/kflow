# Build stage
FROM node:22 AS build
WORKDIR /app

# Installation des dépendances
COPY package*.json ./
RUN npm ci

# Copie du code source
COPY . .

# Build de l'application
RUN npm run build

# Production stage
FROM nginx:alpine
COPY --from=build /app/dist/kflow /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
