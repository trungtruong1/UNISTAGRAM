git clone https://github.com/trungtruong1/UNISTAGRAM.git 
cd UNISTAGRAM

mongoimport --db=cse364 --collection=user --authenticationDatabase admin --username admin --password password --type=csv --file=data/users.csv --fields=user_id.int32\(\),gender.string\(\),age.int32\(\),occupation.int32\(\),zip_code.string\(\) --columnsHaveTypes 
mongoimport --db=cse364 --collection=rating --authenticationDatabase admin --username admin --password password --type=csv --file=data/ratings.csv --fields=user_id.int32\(\),movie_id.int32\(\),rating.int32\(\),timestamp.int32\(\) --columnsHaveTypes 
mongoimport --db=cse364 --collection=movie --authenticationDatabase admin --username admin --password password --type=csv --file=data/movies.csv --fields=movie_id.int32\(\),movie_name.string\(\),genre.string\(\) --columnsHaveTypes 

mvn package
java -jar ./target/cse364-project-1.0-SNAPSHOT.jar