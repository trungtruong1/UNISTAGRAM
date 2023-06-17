git clone https://github.com/trungtruong1/UNISTAGRAM.git 
cd UNISTAGRAM
git checkout milestone3

mongod --fork --logpath /var/log/mongodb.log
mongosh admin --eval "db.createUser({ user: 'admin', pwd: 'password', roles: ['userAdminAnyDatabase'] })"

cd src/main/frontend/
npm i
npm run build

cp -r ./build/. ../resources/static/

cd ../../../
mvn clean package