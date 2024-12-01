#!/usr/bin/env bash

# Usage: ./kill.sh

echo "Killing kafka cluster"

if ! docker compose -f stack.yml down; then
  echo "Failed to kill kafka cluster"
  exit 1
fi

echo "Kafka cluster killed"
