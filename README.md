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

* [Query Messages In Conversation](#queryMessagesInConversation) : `GET /messages/:id`


* [Send Message To Conversation](#sendMessageToConversation) : `POST /messages/send`


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

... TODO