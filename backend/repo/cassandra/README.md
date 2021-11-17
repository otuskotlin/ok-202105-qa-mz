# Run Cassandra in Docker

```shell
docker run \
--name cassandra \
--network cassandra \
-d \
-p 9042:9042 \
cassandra:3.11
```

```shell
docker run --name cassandra --network cassandra -d -p 9042:9042 cassandra:3.11
```

Check schema via `cqlsh`, view [docs](https://cassandra.apache.org/doc/latest/cassandra/cql/index.html):

```shell
docker exec -it cassandra /bin/bash
# cqlsh
cqlsh> SELECT * FROM opinion.questions;
```
