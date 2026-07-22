# === Stage 1: Build the application ===
# Name this stage "dist" so the NGINX stage can reference it
FROM node:20-alpine AS dist
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build  # This creates the /app/build directory

# === Stage 2: Serve with NGINX ===
FROM nginx:alpine
# Docker now correctly recognizes "dist" from Stage 1
COPY --from=dist /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
