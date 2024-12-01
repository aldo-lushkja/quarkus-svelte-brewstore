#!/usr/bin/env bash

echo "Tailing kafka cluster logs"
docker compose -f stack.yml logs -f
