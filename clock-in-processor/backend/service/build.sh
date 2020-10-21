#!/bin/bash
#*******************************************************************************
# Copyright 2020 Marcelo Riss
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#*******************************************************************************
#########################################################################################
# BUILD IMAGE AND DEPLOY TO CLOUD RUN TO BE INVOKED FROM MAVEN EXEC PLUGIN             ##
#########################################################################################
# At command line type:                                                                ##
#   mvn clean install exec:exec                                                        ##
#########################################################################################
# PARAMETERS:                                                                          ##
# 1. VERSION: maven project version info with version and optionally build number      ##
# 2. SERVICE_NAME: clockin-backend-service-dev or clockin-backend-service              ##
#    that defines at which service to deplopy at CloudRun.                             ##
# 3. PROJECT_ID: gcloud project id.                                                    ##
#########################################################################################
# This scripts assumes gcloud executable is at OS PATH variable                        ##
#########################################################################################

deployAtCloudRun() {
    local version=$1
    local serviceName=$2
    local projectId=$3

    echo version=$version
    echo serviceName=$serviceName

    mkdir -p temp && cp target/clockin-backend-service-*.jar temp

    gcloud config set project $projectId && \
    gcloud builds submit --tag gcr.io/$projectId/mail-processing-service && \
    gcloud container images add-tag gcr.io/$projectId/mail-processing-service:latest gcr.io/$projectId/mail-processing-service:$version --quiet && \
    gcloud run deploy $serviceName --image gcr.io/$projectId/mail-processing-service --platform managed --region=us-east1 --memory 1024Mi --quiet --cpu=2000m --revision-suffix=$version
}

VERSION=$1
SERVICE_NAME=$2
PROJECT_ID=$3

echo Version: $VERSION
echo Service Name: $SERVICE_NAME
echo Project Id: $PROJECT_ID
echo Path: $PATH

java -version

FINAL_VERSION=$VERSION

deployAtCloudRun $FINAL_VERSION $SERVICE_NAME $PROJECT_ID

rm -rf temp

