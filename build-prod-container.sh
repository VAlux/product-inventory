#!/usr/bin/env bash
export TARGET_ENV=prod
./mvnw clean package dockerfile:build
