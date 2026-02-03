import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import tailwindcss from "@tailwindcss/vite";

// https://vite.dev/config/
export default defineConfig({
  plugins: [tailwindcss(), vue()],
  server: {
    proxy: {
      // String shorthand: intercepts any request starting with "/api"
      "/api": {
        target: "http://localhost:8080", // Your backend URL
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""), // Removes "/api" before sending to backend
      },
    },
  },
});
