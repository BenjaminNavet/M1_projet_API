# Description of the API of the project

This description is example based.
Install a plugin such as `RESTED` into a browser, or use the `curl` command in a shell.
The examples below use the curl command.

## Products resource

### Get the products' list

`GET /products` provides the list in the reply body.

```bash
$ curl -X GET /products
[
    {"id":1, "price": 2.0, "name":"Hotdog", "remaining": 3},
    {"id":13, "price": 4.0, "name":"Ball bearings pizza", "remaining": 0}
]
```

### Get the details of a product

`GET /products/:id` provides details about a product with a specific id.

```bash
$ curl -X GET /products/1
{"id":1, "price": 2.0, "name":"Hotdog", "remaining": 3}

```

### Get the list of out of stock product

An out of stock product has a `remaining` attribute value of 0.

`GET /products?out_of_stock=true`

```bash
$ curl -X GET /products?out_of_stock=true
{"id":13, "price": 4.0, "name":"Steering balls pizza", "remaining": 0}
```

## Customers resource

### Get the customers' list

`GET /customers`
provides the list of existing customers, in short form.

```bash
$ curl -X GET /customers
[
    {"id": 170, "name":"Emma", "credit":12.0},
    {"id": 195, "name":"Obelix", "credit":1.50}
]
```

### Get the details of a given customer

`GET /customers/:id` returns all the details about a given customer, using the unique id.  
These details include the current list of ordered products.

```bash
$ curl -X GET /customers/170
{"id": 170, "name":"Emma", "credit":12.0,
    "orders": [{"id":1,"price":2.0,"name":"Hotdog"}]}
```

### Ordering a product

`POST /customers/:cid/orders/:pid` adds a new product with id `pid` to the set of products ordered by customer `cid`.


