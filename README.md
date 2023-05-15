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


* [Query All Conversations](#queryAllConversations) : `GET /conversations/` (only for testing purposes, will be removed on production)


* [Query A Conversation](#queryAConversation) : `GET /conversations/:id`


* [Query Conversations By User](#queryConversationByUser) : `GET /conversations/users/:id`

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

* [Query meme my ID](#queryMemeByID) : `GET /memems/:id`


* [Post a new meme](#sendMeme) : `POST /memes`


* [Query all reactions in the meme](#queryReactionsInMeme) : `GET /meme_reactions/:id`


* [Create a new custom reaction](#sendReaction) : `POST /meme_reactions/add`


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

Returns the newly sent message .
```bash
curl POST localhost:8080/messages/send -H 'Content-type:application/json' -d '{"conversation": "645e38253d6d8e1ff0a3b11e", "sender": "64299cd2b2afe565a469ebbf", "content": "I want to sleep~"}'
```