git clone https://github.com/trungtruong1/UNISTAGRAM.git 
cd UNISTAGRAM
mongod --fork --logpath /var/log/mongodb.log
mongosh admin --eval "db.createUser({ user: 'admin', pwd: 'password', roles: ['userAdminAnyDatabase'] })"

mongoimport --db=cse364 --collection=user --authenticationDatabase admin --username admin --password password --file data/users.json  --jsonArray
mongoimport --db=cse364 --collection=conversation --authenticationDatabase admin --username admin --password password --file data/conversations.json  --jsonArray
mongoimport --db=cse364 --collection=meme --authenticationDatabase admin --username admin --password password --file data/memes.json  --jsonArray
mongoimport --db=cse364 --collection=message --authenticationDatabase admin --username admin --password password --file data/messages.json  --jsonArray
mongoimport --db=cse364 --collection=reaction --authenticationDatabase admin --username admin --password password --file data/reactions.json  --jsonArray

mvn package
java -jar ./target/cse364-project-1.0-SNAPSHOT.jar