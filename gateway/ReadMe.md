# API Gateway

## Overview

The API Gateway is the single entry point for all client requests.

It is responsible for

- JWT Authentication
- Route Management
- Request Forwarding
- Cross-Origin Configuration

## Technology

- Spring Boot
- Spring Cloud Gateway
- Spring Security
- JWT

## Routes

/api/v1/**  → Patient Service

/api/v2/**  → Patient History Service

/api/v3/**  → Assessment Service

/login → Authentication

## Responsibilities

- Validate JWT
- Forward requests
- Reject unauthorized requests

## Port

8080