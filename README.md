# UNISTAGRAM

## Open Endpoints

* [Query rating](#query-rating) : `GET /ratings/:rating`

* [Query user](#query-user) : `GET /users/:id`

* [Insert user](#insert) : `GET /users`

* [Update user](#update) : `GET /users/:id`

## API Documentation

### Query rating
Returns the list of those movies with average rating greater or equal to the given rating score.

* URL:

    `/ratings/:rating`

* Method:

    `GET`

* URL Params:

    **Required**:
    - `rating=[double]`

* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
`[
{
  "movie_name" : "Toy Story (1995)",
  "genre": "Animation|Children's|Comedy"
},
{
    "movie_name": "xxx",
    "genre": "xxx",
},
...
]
`

* Error Response:

    - Code: 406 Not Acceptable

        Content: `"Parameter is not in the range of [1, 5]!"` or `"Parameter is not a number!"`

* Sample Call:
```bash
curl -X GET http://localhost:8080/ratings/4
```

### Query user

* URL:

    `/user/:id`

* Method:

    `GET`

* URL Params:

    **Required**:
    - `id=[integer]`

* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
`
{
  "id" : "64299cd2b2afe565a469eba2",
  "user_id" : 3,
  "gender" : "M",
  "age" : 25,
  "occupation" : 15,
  "zip_code" : "55117",
  "userId" : 3,
  "zipCode" : "55117"
}
`

* Error Response:

    - Code: 500 Internal Server Error

        Content: `"Something wrong when saving the user"`


* Sample Call:
```bash
curl -X GET http://localhost:8080/users/3
```

### Insert

* URL:

    `/user`

* Method:

    `POST`

* URL Params:

    **Required**:
    - `id=[integer]`

* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
`
{
  "id" : "642c1427ce91314b8fb3c3b0",
  "user_id" : 6061,
  "gender" : "M",
  "age" : 21,
  "occupation" : 7,
  "zip_code" : "100000"
}
`

* Error Response:

    - Code: 406 Not Acceptable

        Content: `"Parameter is not a number!"`

    Or

    - Code: 404 Not Found

        Content: `"User id does not exist!"`

    Or

    - Code: 500 Internal Server Error

        Content: `"Something wrong when saving the user"`


* Sample Call:
```bash
curl -X POST http://localhost:8080/users -H 'Content-type:application/json' -d '{"gender": "M", "age": 21, "occupation": 7, "zip_code": "100000"}'
```

### Update

* URL:

    `/user/:id`

* Method:

    `POST`

* URL Params:

    None

* Data Params:

    None

* Success Response:

    - Code: 200

        Content: 
`
{
  "id" : "642c1427ce91314b8fb3c3b0",
  "user_id" : 6061,
  "gender" : "M",
  "age" : 21,
  "occupation" : 7,
  "zip_code" : "100000"
}
`

* Error Response:

    - Code: 406 Not Acceptable

        Content: `"Parameter is not a number!"`

    Or

    - Code: 404 Not Found

        Content: `"User id does not exist!"`

    Or

    - Code: 500 Internal Server Error

        Content: `"Something wrong when saving the user"`


* Sample Call:
```bash
curl -X POST http://localhost:8080/users -H 'Content-type:application/json' -d '{"gender": "M", "age": 21, "occupation": 7, "zip_code": "100000"}'
```