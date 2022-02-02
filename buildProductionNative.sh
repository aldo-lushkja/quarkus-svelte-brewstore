# Build frontend
cd frontend
npm install
npm audit fix
npm run build
cp -rvf public/* ../backend/src/main/resources/META-INF/resources/
cd ..


# Run app
./backend/target/backend-*runner