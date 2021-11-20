# Run Cassandra in Docker

Cassandra image [documentation](https://hub.docker.com/_/cassandra).

```shell
# persist data in the 'cassandra' docker volume
docker volume create cassandra
docker run --name cassandra --network cassandra --volume cassandra:/var/lib/cassandra -d -p 9042:9042 cassandra:3.11

# remove the container and anonymous volume after stop, and loose data
docker run --name cassandra --network cassandra --rm -d -p 9042:9042 cassandra:3.11
```

View CQL [documentation](https://cassandra.apache.org/doc/latest/cassandra/cql/index.html).
Check schema via `cqlsh`:

```shell
docker exec -it cassandra /bin/bash
# cqlsh
cqlsh> SELECT * FROM opinion.questions;
```
