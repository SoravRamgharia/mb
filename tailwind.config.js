/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/main/resources/**/*.{html,js}"], 
  theme: {
    extend: {
      fontFamily: {
        greatVibes: ['Great Vibes', 'cursive'],
        montserrat: ['Montserrat', 'sans-serif'],
      },
    },
  },
  plugins: [],
  darkMode: "selector",
}