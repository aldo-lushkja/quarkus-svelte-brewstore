# Build frontend
cd frontend
npm install
npm audit fix
npm run build
cp -rvf public/* ../backend/src/main/resources/META-INF/resources/
cd ..

# Build backend
mvn clean package -f backend/pom.xml -Pnative -DskipTests

# Run app
./backend/target/backend-*runner