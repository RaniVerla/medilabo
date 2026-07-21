# Assessment Service

## Overview

Generates a diabetes risk assessment by aggregating patient demographics and medical history from other services.

## Responsibilities

- Retrieve Patient Information
- Retrieve Medical History
- Count Trigger Terms
- Calculate Risk Level

## Technology

- Spring Boot
- WebClient
- DTO Mapping

## Endpoint

GET /api/v3/assessments/{patientId}

## Risk Levels

None

Borderline

In Danger

Early Onset

## Business Logic

Assessment is calculated using

- Age

- Gender

- Trigger Terms