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
