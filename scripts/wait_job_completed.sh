#!/bin/bash

OWNER="$1"
REPO="$2"
USER="$3"
PWD="$4"

if [ "$#" -eq 3 ]; then
  # use cURL with a Personal Access Token
  echo "Using USER as personal access token for the GitHub API"
  PARAMS=(-H "Authorization: Bearer $USER" -H "Accept: application/vnd.github.v3+json")
elif [ "$#" -eq 4 ]; then
  # use basic auth with cUrl
  echo "Using USER/PWD authentication for the GitHub API"
  PARAMS=(-u "$USER":"$PWD" -H "Accept: application/vnd.github.v3+json")
else
  echo "Usage: wait_job_completion.sh OWNER REPO USER [PWD]"
  echo "OWNER    = the owner/org of the github repo"
  echo "REPO     = the name of the github repo"
  echo "USER     = the username to use for authentication against the GitHub API, or an API token"
  echo "PWD      = the password of USER. if not specified, USER will be interpreted as token"
  exit 1
fi

RUN_ID=$(cat ./run.id)

while [ "$status" != "completed" ]; do
  json=$(curl --fail -sSl "${PARAMS[@]}" -X GET "https://api.github.com/repos/${OWNER}/${REPO}/actions/runs/${RUN_ID}")
  status=$(echo "$json" | jq -r '.status')
  conclusion=$(echo "$json" | jq -r '.conclusion')
  echo "$(date) :: Run $RUN_ID is $status"
  sleep 30 # sleep for 30 seconds before we check again, lets keep API requests low
done

echo "Run completed, conclusion: $conclusion"
