#!/bin/bash

echo "begin creating indexes"
cat /import/load/create_indexes.cql | cypher-shell -u neo4j -p your_password
echo "begin loading"
cat /import/load/load_data.cql | cypher-shell -u neo4j -p your_password

$@
