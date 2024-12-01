#!/usr/bin/env bash

echo "Starting kafka cluster"
if ! docker compose -f stack.yml up -d --remove-orphans; then
  echo "Failed to start kafka cluster"
  exit 1
fi
echo "Kafka cluster started"
