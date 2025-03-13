# Stage 1: Build
FROM node:22-slim AS build

WORKDIR /app

# Copy only package.json and package-lock.json first (to optimize caching)
COPY ../frontend/package.json ./

# Install dependencies
RUN npm install

# Copy only necessary files (avoid unnecessary files)
COPY ../frontend/public ./public
COPY ../frontend/src ./src
COPY ../frontend/vite.config.js ./
COPY ../frontend/index.html ./
# TODO - red flag, but i want to test the api on the develop first
COPY ../frontend/.env.production ./ 

# Build the frontend
RUN npm run build

# Stage 2: Serve (using Node.js `serve` package)
FROM node:22-slim

WORKDIR /app

# Install serve globally to serve static files
RUN npm install -g serve

# Copy built frontend files from the build stage
COPY --from=build /app/dist ./dist

# Expose port 3000
EXPOSE 3000

# Serve the built frontend
CMD ["serve", "-s", "dist", "-l", "3000"]
