#!/usr/bin/env bash
cd ..
HOME=$(pwd)

#TODO: Fix running the script from any directory
cd "$HOME"/modules/recommendations-engine/code || exit
mvn clean install
docker build -t recommendations-engine:latest .

cd "$HOME"/modules/recommendations-service/code || exit
docker build -t recommendations-service:latest . || exit

cd "$HOME"/modules/users-service/code || exit
docker build -t users-service:latest . || exit


kind create cluster --config="$HOME"/.github/kind.yml
kind load docker-image recommendations-engine:latest
kind load docker-image recommendations-service:latest
kind load docker-image users-service:latest

cd "$HOME"/modules/recommendations-engine/chart || exit
helm install recommendations-engine .

cd "$HOME"/modules/recommendations-service/chart || exit
helm install recommendations-service .

cd "$HOME"/modules/users-service/chart || exit
helm install users-service .

kubectl rollout status deployment recommendations-engine
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
kubectl -n ingress-nginx rollout status deployment ingress-nginx-controller




# curl service and check if output is Hello World
response=$(curl -s -X GET http://recommendations-engine-127-0-0-1.nip.io/recommendations/existingUser@atlassian.com)

if [[ "$response" == "Hello World!" ]]; then
  echo "Service responded with 'Hello World!'"
else
  echo "Service did not respond with 'Hello World'. Response: $response"
  exit 1
fi