# primer-server

TDD + Hexagonal Architecture (ports and adapters pattern) 

## Notes

- I tried to keep the domain layer clean of external dependencies (such as `Akka Streams`) so I used standard libraries datastructures, such as `LazyList` and then convert them to the desired data type required by the adapter layer.

## Disclaimer

- the original idea was to generate a multimodule project (proxy-service, prime-number-service and proto) to share common dependencies and having a single point of management of the entire project, but I got stuck with `akka-grpc` plugin (I had issues generating stubs in a multimodule project). So, I finally ended up using different independent projects.

## TODO

- add more logging on `prime-number-service`
