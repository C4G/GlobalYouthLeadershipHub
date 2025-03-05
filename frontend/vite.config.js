import { defineConfig, loadEnv } from "vite";
import react from "@vitejs/plugin-react";
import path from 'path'

// https://vite.dev/config/
export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '')

  return {
    server: {
      port: 3000
    },
    preview: {
      port: 3000
    },
    plugins: [
      react()
    ],
    resolve: {
      alias: {
        "@": path.resolve(__dirname, './src')
      }
    },
    define: {
      'process.env': env,
    },
    envPrefix: "REACT_APP_",
  }
})