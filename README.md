# 🍻 Breweries

## ⚒️ Uses

Here are the technologies used in this project:

### Frontend
1. [Open Brewery DB API](https://www.openbrewerydb.org/)
2. [Svelte](https://svelte.dev/)
3. [SvelteKit](https://kit.svelte.dev/)
4. [Tailwind CSS](https://tailwindcss.com/)
5. [TypeScript](https://www.typescriptlang.org/)
6. [Vite](https://vitejs.dev/)

### Backend
- [Quarkus](https://quarkus.io/)
- [Java](https://www.java.com/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [PostgreSQL](https://www.postgresql.org/)

## ⚙️ Setup

### Frontend
1. Clone the project
2. Go to the `frontend` directory
3. Run `npm install`
4. Run `npm run dev`

### Backend
1. Clone the project
2. Go to the `backend` directory
3. Run `./mvnw compile quarkus:dev`
4. Go to `http://localhost:8080/swagger-ui/` to see the API documentation
5. Go to `http://localhost:8080/h2-console` to see the database
6. Use the following credentials to login:
   - Driver Class: `org.h2.Driver`
   - JDBC URL: `jdbc:h2:mem:breweries`
   - User Name: `sa`
   - Password: `password`

### 📒 Description
This project is a simple webapp that uses the Open Brewery DB API to fetch breweries and display them in a list. The user can search for breweries by name, city, state, and type. The user can also click on a brewery to see more details about it.

### 📅 TODO
- Secure webapp (https, Basic Auth, JWT, etc)
- Create a docker-compose file to run the app locally
- Add a CI/CD pipeline
- Add a database to store the breweries
- Add a map to show the location of the breweries
- Add a way to add new breweries
- Add a way to edit breweries
- Add a way to delete breweries

#### 🫂 How to contribute
1. Fork the project
2. Open a pull request

#### 🌟 Authors
- [Aldo Lushkja](mailto:aldo.lushkja@outlook.it)