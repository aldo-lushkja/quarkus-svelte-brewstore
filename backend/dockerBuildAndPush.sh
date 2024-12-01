#!/usr/bin/env bash

REGISTRY=docker.io
USERNAME=yourusername
REPOSITORY=backend
VERSION=latest

PUSH_REMOTE=false
PUSH_LATEST=false

# Function to display the help
function display_help() {
  echo "Usage: $0 [options...]" >&2
  echo
  echo "   -r <registry>    The docker registry to push the image to (default: docker.io)"
  echo "   -u <username>    The username to login to the docker registry (default: yourusername)"
  echo "   -v <version>     The version of the image (default: latest)"
  echo "   -p               Push the image to the registry (default: false)"
  echo "   -l               Push the image with the latest tag to the registry (default: false)"
  echo "   -h               Display this help message"
  echo
  echo "Example: $0 -r docker.io -u yourusername -v 1.0.0 -p true -l false (to push the image without the latest tag)"
  echo "Example: $0 -r docker.io -u yourusername -v 1.0.0 -p true -l true (to push the image with the latest tag)"
  exit 1
}

# Get the arguments
while getopts 'r:u:v:p:l:h' flag; do
  case "${flag}" in
    r) REGISTRY="${OPTARG}" ;;
    u) USERNAME="${OPTARG}" ;;
    v) VERSION="${OPTARG}" ;;
    p) PUSH_REMOTE=true ;;
    l) PUSH_LATEST=true ;;
    h) display_help ;;
    *) error "Unexpected option ${flag}" ;;
  esac
done

# Print the arguments
echo "----------------------------------------"
echo "REGISTRY: $REGISTRY"
echo "USERNAME: $USERNAME"
echo "REPOSITORY: $REPOSITORY"
echo "VERSION: $VERSION"
echo "PUSH_REMOTE: $PUSH_REMOTE"
echo "PUSH_LATEST: $PUSH_LATEST"
echo "----------------------------------------"

# Check if the docker command exists
if ! [ -x "$(command -v docker)" ]; then
  echo 'Error: docker is not installed.' >&2
  exit 1
fi

# Check if the maven command exists
if ! [ -x "$(command -v mvn)" ]; then
  echo 'Error: maven is not installed.' >&2
  exit 1
fi

# Build project with maven
echo "Building the project with maven"
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
  echo "Error: maven build failed"
  exit 1
fi

echo "Building the docker image..."

if [ -z "$USERNAME" ]; then
  # Read the username from the user
  echo "Enter your username for $REGISTRY"
  read -r USERNAME
fi

# Get the password from the user
echo "Enter your password for $REGISTRY"
read -rs PASSWORD


# Define the image name

IMAGE=$REGISTRY/$USERNAME/$REPOSITORY:$VERSION
IMAGE_LATEST=$REGISTRY/$USERNAME/$REPOSITORY:latest

# Login to the docker registry
docker login $REGISTRY -u $USERNAME -p $PASSWORD

if [ $? -ne 0 ]; then
  echo "Error: docker login failed"
  exit 1
fi

# Build the docker image
docker build -t "$IMAGE" -f src/main/docker/Dockerfile.jvm .

if [ $? -ne 0 ]; then
  echo "Error: docker build failed"
  exit 1
fi

# Build the docker image latest
docker build -t "$IMAGE_LATEST" -f src/main/docker/Dockerfile.jvm .

if [ $? -ne 0 ]; then
  echo "Error: docker build failed latest"
  exit 1
fi

# Push the docker image
if [ "$PUSH_REMOTE" = true ]; then
  docker push "$IMAGE"

  if [ $? -ne 0 ]; then
    echo "Error: docker push failed"
    exit 1
  fi

  if [ "$PUSH_LATEST" = true ]; then
    docker push "$IMAGE_LATEST"

    if [ $? -ne 0 ]; then
      echo "Error: docker push failed latest"
      exit 1
  fi
fi

echo "Docker image $IMAGE has been built and pushed successfully"