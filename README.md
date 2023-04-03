# UNISTAGRAM
Very cool app

bietongliemkhong?

Trong `main/java/com/JustA_Group`
1. Config: Cho Swagger thoi k can qtam
1. Controller: Control may cai API
1. Model: Declare may cai don vi (entities). Vdu: User, Movie, Lon, cu, Player
1. Repositories: Ncl cai repo cho cai entity
1. Service: Dinh nghia cac cai service cua cac cai entities

`main/resources`:
Chua cac thong tin de access mongodb (ten dang nhap, pwd, ten database....)

How to do rating query:
```js
db.rating.aggregate([
{ 
    $group: { 
        _id: "$movie_id", 
        avg_rating: { $avg: "$rating" } 
    } 
}, 
{
    $match: {
        avg_rating: { $gte: 4 }
    }
},
{ 
    $lookup: { 
        "from": "movie", 
        "localField": "_id", 
        "foreignField": "movie_id", 
        "as": "matching_movie" 
    } 
}, 
{ $unwind: "$matching_movie" }, 
{ 
    $project: { 
        "matching_movie.movie_name": 1, 
        "matching_movie.genre": 1 
    } 
}, 
{ 
    $replaceRoot: { newRoot: "$matching_movie" } 
}
])
```