# UNISTAGRAM

[//]: # (## Note for part 2)

[//]: # ()
[//]: # (When testing POST and PUT method, please use the following commands)

[//]: # ()
[//]: # (```bash)

[//]: # (curl -X POST http://localhost:8080/employees -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}')

[//]: # (```)

[//]: # (```bash)

[//]: # (curl -X PUT http://localhost:8080/employees/3 -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}')

[//]: # (```)

[//]: # ()
[//]: # (If you use the original `curl` command given in the handout, you will always recieve `400 - Bad request` due to a typo in the assignment handout.)

---
### Note for Users: Open Endpoints
* [Query user](#query-user) : `GET /users/:id`

* [Insert user](#insert-user) : `POST /users`

* [Update user](#update-user) : `PUT /users/:id`

## Features
* [Chatting](#Chatapp): Saving the messages according to users' conversation.

* [Matching](#Matchapp): Matching 2 users randomly to form a new friendship. The
  Identities of both sides are not disclosed (remain anonymous) to each other throughout the entire
  session.

* [MemeFeed](#Memefeedapp): Provides an active and interactive meme feed so that everyone can share
  memes, or about anything one’s heart may desire (images).

[//]: # (## Open Endpoints)

[//]: # ()
[//]: # (* [Query rating]&#40;#query-rating&#41; : `GET /ratings/:rating`)

[//]: # ()
[//]: # (* [Query user]&#40;#query-user&#41; : `GET /users/:id`)

[//]: # ()
[//]: # (* [Insert user]&#40;#insert-user&#41; : `POST /users`)

[//]: # ()
[//]: # (* [Update user]&#40;#update-user&#41; : `PUT /users/:id`)

## API Documentation

### Chatting
Saving the messages according to users' conversation.

#### Open Endpoints

* [Query Messages In Conversation](#queryMessagesInConversation) : `GET /messages/:id`


* [Send Message To Conversation](#sendMessageToConversation) : `POST /messages/send`


* [Query All Conversations](#queryAllConversations) : `GET /conversations/`


* [Query A Conversation](#queryAConversation) : `GET /conversations/:id`


* [Query A Conversation By User](#queryConversationByUser) : `GET /conversations/users/:id`

### Matching
Matching 2 users randomly to form a new friendship.

#### Open Endpoints

* [Update User to Join Queue](#updateUserToJoinQueue) : `PUT /matching/join_queue/:id`


* [Update User to Out Queue](#updateUserToOutQueue) : `PUT /matching/out_queue/:id`


* [Check User In Queue](#checkUserInQueue) : `GET /matching/check_in_queue/:id`


### MemeFeed
Provides an active and interactive meme feed so that everyone can share
memes, or about anything one’s heart may desire (images).

#### Open Endpoints

* [Query Meme By ID](#queryMemeByID) : `GET /memems/:id`


* [Send Meme](#sendMeme) : `POST /memes`


* [Query Reactions In Meme](#queryReactionsInMeme) : `GET /meme_reactions/:id`


* [Send Reaction](#sendReaction) : `POST /meme_reactions/add`


* [Remove Reaction](#removeReaction) : `DELETE /meme_reactions/del`




[//]: # (* URL:)

[//]: # ()
[//]: # (    `/ratings/:rating`)

[//]: # ()
[//]: # (* Method:)

[//]: # ()
[//]: # (    `GET`)

[//]: # ()
[//]: # (* URL Params:)

[//]: # ()
[//]: # (    **Required**:)

[//]: # (    - `rating=[double]`)

[//]: # ()
[//]: # (* Data Params:)

[//]: # ()
[//]: # (    None)

[//]: # ()
[//]: # (* Success Response:)

[//]: # ()
[//]: # (    - Code: 200)

[//]: # ()
[//]: # (        Content: )

[//]: # (`[)

[//]: # ({)

[//]: # (  "movie_name" : "Toy Story &#40;1995&#41;",)

[//]: # (  "genre": "Animation|Children's|Comedy")

[//]: # (},)

[//]: # ({)

[//]: # (    "movie_name": "xxx",)

[//]: # (    "genre": "xxx",)

[//]: # (},)

[//]: # (...)

[//]: # (])

[//]: # (`)

[//]: # ()
[//]: # (* Error Response:)

[//]: # ()
[//]: # (    - Code: 406 Not Acceptable)

[//]: # ()
[//]: # (        Content: `"Parameter is not in the range of [1, 5]!"` or `"Parameter is not a number!"`)

[//]: # ()
[//]: # (* Sample Call:)

[//]: # ()
[//]: # (Returns a list of movies whose average ratings are greater or equal to 4.)

[//]: # (```bash)

[//]: # (curl -X GET http://localhost:8080/ratings/4)

[//]: # (```)

[//]: # ()
[//]: # (### Query user)

[//]: # (Returns a user object with a given user ID.)

[//]: # ()
[//]: # (* URL:)

[//]: # ()
[//]: # (    `/users/:id`)

[//]: # ()
[//]: # (* Method:)

[//]: # ()
[//]: # (    `GET`)

[//]: # ()
[//]: # (* URL Params:)

[//]: # ()
[//]: # (    **Required**:)

[//]: # (    - `id=[integer]`)

[//]: # ()
[//]: # (* Data Params:)

[//]: # ()
[//]: # (    None)

[//]: # ()
[//]: # (* Success Response:)

[//]: # ()
[//]: # (    - Code: 200)

[//]: # ()
[//]: # (        Content: )

[//]: # (`)

[//]: # ({)

[//]: # (  "id" : "64299cd2b2afe565a469eba2",)

[//]: # (  "user_id" : 3,)

[//]: # (  "gender" : "M",)

[//]: # (  "age" : 25,)

[//]: # (  "occupation" : 15,)

[//]: # (  "zip_code" : "55117",)

[//]: # (  "userId" : 3,)

[//]: # (  "zipCode" : "55117")

[//]: # (})

[//]: # (`)

[//]: # ()
[//]: # (* Error Response:)

[//]: # ()
[//]: # (    - Code: 406 Not Acceptable)

[//]: # ()
[//]: # (        Content: `"Parameter is not a number!"`)

[//]: # ()
[//]: # (    Or)

[//]: # ()
[//]: # (    - Code: 404 Not Found)

[//]: # ()
[//]: # (        Content: `"User id does not exist!"`)

[//]: # ()
[//]: # ()
[//]: # (* Sample Call:)

[//]: # ()
[//]: # (Returns a user object with a given user ID 3. )

[//]: # (```bash)

[//]: # (curl -X GET http://localhost:8080/users/3)

[//]: # (```)

[//]: # ()
[//]: # (### Insert user)

[//]: # (Inserts a new user data to the database and returns a newly inserted user object given in data param.)

[//]: # ()
[//]: # (* URL:)

[//]: # ()
[//]: # (    `/users`)

[//]: # ()
[//]: # (* Method:)

[//]: # ()
[//]: # (    `POST`)

[//]: # ()
[//]: # (* URL Params:)

[//]: # ()
[//]: # (    None)

[//]: # ()
[//]: # (* Data Params:)

[//]: # ()
[//]: # (```json)

[//]: # ({)

[//]: # (    "gender": "M", )

[//]: # (    "age": 21, )

[//]: # (    "occupation": 7, )

[//]: # (    "zip_code": "100000")

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (* Success Response:)

[//]: # ()
[//]: # (    - Code: 200)

[//]: # ()
[//]: # (        Content: )

[//]: # (`)

[//]: # ({)

[//]: # (  "id" : "642c1427ce91314b8fb3c3b0",)

[//]: # (  "user_id" : 6061,)

[//]: # (  "gender" : "M",)

[//]: # (  "age" : 21,)

[//]: # (  "occupation" : 7,)

[//]: # (  "zip_code" : "100000")

[//]: # (})

[//]: # (`)

[//]: # ()
[//]: # (* Error Response:)

[//]: # ()
[//]: # (    - Code: 500 Internal Server Error)

[//]: # ()
[//]: # (        Content: `"Something wrong when saving the user"`)

[//]: # ()
[//]: # ()
[//]: # (* Sample Call:)

[//]: # ()
[//]: # (Returns a newly inserted user with given information `{"gender": "M", "age": 21, "occupation": 7, "zip_code": "100000"}`.)

[//]: # (```bash)

[//]: # (curl -X POST http://localhost:8080/users -H 'Content-type:application/json' -d '{"gender": "M", "age": 21, "occupation": 7, "zip_code": "100000"}')

[//]: # (```)

[//]: # ()
[//]: # (### Update user)

[//]: # (Updates an existing user data in database and returns the updated user object given in data param.)

[//]: # ()
[//]: # (* URL:)

[//]: # ()
[//]: # (    `/users/:id`)

[//]: # ()
[//]: # (* Method:)

[//]: # ()
[//]: # (    `PUT`)

[//]: # ()
[//]: # (* URL Params:)

[//]: # ()
[//]: # (    **Required**:)

[//]: # (    - `id=[integer]`)

[//]: # ()
[//]: # (* Data Params:)

[//]: # ()
[//]: # (```json)

[//]: # ({)

[//]: # (    "gender": "F", )

[//]: # (    "age": 18, )

[//]: # (    "occupation": 5, )

[//]: # (    "zip_code": "100")

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (* Success Response:)

[//]: # ()
[//]: # (    - Code: 200)

[//]: # ()
[//]: # (        Content: )

[//]: # (`)

[//]: # ({)

[//]: # (  "id" : "642c1427ce91314b8fb3c3b0",)

[//]: # (  "user_id" : 6061,)

[//]: # (  "gender" : "F",)

[//]: # (  "age" : 18,)

[//]: # (  "occupation" : 5,)

[//]: # (  "zip_code" : "100")

[//]: # (})

[//]: # (`)

[//]: # ()
[//]: # (* Error Response:)

[//]: # ()
[//]: # (    - Code: 406 Not Acceptable)

[//]: # ()
[//]: # (        Content: `"Parameter is not a number!"`)

[//]: # ()
[//]: # (    Or)

[//]: # ()
[//]: # (    - Code: 404 Not Found)

[//]: # ()
[//]: # (        Content: `"User id does not exist!"`)

[//]: # ()
[//]: # (    Or)

[//]: # ()
[//]: # (    - Code: 500 Internal Server Error)

[//]: # ()
[//]: # (        Content: `"Something wrong when saving the user"`)

[//]: # ()
[//]: # ()
[//]: # (* Sample Call:)

[//]: # ()
[//]: # (Change the user with id 6041 to `{"gender": "F", "age": 18, "occupation": 5, "zip_code": "100"}`)

[//]: # (```bash)

[//]: # (curl -X PUT http://localhost:8080/users/6041 -H 'Content-type:application/json' -d '{"gender": "F", "age": 18, "occupation": 5, "zip_code": "100"}')

[//]: # (```)
