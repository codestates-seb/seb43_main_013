/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        bg: "#FBFBFB",
        main: {
          50: "#fff1f2",
          100: "#ffe4e6",
          200: "#fecdd3",
          300: "#fda4af",
          400: "#fb7185",
          500: "#f43f5e",
          600: "#e11d48",
          700: "#be123c",
          800: "#9f1239",
          900: "#881337",
          950: "#4c0519",
        },
        sub: {
          50: "#f9fafb",
          100: "#f3f4f6",
          200: "#e5e7eb",
          300: "#d1d5db",
          400: "#9ca3af",
          500: "#6b7280",
          600: "#4b5563",
          700: "#374151",
          800: "#1f2937",
          900: "#111827",
          950: "#030712",
        },
      },
      keyframes: {
        "skeleton-gradient": {
          "0%": { "background-color": "rgba(165, 165, 165, 0.2)" },
          "50%": { "background-color": "rgba(165, 165, 165, 0.4)" },
          "100%": { "background-color": "rgba(165, 165, 165, 0.6)" },
        },
      },
      animation: {
        "skeleton-gradient": "skeleton-gradient 0.8s linear infinite alternate",
      },
    },
  },
  plugins: [],
};
