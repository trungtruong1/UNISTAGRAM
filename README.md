# UNISTAGRAM

## Contents

* [Features](#features) : A brief introduction to our system's features.
* [Rest API Open Endpoints](#rest-api-open-endpoints) : A brief introduction to our system's REST API open endpoints.
* [API Documentation](#api-documentation) : A comprehensive documentation of the current REST API version.

## Note for Users: Open Endpoints are the same as the previous milestone
* [Query user](#query-user) : `GET /users/:id`

* [Insert user](#insert-user) : `POST /users`

* [Update user](#update-user) : `PUT /users/:id`

# Features

After considering the practicality and feasibility of our project, we want to focus on a more coherent system, so we decided to remove the game and auction, and keeping and specializing in only the anonymous messaging and meme feed. Therefore, our system will contain the following features

* [Chatting](#chatting): Sending and querying the messages according to users' conversation. The identities of both parties are not disclosed (remain anonymous) to each other throughout the entire session.

* [Matching](#matching): Matches 2 users randomly to form a new anonymous conversation. 

* [MemeFeed](#memefeed): Provides an active and interactive meme feed so that everyone can share memes, or about anything one’s heart may desire. 

# REST API Open Endpoints

## Chatting
Sending and querying the messages according to users' conversation. The identities of both parties are not disclosed (remain anonymous) to each other throughout the entire session. 

This current REST API version is only for testing purposes as it does not support real-time communication between the client and the server. We find hard to test this real-time connection using commands as the `curl` commands could get ridiculously long and prone to errors. Therefore, it is best to present the REST API version, and the real-time connection using websocket will be provided after a front-end system is completed in the next milestone.

### Open Endpoints

* [Query Messages In Conversation](#query-messages-in-conversation) : `GET /messages/:id`


* [Send Message To Conversation](#send-message-to-conversation) : `POST /messages/send`


* [Query All Conversations](#query-all-conversations) : `GET /conversations/` (only for testing purposes, will be removed on production)


* [Query A Conversation](#query-a-conversation) : `GET /conversations/:id`


* [Query Conversations By User](#query-conversations-by-user) : `GET /conversations/users/:id`


* [End Conversation](#end-conversation) : `PUT /conversations/end/:id`

## Matching
Matches 2 users randomly to form a new anonymous conversation. 

This current REST API version is only for testing purposes as it does not support real-time communication between the client and the server. We find hard to test this real-time connection using commands as the `curl` commands could get ridiculously long and prone to errors. Therefore, it is best to present the REST API version, and the real-time connection using websocket will be provided after a front-end system is completed in the next milestone.

### Open Endpoints

* [Add an user to the waiting queue](#updateUserToJoinQueue) : `PUT /matching/join_queue/:id`


* [Remove the user from the queue](#updateUserToOutQueue) : `PUT /matching/out_queue/:id`


* [Check if the user is in the queue](#checkUserInQueue) : `GET /matching/check_in_queue/:id`

Note that the system will automatically matches users when they enter the queue.

## MemeFeed
Provides an active and interactive meme feed so that everyone can share memes, or about anything one’s heart may desire.

In addition to posting memes, user can also "react" to the meme by choosing among various customized reactions created by other users.

### Open Endpoints

* [Query meme my ID](#queryMemeByID) : `GET /memes/:id`


* [Post a new meme](#sendMeme) : `POST /memes`
  

* [Add a custom reaction](#saveReactino) : `POST /reactions`


* [Get a reaction by an ID](#getMemeByID): `GET /reactions/:id`


* [Query all reactions in the meme](#queryReactionsInMeme) : `GET /meme_reactions/:id`


* [React to a meme](#sendReaction) : `POST /meme_reactions/add`


* [Remove the user's reaction from a meme](#removeReaction) : `DELETE /meme_reactions/del`


# API Documentation

## Chat app

### Query Messages In Conversation
Returns the list of messages given a converstion ID.

* URL:

    `/messages/:id`

* Method:

    `GET`

* URL Params:

    **Required**:
    - `id=[string]` : The conversation ID

* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
```json 
[ {
  "id" : "646089d9041dea27617b717f",
  "conversation" : "645e37b871927a11886bc874",
  "sender" : "64299cd2b2afe565a469ebbf",
  "receiver" : "642b007f78fa427e80e8e3dd",
  "content" : "Hi~ Nice to meet u~",
  "timestamp" : 1684117781004
}, {
  "id" : "646089de041dea27617b7180",
  "conversation" : "645e37b871927a11886bc874",
  "sender" : "64299cd2b2afe565a469ebbf",
  "receiver" : "642b007f78fa427e80e8e3dd",
  "content" : "hi!",
  "timestamp" : 1684117781004
},
...
]
```

* Sample Call:

Returns a list of messages in the conversation with the ID of `645e37b871927a11886bc874`.
```bash
curl -X GET localhost:8080/messages/645e37b871927a11886bc874
```

### Send Message To Conversation
Send a new message to the given conversation. The API will return the newly sent message

* URL:

    `/messages/send`

* Method:

    `POST`

* URL Params:

    None

* Data Params:

    **Required**:
    - `conversation=[string]` : The conversation ID that this message belongs
    - `sender=[string]` : The ID of the sender
    - `content=[string]` : The main content of the message

* Success Response:

    - Code: 200

        Content: 
```json
{
  "id" : "64619c77410d4207a6b45518",
  "conversation" : "645e38253d6d8e1ff0a3b11e",
  "sender" : "64299cd2b2afe565a469ebbf",
  "receiver" : "64299cd2b2afe565a469eba6",
  "content" : "I want to sleep~",
  "timestamp" : 1684118647784
}
```

* Error Response:

    - Code: 404 Not Found

        Content: `"Conversation does not exist!"`

    - Code: 406 Not Acceptable

        Content: `"Conversation has been terminated!"` or `"Sender is not in this conversation!"`

* Sample Call:

Returns the newly sent message.
```bash
curl -X POST localhost:8080/messages/send -H 'Content-type:application/json' -d '{"conversation": "645e38253d6d8e1ff0a3b11e", "sender": "64299cd2b2afe565a469ebbf", "content": "I want to sleep~"}'
```

### Query All Conversations
Return all conversations in the database.

* URL:

    `/conversations/`

* Method:

    `GET`

* URL Params:

    None

* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
```json
[ {
  "id" : "645e37b871927a11886bc874",
  "client1" : "64299cd2b2afe565a469ebbf",
  "client2" : "642b007f78fa427e80e8e3dd",
  "status" : "ONGOING"
}, {
  "id" : "645e38253d6d8e1ff0a3b11e",
  "client1" : "64299cd2b2afe565a469eba6",
  "client2" : "64299cd2b2afe565a469ebbf",
  "status" : "TERMINATED"
},
...
]
```

* Sample Call:

Returns the all conversations in the database.
```bash
curl -X GET localhost:8080/conversations/
```

### Query A Conversations
Return the conversation's information.

* URL:

    `/conversations/:id`

* Method:

    `GET`

* URL Params:

    **Required**:
    - `id=[string]` : The conversation ID

* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
```json
{
  "id" : "645e37b871927a11886bc874",
  "client1" : "64299cd2b2afe565a469ebbf",
  "client2" : "642b007f78fa427e80e8e3dd",
  "status" : "ONGOING"
}
```

* Error Response:

    - Code: 404 Not Found

        Content: `"Conversation id does not exist!"`

* Sample Call:

Return the conversation's information.
```bash
curl -X GET localhost:8080/conversations/645e37b871927a11886bc874
```

### Query Conversations By User
Return a list of conversations that the user participates in.

* URL:

    `/conversations/users/:id`

* Method:

    `GET`

* URL Params:

    **Required**:
    - `id=[integer]` : The user ID

* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
```json
[ {
  "id" : "645e37b871927a11886bc874",
  "client1" : "64299cd2b2afe565a469ebbf",
  "client2" : "642b007f78fa427e80e8e3dd",
  "status" : "ONGOING"
}, {
  "id" : "645e38253d6d8e1ff0a3b11e",
  "client1" : "64299cd2b2afe565a469eba6",
  "client2" : "64299cd2b2afe565a469ebbf",
  "status" : "TERMINATED"
},
...
]
```

* Error Response:

    - Code: 404 Not Found

        Content: `"User id does not exist!"`

* Sample Call:

Return the conversation's information.
```bash
curl -X GET localhost:8080/conversations/users/1
```

### End Conversation
Terminate the conversation.

* URL:

    `/conversations/end/:id`

* Method:

    `PUT`

* URL Params:

    **Required**:
    - `id=[string]` : The conversation ID

* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
```json
{
  "id" : "645e38253d6d8e1ff0a3b11e",
  "client1" : "64299cd2b2afe565a469eba6",
  "client2" : "64299cd2b2afe565a469ebbf",
  "status" : "TERMINATED"
}
```

* Error Response:

    - Code: 404 Not Found

        Content: `"Conversation ID does not exist!"`

* Sample Call:

Return the conversation's information.
```bash
curl -X PUT localhost:8080/conversations/end/645e38253d6d8e1ff0a3b11e
```

## Matching app

### Update a user to join the waiting queue
Add a user to the waiting queue for conversation.

* URL:

    `/matching/join_queue/:id`

* Method:

    `PUT`

* URL Params:

    **Required**:
    - `id=[string]` : The user ID

* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
```json 
{
  "id" : "646076867f96a723e9a3f204",
  "user_id" : 3000,
  "age" : 25,
  "gender" : "M",
  "is_in_queue" : true
}
```
* Error Response:

    - Code: 404 Not Found

        Content: `"User id does not exist!"`

    - Code: 406 Not Acceptable

        Content: `"Parameter is not a number!"`

* Sample Call:

Add to the waiting queue a user with the id `3000`.
```bash
curl -X PUT localhost:8080/matching/join_queue/3000
```

### Remove a user from the waiting queue
Remove the user from the waiting queue

* URL:

    `/matching/out_queue/:id`

* Method:

    `PUT`

* URL Params:

    None

* Data Params:

    **Required**:
    - `id=[string]` : The user ID

* Success Response:

    - Code: 200

        Content: 
```json
{
  "id" : "646076867f96a723e9a3f204",
  "user_id" : 3000,
  "age" : 25,
  "gender" : "M",
  "is_in_queue" : false
}
```

* Error Response:

    - Code: 404 Not Found

        Content: `"User id does not exist!"`

    - Code: 406 Not Acceptable

        Content: `"Parameter is not a number!"`

* Sample Call:

Remove from the queue a user with the id `3000`.
```bash
curl -X PUT localhost:8080/matching/out_queue/3000
```

### Check if the user is in the queue

* URL:

    `/matching/check_in_queue/:id`

* Method:

    `GET`

* URL Params:

    None

* Data Params:

    **Required**:
    - `id=[string]` : The user ID

* Success Response:

    - Code: 200

        Content: 
```json
{
  "is_in_queue" : true/false
}
```

* Error Response:

    - Code: 404 Not Found

        Content: `"User id does not exist!"`

* Sample Call:

Returns the status `is_in_queue` of the user
```bash
curl -X GET localhost:8080/matching/check_in_queue/3000
```

## Memefeed app

### Post a meme 

* URL:

    `/memes`

* Method:

    `POST`

* URL Params:

    None

* Data Params:

    **Required**:
    - `title=[string]` : The title of the posted meme
    - `image=[@path/to/meme]` : The address of the meme 
    - `author=[string]` : The user who posted the meme 

* Success Response:

    - Code: 200

        Content: 
```json 
{
  "id" : "6461c3919e67e01f498aa6f0",
  "title" : "Meme",
  "image" : {
    "type" : 0,
    "data" : <data>
  },
  "author" : "Jay",
  "timestamp" : 1684128657972
}
```

* Sample Call:

Add to the database a meme with the title `Meme`, path: `@data/meme_test.png`, and author `Jay`:
```bash
curl -X POST localhost:8080/memes -H "Content-Type:multipart/form-data" -F "title=Meme" -F "image=@data/meme_test.png" -F "author=Jay"
```

### Get a meme by its id

* URL:

    `/memes/:id`

* Method:

    `GET`

* URL Params:

    **Required**:
    - `id=[string]` : The meme ID

* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
```json 
{
  "id" : "6461c3919e67e01f498aa6f0",
  "title" : "Meme",
  "image" : {
    "type" : 0,
    "data" : <data>
  },
  "author" : "Jay",
  "timestamp" : 1684128657972
}
```

* Error Response:

    - Code: 404 Not Found

        Content: `"The meme id does not exist!"`


* Sample Call:

Get from the database the meme with the id `6461c3919e67e01f498aa6f0`.
```bash
curl -X GET localhost:8080/memes/6461c3919e67e01f498aa6f0
```

### Save Reaction
This feature is created to allow users to upload their own reaction to memes

* URL:

    `/reactions`

* Method:

    `POST`

* URL Params:

    None

* Data Params:

    **Required**:
    - `title=[string]` : The title of the uploaded reaction
    - `image=[@path/to/meme]` : The path to the reaction
    - `author=[string]` : The user who posted the reaction

* Success Response:

    - Code: 200

        Content: 
```json
{
  "id" : "6461c9989e67e01f498aa6f1",
  "title" : "Reaction",
  "image" : {
    "type" : 0,
    "data" : <data>
  },
  "author" : "Garrick",
  "timestamp" : 1684130200614
}
```

* Sample Call:

Add to the database a meme with the title `Reaction`, path: `@data/reaction_test.png`, and author `Garrick`:
```bash
curl -X POST localhost:8080/reactions -H "Content-Type:multipart/form-data" -F "title=Reaction" -F "image=@data/reaction_test.png" -F "author=Garrick"
```
### Get a reaction by its id

* URL:

    `/reactions/:id`

* Method:

    `GET`

* URL Params:

    **Required**:
    - `id=[string]` : The reaction ID

* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
```json 
{
  "id" : "6461c9989e67e01f498aa6f1",
  "title" : "Reaction",
  "image" : {
    "type" : 0,
    "data" : <data>
  },
  "author" : "Garrick",
  "timestamp" : 1684130200614
}
```

* Error Response:

    - Code: 404 Not Found

        Content: `"The reaction id does not exist!"`


* Sample Call:

Get from the database the reaction with the id `6461c9989e67e01f498aa6f1`.
```bash
curl -X GET localhost:8080/reactions/6461c9989e67e01f498aa6f1
```

### Add Reaction to a meme
Every reaction a user gave to a meme is added.

* URL:

    `/meme_reactions/add`

* Method:

    `POST`

* URL Params:

    None

* Data Params:

    **Required**:
    - `meme_id=[string]` : The id of the meme
    - `reaction_id=[string]` : The id of the reaction
    - `user=[string]` : The user who made the reaction

* Success Response:

    - Code: 200

        Content: 
```json 
{
  "id" : "6461cbad9e67e01f498aa6f2",
  "meme_id" : "6461c3919e67e01f498aa6f0",
  "reaction_id" : "6461c9989e67e01f498aa6f1",
  "user_id" : "64299cd2b2afe565a469eba6"
}
```

* Error Response:

    - Code: 409 Conflict 

        Content: `"This user has already reacted to this meme!"`


* Sample Call:

An user with id `64299cd2b2afe565a469eba6` reacted to the meme `6461c3919e67e01f498aa6f0` with the reaction `6461c9989e67e01f498aa6f1`
```bash
curl -X POST localhost:8080/meme_reactions/add/ -F 'meme_id=6461c3919e67e01f498aa6f0' -F 'reaction_id=6461c9989e67e01f498aa6f1' -F 'user_id=64299cd2b2afe565a469eba6'
```

### Get all the reactions that have been given to a meme.

* URL:

    `/meme_reactions/:id`

* Method:

    `GET`

* URL Params:

    **Required**:
    - `id=[string]` : The id of the meme
  
* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
```json 
{
  "6461c9989e67e01f498aa6f1" : 1
}
```

* Sample Call:

Get all reactions that has been given to the meme with meme_id `6461c3919e67e01f498aa6f0`
```bash
curl -X GET localhost:8080/meme_reactions/6461c3919e67e01f498aa6f0
```

### Remove reaction from a meme.
I think it is no longer funny, i want my reaction back!

* URL:

    `/meme_reactions/del`

* Method:

    `DELETE`

* URL Params:

    None
  
* Data Params:

    **Required**:
    - `meme_id=[string]` : The id of the meme
    - `reaction_id=[string]` : The id of the reaction
    - `user=[string]` : The user who wants to retract the reaction

* Success Response:

    - Code: 200

        Content: 
```json 
true
```

* Sample Call:

An user with id `64299cd2b2afe565a469eba6` retracts his/her reaction `6461c9989e67e01f498aa6f1` from the meme `6461c3919e67e01f498aa6f0` 
```bash
curl -X DELETE localhost:8080/meme_reactions/del -F 'meme_id=6461c3919e67e01f498aa6f0' -F 'reaction_id=6461c9989e67e01f498aa6f1' -F 'user_id=64299cd2b2afe565a469eba6'
```