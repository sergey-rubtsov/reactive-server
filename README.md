# Reactive Server

This is a new scalable application that allows its existing users to \
store some payload.
Implemented using reactive style programming, from endpoint handling to database queries.

System consist of two microservices: Client and Server. \
They are communicating directly via REST to each other. 

This service is **Server**. \
The **Client** example is here [https://github.com/sergey-rubtsov/reactive-client](https://github.com/sergey-rubtsov/reactive-client)

It processes request from Client, makes some calculations \
and turns back Id and some value as a response. \
All the application data stored in a centralized DB H2, \
but it can be used Postgres for that (you should change reactive driver dependency to postgres).

## Server

Run it with gradle task bootRun

Server port and database configured in application.yml
With default configuration metrics are available by url:

[http://localhost:8086/metrics](http://localhost:8086/metrics)

---

Client payload request has format: 

  - requestId

  - data (string, for example: "AAAA", "BBBB", "DDDD", "EE" or any other)

  - quantity

Example:

`
{  
    "requestId":  "5389302e-0095-422b-8b23-87c16ca66153",  
    "data":   "AAAA",  
    "quantity": 42  
}
`

- Client is printing new request data and response data into the standard output and log

Server is calculating response value based on this formula:

  - responseNumber = quantity * random number from 1.1 to 2.0
  
Example:

`
{
    "requestId":      "5389302e-0095-422b-8b23-87c16ca66153";
    "responseNumber":   1.2
}
`

Server metrics endpoint returns 2 properties:

  - total count of processed requests

  - average responseNumber
  
Example:

`
{
    "total":    72,
    "average":  68.59713168127218613333333333333333333333333
}
`