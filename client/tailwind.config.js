/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      /** 폰트 */
      fontFamily: {
        main: ["BMJUA"],
        sub: ["KOTRA_BOLD-Bold"],
        sp: ["GoryeongStrawberry"],
      },
      colors: {
        bg: "#121212",
        "thick-bg": "#080808",
        fg: "#bbb",
        main: {
          50: "#eef2ff",
          100: "#e0e7ff",
          200: "#c7d2fe",
          300: "#a5b4fc",
          400: "#818cf8",
          500: "#6366f1",
          600: "#4f46e5",
          700: "#4338ca",
          800: "#3730a3",
          900: "#312e81",
          950: "#1e1b4b",
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
        swing: {
          "20%": { transform: "rotate(15deg)" },
          "40%": { transform: "rotate(-10deg)" },
          "60%": { transform: "rotate(5deg)" },
          "80%": { transform: "rotate(-5deg)" },
          "100%": { transform: "rotate(0deg)" },
        },
        "fade-in": {
          "0%": { opacity: "0.01" },
          "100%": { opacity: "1" },
        },
        "fade-down": {
          "0%": { opacity: "0.01", transform: "translateY(-10%)" },
          "100%": { opacity: "1", transform: "translateY(0%)" },
        },
        "fade-up": {
          "0%": { opacity: "0.01", transform: "translateY(10%)" },
          "100%": { opacity: "1", transform: "translateY(0%)" },
        },
      },
      animation: {
        "skeleton-gradient": "skeleton-gradient 0.8s linear infinite alternate",
        swing: "swing 2s ease infinite",
        "fade-in": "fade-in 0.6s ease",
        "fade-down": "fade-down 0.6s ease",
        "fade-up": "fade-up 0.8s ease",
      },
    },
  },
  plugins: [],
};
