# primer-server

## Stack

- Scala 2.13
- Akka HTTP
- Akka Streams
- gRPC

## Architectural components

- `prime-number-server`: serve prime numbers through gRPC. It was implemented using `TDD` + `Hexagonal Architecture` (ports and adapters pattern)

- `proxy-server`: middleware which acts as a client entrypoint to the system. Because it simplicity, its layering is simpler than the previous service (having just a route and service layers). It also was implemented using `TDD`.

## Notes

- I tried to keep the domain layer clean of external dependencies (such as `Akka Streams`) so I used standard libraries datastructures, such as `LazyList` and then convert them to the desired data type required by the adapter layer.

## Run the app

1. Inside `prime-number-server` folder, run:

`sbt run`

It will start up the gRPC server on `localhots:8082`

2. Inside `proxy-service` folder, run:

`sbt run`

It will start up the rest server on `localhots:8080`

Then, you can make request to the proxy server:

`curl http://localhost:8080/prime/16`

## Disclaimer

- The original idea was to generate a multimodule project (proxy-service, prime-number-service and proto) to share common dependencies and having a single point of management of the entire project, but I got stuck with `akka-grpc` plugin (I had issues generating stubs in a multimodule project). So, I finally ended up using different independent projects.

- Sorry for the last trailing comma-space (", ") when requesting the endpoint.