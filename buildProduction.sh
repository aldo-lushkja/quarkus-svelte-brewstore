# Build frontend
cd frontend
npm install
npm audit fix
npm run build
cp -rvf public/* ../backend/src/main/resources/META-INF/resources/
cd ..

# Build backend
mvn clean package -f backend/pom.xml -Dquarkus.package.type=uber-jar -DskipTests

# Run app
java -jar backend/target/backend-*runner.jar