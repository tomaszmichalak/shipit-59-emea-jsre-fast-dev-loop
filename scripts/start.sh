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

# Verify the API is working and returns the expected response

URL=http://recommendations-engine-127-0-0-1.nip.io/recommendations/existingUser@atlassian.com
EXPECTED_RESPONSE='[{"id":"test1","name":"name1","description":"description1","url":"url1"}]'

response=$(curl -s "$URL")
if echo "$response" | jq -e . >/dev/null 2>&1; then
  if [[ "$response" == "$EXPECTED_RESPONSE" ]]; then
    echo "Response matches the expected JSON."
  else
    echo "Response does not match the expected JSON: $response"
    exit 1
  fi
else
  echo "Response is not a valid JSON: $response"
  exit 1
fi

