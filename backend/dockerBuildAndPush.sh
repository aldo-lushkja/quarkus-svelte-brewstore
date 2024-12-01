#!/usr/bin/env bash

REGISTRY=docker.io
USERNAME=yourusername
PASSWORD=yourpassword
REPOSITORY=brewstore-backend

PUSH_REMOTE=false
PUSH_LATEST=false

# Function to display the help
function display_help() {
  echo "Usage: $0 [options...]" >&2
  echo
  echo "   -r <registry>    The docker registry to push the image to (default: docker.io)"
  echo "   -u <username>    The username to login to the docker registry (default: yourusername)"
  echo "   -p <password>    The password to login to the docker registry (default: yourpassword)"
  echo "   -r <repository>  The repository name (default: backend)"
  echo "   -v <version>     The version of the image (default: project version from pom.xml)"
  echo "   -d               Push the image to the registry (default: false)"
  echo "   -l               Push the image with the latest tag to the registry (default: false)"
  echo "   -h               Display this help message"
  echo
  echo "Example: $0 -r docker.io -u yourusername -v 1.0.0 -p true -l false (to push the image without the latest tag)"
  echo "Example: $0 -r docker.io -u yourusername -v 1.0.0 -p true -l true (to push the image with the latest tag)"
  exit 1
}

# Get the arguments
while getopts 'r:u:v:p:dlh' flag; do
  case "${flag}" in
    r) REGISTRY="${OPTARG}" ;;
    u) USERNAME="${OPTARG}" ;;
    v) VERSION="${OPTARG}" ;;
    p) PASSWORD="${OPTARG}" ;;
    d) PUSH_REMOTE=true ;;
    l) PUSH_LATEST=true ;;
    h) display_help ;;
    *) error "Unexpected option ${flag}" ;;
  esac
done

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

if [ -z "$VERSION" ]; then
  echo "Using project version from pom.xml"
  VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
fi

# Print the arguments
echo "----------------------------------------"
echo "REGISTRY: $REGISTRY"
echo "USERNAME: $USERNAME"
if [ -z "$PASSWORD" ]; then
  echo "PASSWORD: N/A"
else
  echo "PASSWORD: ********"
fi
echo "REPOSITORY: $REPOSITORY"
echo "VERSION: $VERSION"
echo "PUSH_REMOTE: $PUSH_REMOTE"
echo "PUSH_LATEST: $PUSH_LATEST"
echo "----------------------------------------"


# Build project with maven
echo "Building the project with maven"
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
  echo "Error: maven build failed"
  exit 1
fi

echo "Building the docker image..."

if [[ -z "$USERNAME" || $USERNAME == "yourusername" ]]; then
  # Read the username from the user
  echo "Enter your username for $REGISTRY"
  read -r USERNAME
fi

# Get the password from the user
if [[ -z "$PASSWORD" || $PASSWORD == "yourpassword" ]]; then
  echo "Enter your password for $REGISTRY"
  read -rs PASSWORD
fi

# Define the image name

IMAGE=$REGISTRY/$USERNAME/$REPOSITORY:$VERSION
IMAGE_LATEST=$REGISTRY/$USERNAME/$REPOSITORY:latest

echo "----------------------------------------"
echo "IMAGE: $IMAGE"
echo "IMAGE_LATEST: $IMAGE_LATEST"
echo "----------------------------------------"

# Login to the docker registry
docker login $REGISTRY -u $USERNAME -p $PASSWORD

if [ $? -ne 0 ];then
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

# Check if docker image can run successfully
# > /dev/null
container_id=$(docker run -d --rm "$IMAGE")
echo "Container ID: $container_id"

# Check if the container is running using retry
for i in {1..10}; do
  echo "$i of 10 - Checking if the container is running..."
  docker inspect -f '{{.State.Running}}' "$container_id"

  if [ $? -eq 0 ]; then
    break
  fi

  sleep 5
done
echo "Docker image is running successfully"

# Stop the container
docker rm -f "$container_id"
if [ $? -ne 0 ]; then
  echo "Error: docker run failed"
  exit 1
fi

# Check if service is healthy with retry


# Push the docker image
if [ "$PUSH_REMOTE" == true ]; then
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
  else
    echo "The image has not been pushed with the latest tag"
  fi
else
  echo "The image has not been pushed to the registry"
fi

echo "Docker image $IMAGE has been built and pushed successfully"