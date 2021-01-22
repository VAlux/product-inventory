#!/usr/bin/env bash
export TARGET_ENV=local
./mvnw clean package dockerfile:build
