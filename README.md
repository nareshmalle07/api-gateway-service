# Amazon Order Management System --- Production-Level Roadmap

## Goal

Build the Amazon-style Order Management System from a simple Spring Boot
application into a production-ready, cloud-deployable microservices
application.

The final system will cover:

-   Java and Spring Boot
-   REST APIs
-   Microservices architecture
-   API Gateway
-   Service Discovery
-   Distributed data ownership
-   HTTP communication
-   Kafka and event-driven architecture
-   Correlation ID and distributed tracing
-   JWT authentication and authorization
-   Resilience patterns
-   Idempotency
-   Saga Pattern
-   Outbox Pattern
-   State Machine
-   Logging and observability
-   Automated testing
-   Docker
-   Kubernetes
-   CI/CD
-   AWS cloud deployment
-   Production security
-   Monitoring and troubleshooting

------------------------------------------------------------------------

# 1. Simple Order Service

## Objective

Start with a simple Order Service before introducing microservices
complexity.

## Topics

-   Spring Boot project structure
-   Controller
-   Service
-   Repository
-   Entity
-   DTO
-   Request/Response model
-   REST APIs
-   JPA/Hibernate
-   Database integration
-   Validation
-   Exception handling
-   CRUD operations
-   Basic unit/API testing

## Basic Flow

``` text
Client
  |
  v
Order Controller
  |
  v
Order Service
  |
  v
Order Repository
  |
  v
Database
```

**Status: COMPLETED / BASELINE**

------------------------------------------------------------------------

# 2. Real / Production-Quality Order Service

## Objective

Convert the basic Order Service into a realistic production-oriented
service.

## Topics

-   Clean package structure
-   DTO and Entity separation
-   Request validation
-   Global exception handling
-   Custom exceptions
-   Standard error response
-   `@Transactional`
-   Transaction boundaries
-   Database constraints
-   Optimistic locking
-   Pessimistic locking
-   Pagination
-   Sorting
-   Filtering
-   API versioning
-   Idempotency
-   Order status
-   Order lifecycle
-   Audit fields
-   Created/updated timestamps
-   Database indexing
-   SQL optimization
-   Unit testing
-   Integration testing
-   API testing

## Production Problems to Handle

-   Duplicate order creation
-   Concurrent order updates
-   Invalid requests
-   Database failure
-   Transaction rollback
-   Partial failure

**Status: CURRENT / NEXT MAJOR AREA**

------------------------------------------------------------------------

# 3. Product Service Extraction

## Objective

Extract Product functionality into its own microservice.

``` text
Order Service
      |
      | HTTP
      v
Product Service
      |
      v
Product Database
```

## Topics

-   Service boundaries
-   Product domain
-   Product API
-   Product database
-   Distributed data ownership
-   REST client
-   Service-to-service communication
-   Product validation
-   Product availability
-   Timeout
-   Error handling

## Principle

Each service owns its own data.

``` text
Order Service   -> Order DB
Product Service -> Product DB
```

Order Service must not directly access Product Service's database.

**Status: PENDING**

------------------------------------------------------------------------

# 4. Pricing Service

## Objective

Extract pricing logic into a dedicated service.

## Topics

-   Product price
-   Discounts
-   Coupons
-   Taxes
-   Offers
-   Pricing rules
-   Pricing API
-   Dynamic pricing concepts
-   Strategy Pattern
-   Service-to-service communication
-   Failure handling
-   Caching considerations

**Status: PENDING**

------------------------------------------------------------------------

# 5. Payment Service

## Objective

Introduce payment processing as an independent service.

## Topics

-   Payment domain
-   Payment API
-   Payment states
-   Authorization
-   Capture
-   Payment failure
-   Payment retry
-   Payment timeout
-   Payment idempotency
-   Payment reference
-   External payment provider concept
-   Webhook concept
-   Duplicate payment prevention

Example:

``` text
INITIATED
    |
    v
AUTHORIZED
    |
    v
CAPTURED
```

Failure:

``` text
INITIATED
    |
    v
FAILED
```

**Status: PENDING**

------------------------------------------------------------------------

# 6. Strategy Pattern

## Objective

Use Strategy Pattern where business behavior varies dynamically.

Example:

``` text
PaymentStrategy
      |
      +---- CardPaymentStrategy
      |
      +---- WalletPaymentStrategy
      |
      +---- UpiPaymentStrategy
```

## Topics

-   Strategy Pattern
-   Interface-based design
-   Dependency Injection
-   Runtime strategy selection
-   Factory/Resolver concept
-   Open/Closed Principle
-   Avoiding large `if/else` blocks

**Status: PENDING**

------------------------------------------------------------------------

# 7. Inventory Service

## Objective

Extract inventory management into its own service.

## Topics

-   Inventory domain
-   Stock management
-   Stock reservation
-   Stock release
-   Stock deduction
-   Inventory API
-   Concurrent stock updates
-   Optimistic locking
-   Database locking
-   Overselling problem
-   Idempotent inventory operations

Example:

``` text
Available Stock = 1

Order A -> sees 1
Order B -> sees 1

Both reserve -> WRONG
```

The implementation must prevent overselling.

**Status: PENDING**

------------------------------------------------------------------------

# 8. State Machine

## Objective

Model the Order lifecycle explicitly.

Example:

``` text
CREATED
   |
   v
PAYMENT_PENDING
   |
   v
PAYMENT_SUCCESS
   |
   v
INVENTORY_RESERVED
   |
   v
CONFIRMED
   |
   v
SHIPPED
   |
   v
DELIVERED
```

Failure states:

``` text
PAYMENT_FAILED
INVENTORY_FAILED
CANCELLED
```

## Topics

-   State Machine
-   State transitions
-   Valid transitions
-   Invalid transitions
-   State transition validation
-   Business events
-   State persistence
-   Transition history

**Status: PENDING**

------------------------------------------------------------------------

# 9. Shipping Service

## Objective

Introduce shipment management.

## Topics

-   Shipping domain
-   Shipment creation
-   Address validation
-   Shipment status
-   Delivery tracking
-   Carrier integration concept
-   Estimated delivery
-   Shipment cancellation
-   Shipping failures
-   Shipping events

Example:

``` text
CONFIRMED
    |
    v
SHIPMENT_CREATED
    |
    v
SHIPPED
    |
    v
OUT_FOR_DELIVERY
    |
    v
DELIVERED
```

**Status: PENDING**

------------------------------------------------------------------------

# 10. Notification Service

## Objective

Create a dedicated notification service.

## Topics

-   Email
-   SMS
-   Push notification
-   Templates
-   Notification status
-   Retry
-   Dead Letter Queue
-   Asynchronous processing
-   Event-driven notifications
-   Idempotent notification handling

Example:

``` text
Order Event
     |
     v
Kafka
     |
     v
Notification Service
     |
     +----> Email
     +----> SMS
     +----> Push
```

**Status: PENDING**

------------------------------------------------------------------------

# 11. Microservices Architecture

## Objective

Complete the transition into a real microservices architecture.

## 11.1 Why Microservices?

Understand:

-   Why monoliths become difficult to scale
-   Independent deployment
-   Independent scaling
-   Team ownership
-   Fault isolation
-   Technology independence
-   Database ownership

## 11.2 Service Decomposition

``` text
API Gateway
     |
     +---- Order Service
     +---- Product Service
     +---- Pricing Service
     +---- Payment Service
     +---- Inventory Service
     +---- Shipping Service
     +---- Notification Service
```

## 11.3 Communication

-   Synchronous communication
-   Asynchronous communication
-   REST
-   HTTP
-   Kafka
-   Event-driven architecture
-   Request/response
-   Event-based communication

## 11.4 Distributed Data Ownership

``` text
Order DB
Product DB
Pricing DB
Payment DB
Inventory DB
Shipping DB
Notification DB
```

## 11.5 End-to-End Flow

Design the complete order flow before implementing the final distributed
architecture.

**Status: PARTIALLY COMPLETED**

------------------------------------------------------------------------

# 12. Service Discovery

## Objective

Remove hardcoded service URLs.

Avoid:

``` text
http://localhost:8081
http://localhost:8082
http://localhost:8083
```

Use service discovery:

``` text
Order Service
     |
     v
Service Discovery
     |
     v
Inventory Service
```

## Topics

-   Eureka
-   Service registration
-   Service discovery
-   Heartbeat
-   Health status
-   Client-side load balancing
-   Service-name based routing
-   Dynamic service instances

**Status: PENDING**

------------------------------------------------------------------------

# 13. API Gateway

## Objective

Create a production-level entry point for client requests.

## Gateway Modules

1.  Introduction
2.  Configuration
3.  Routing
4.  JWT Authentication
5.  Correlation ID
6.  Logging
7.  Rate Limiting
8.  Circuit Breaker
9.  Retry
10. Timeout
11. Request Validation
12. Header Manipulation
13. Path Rewriting
14. Load Balancing
15. Gateway Security
16. Production Gateway Project

## Correlation ID

Current design:

``` text
Client
  |
  | X-Correlation-ID
  v
API Gateway
  |
  | Generate if missing
  v
Correlation ID
```

The Gateway returns the correlation ID in the response.

### Correlation ID vs Propagation

These are two separate responsibilities:

``` text
Correlation ID creation
        +
Correlation ID propagation
```

For every new downstream HTTP call:

``` text
Service A
   |
   | X-Correlation-ID
   v
Service B
```

Use an HTTP client interceptor to automatically add the correlation ID.

The interceptor belongs in services that make outbound HTTP calls. It is
also reasonable to standardize this mechanism across services so future
outbound calls automatically get propagation.

## Logging

Use MDC:

``` java
MDC.put("correlationId", correlationId);
```

Configure the logging pattern to include the correlation ID.

## Rate Limiting

Topics:

-   Why rate limiting is needed
-   Requests per second
-   Client-based limiting
-   IP-based limiting
-   Token Bucket
-   Redis-based distributed rate limiting

## Circuit Breaker

Understand:

``` text
CLOSED
  |
  v
OPEN
  |
  v
HALF_OPEN
```

## Retry

Topics:

-   Retryable errors
-   Non-retryable errors
-   Maximum attempts
-   Backoff
-   Exponential backoff
-   Jitter
-   Retry storms

## Timeout

Every remote call should have an appropriate timeout.

Never allow downstream calls to wait indefinitely.

## JWT

Topics:

-   JWT
-   Access token
-   Claims
-   Signature
-   Expiration
-   Authentication
-   Authorization
-   Role-based authorization
-   Token validation
-   Security filters

**Status: IN PROGRESS**

### Already covered/implemented

-   Gateway project
-   Gateway configuration
-   Routing
-   Correlation ID generation
-   `OncePerRequestFilter`
-   Request wrapper
-   Response correlation header
-   MDC
-   Correlation ID logging concept
-   Understanding immutable `HttpServletRequest`
-   Understanding `HttpServletRequestWrapper`
-   Understanding `Enumeration`
-   Correlation propagation design

### Remaining

-   Final propagation implementation
-   JWT
-   Rate limiting
-   Circuit breaker
-   Retry
-   Timeout
-   Request validation
-   Header manipulation
-   Path rewrite
-   Load balancing
-   Gateway security
-   Production hardening

------------------------------------------------------------------------

# 14. Kafka

## Objective

Introduce asynchronous, event-driven communication.

## Core Topics

-   Kafka architecture
-   Broker
-   Topic
-   Partition
-   Producer
-   Consumer
-   Consumer Group
-   Offset
-   Replication
-   Leader
-   Follower
-   Retention
-   Ordering
-   Partition key
-   Consumer rebalancing

## Spring Kafka

-   `KafkaTemplate`
-   `@KafkaListener`
-   Producer configuration
-   Consumer configuration
-   Serialization
-   Deserialization
-   JSON events
-   Error handling

## Delivery Semantics

-   At-most-once
-   At-least-once
-   Exactly-once concepts

## Reliability

-   Retry
-   Dead Letter Topic
-   Error handling
-   Duplicate messages
-   Idempotent consumer

### Duplicate Kafka Message

Consumers should be idempotent:

``` text
Same event received multiple times
              |
              v
Business operation happens only once
```

**Status: PARTIALLY COVERED / IMPLEMENTATION PENDING**

------------------------------------------------------------------------

# 15. Idempotency

## Objective

Prevent duplicate business operations.

## Topics

-   Idempotency key
-   Duplicate requests
-   Duplicate Kafka events
-   Idempotent consumers
-   Database uniqueness
-   Payment idempotency
-   Order idempotency
-   Inventory idempotency
-   Retry-safe APIs

Example:

``` text
Client
  |
  | POST /orders
  | Idempotency-Key: ABC123
  v
Order Service
```

A retry using the same key should not create another order.

**Status: CONCEPT COVERED / FULL IMPLEMENTATION PENDING**

------------------------------------------------------------------------

# 16. Resilience Patterns

## Objective

Make the system survive partial failures.

## Topics

-   Timeout
-   Retry
-   Circuit Breaker
-   Bulkhead
-   Rate Limiting
-   Fallback
-   Load balancing
-   Backpressure
-   Graceful degradation

Example:

``` text
Order Service
      |
      v
Inventory Service
      |
      X
   Timeout
      |
      v
Retry / Failure Response / Fallback
```

Understand how these patterns interact rather than adding them blindly.

**Status: PENDING**

------------------------------------------------------------------------

# 17. Saga Pattern

## Objective

Handle distributed transactions across independent service databases.

Problem:

``` text
Order
  |
  v
Payment
  |
  v
Inventory
  |
  v
Shipping
```

If Inventory fails after Payment succeeds, compensation is required.

## Saga Flow

``` text
Create Order
     |
     v
Payment
     |
     v
Reserve Inventory
     |
     v
Create Shipment
```

Failure:

``` text
Inventory Failed
      |
      v
Refund Payment
      |
      v
Cancel Order
```

## Topics

-   Distributed transactions
-   Saga
-   Orchestration
-   Choreography
-   Compensation
-   Failure scenarios
-   Retry
-   Idempotency
-   Saga state

**Status: PENDING**

------------------------------------------------------------------------

# 18. Outbox Pattern

## Objective

Reliably combine database changes with event publishing.

Problem:

``` text
Update DB
   |
   X
Kafka publish fails
```

Database update succeeds but event is lost.

## Solution

``` text
Service
   |
   +----> Business DB
   |
   +----> Outbox Table
              |
              v
        Outbox Publisher
              |
              v
            Kafka
```

## Topics

-   Transactional Outbox
-   Outbox table
-   Event publishing
-   Polling publisher
-   CDC concept
-   Debezium concept
-   Duplicate events
-   Idempotent consumers
-   Retry
-   Event status

**Status: PENDING**

------------------------------------------------------------------------

# 19. Observability

## Objective

Make production issues diagnosable.

## Logging

-   Structured logging
-   Log levels
-   MDC
-   Correlation ID
-   Centralized logging
-   Error logging
-   Sensitive data masking

## Metrics

-   Request count
-   Error count
-   Latency
-   Throughput
-   CPU
-   Memory
-   Kafka lag
-   Database connection pool

## Distributed Tracing

``` text
Trace
  |
  +---- Span: Gateway
  |
  +---- Span: Order
  |
  +---- Span: Payment
  |
  +---- Span: Inventory
```

Topics:

-   OpenTelemetry
-   Trace ID
-   Span ID
-   Correlation ID
-   Metrics
-   Logs
-   Distributed tracing

**Status: PENDING**

------------------------------------------------------------------------

# 20. Security

## Objective

Secure the complete application.

## Topics

-   Spring Security
-   JWT
-   Authentication
-   Authorization
-   Roles
-   Permissions
-   Password hashing
-   Token expiration
-   Refresh token
-   API security
-   CORS
-   CSRF concepts
-   HTTPS/TLS
-   Secrets management
-   Sensitive information masking
-   Service-to-service authentication
-   API Gateway security

**Status: PARTIALLY COVERED**

------------------------------------------------------------------------

# 21. Testing

## Objective

Make the application production-safe through automated testing.

## Unit Testing

-   JUnit
-   Mockito
-   Service tests
-   Controller tests
-   Repository tests

## Integration Testing

-   Spring Boot Test
-   Testcontainers
-   Real database testing
-   Kafka integration testing

## API Testing

-   Postman
-   Swagger/OpenAPI
-   REST API validation

## Contract Testing

-   Consumer-driven contracts
-   Service compatibility
-   API version compatibility

## Performance Testing

-   Load testing
-   Stress testing
-   Spike testing
-   Endurance testing

**Status: PARTIALLY COVERED / PENDING**

------------------------------------------------------------------------

# 22. Docker

## Objective

Containerize every microservice.

## Topics

-   Docker image
-   Dockerfile
-   Container
-   Port mapping
-   Environment variables
-   Docker network
-   Docker Compose
-   Multi-stage builds
-   Image optimization
-   Container health checks

## Local Environment

``` text
Docker Compose
     |
     +---- API Gateway
     +---- Order Service
     +---- Product Service
     +---- Pricing Service
     +---- Payment Service
     +---- Inventory Service
     +---- Shipping Service
     +---- Notification Service
     +---- Kafka
     +---- Database
```

**Status: PENDING**

------------------------------------------------------------------------

# 23. Kubernetes

## Objective

Deploy the application using Kubernetes.

## Topics

-   Pod
-   Deployment
-   Service
-   ConfigMap
-   Secret
-   Namespace
-   Ingress
-   Service Discovery
-   Replicas
-   Rolling deployment
-   Health checks
-   Liveness probe
-   Readiness probe
-   Resource requests
-   Resource limits
-   Horizontal Pod Autoscaler
-   Persistent storage
-   Kubernetes networking

## Deployment Flow

``` text
Docker Image
     |
     v
Container Registry
     |
     v
Kubernetes
     |
     +---- Deployment
     +---- Service
     +---- ConfigMap
     +---- Secret
     +---- Ingress
```

**Status: PENDING**

------------------------------------------------------------------------

# 24. CI/CD

## Objective

Automatically build, test, package and deploy the application.

## Pipeline

``` text
Git Push
   |
   v
Build
   |
   v
Unit Tests
   |
   v
Integration Tests
   |
   v
Docker Build
   |
   v
Security Scan
   |
   v
Push Image
   |
   v
Deploy
   |
   v
Kubernetes
```

## Topics

-   Git
-   Branching strategy
-   Pull Requests
-   Code review
-   Build pipeline
-   Automated testing
-   Docker image build
-   Container registry
-   Deployment
-   Rollback
-   Environment management

## Tools

-   Git
-   GitHub/GitLab
-   Jenkins/GitLab CI
-   Docker
-   Kubernetes

**Status: PENDING**

------------------------------------------------------------------------

# 25. AWS Cloud Deployment

## Objective

Deploy the complete application to AWS.

## AWS Fundamentals

-   IAM
-   VPC
-   Subnet
-   Route Table
-   Internet Gateway
-   NAT Gateway
-   Security Group
-   Load Balancer
-   DNS
-   CloudWatch

## Compute

-   EC2
-   ECS
-   EKS
-   Lambda
-   Container deployment

## Database

-   RDS
-   Aurora
-   DynamoDB
-   Backups
-   Read replicas
-   Multi-AZ

## Messaging

-   Amazon MSK
-   SQS
-   SNS

## Storage

-   S3

## Secrets

-   AWS Secrets Manager
-   Parameter Store

## Monitoring

-   CloudWatch
-   Logs
-   Metrics
-   Alarms

## Target Cloud Architecture

``` text
                    Internet
                       |
                       v
                  Load Balancer
                       |
                       v
                     EKS
                       |
                       v
                  API Gateway
                       |
        +--------------+--------------+
        |              |              |
        v              v              v
      Order         Product        Payment
     Service        Service        Service
        |              |              |
        v              v              v
     Order DB       Product DB     Payment DB
        |
        v
      Kafka
        |
   +----+---------+-------------+
   |              |             |
   v              v             v
Inventory      Shipping     Notification
 Service        Service       Service
```

**Status: PENDING**

------------------------------------------------------------------------

# 26. Production Architecture

## Objective

Bring all concepts together.

## Final Logical Architecture

``` text
                         CLIENT
                           |
                           v
                    LOAD BALANCER
                           |
                           v
                     API GATEWAY
                           |
       +-------------------+-------------------+
       |                   |                   |
       v                   v                   v
 ORDER SERVICE       PRODUCT SERVICE     PAYMENT SERVICE
       |
       +---- HTTP ----> Product Service
       |
       +---- HTTP ----> Pricing Service
       |
       +---- HTTP ----> Payment Service
       |
       +---- HTTP ----> Inventory Service
       |
       v
 Order State Machine
       |
       v
     Outbox
       |
       v
      Kafka
       |
       +----> Shipping Service
       |
       +----> Notification Service
       |
       +----> Other Consumers
```

Every service owns its own database.

------------------------------------------------------------------------

# 27. Production Engineering

## Reliability

-   Retry
-   Timeout
-   Circuit Breaker
-   Bulkhead
-   Idempotency
-   Dead Letter Queue
-   Graceful degradation

## Data Consistency

-   Saga
-   Outbox
-   Eventual consistency
-   Distributed transactions
-   Compensation

## Security

-   JWT
-   Authorization
-   HTTPS
-   Secrets
-   Service authentication
-   Input validation

## Observability

-   Logs
-   Metrics
-   Traces
-   Correlation ID
-   Trace ID
-   Alerts

## Performance

-   Database indexing
-   Query optimization
-   Caching
-   Connection pools
-   Kafka partitioning
-   Async processing
-   Load balancing

## Scalability

-   Horizontal scaling
-   Stateless services
-   Kubernetes replicas
-   Autoscaling
-   Kafka partitions

## Deployment

-   Docker
-   Kubernetes
-   CI/CD
-   Rolling deployments
-   Rollbacks
-   Health checks

------------------------------------------------------------------------

# 28. Production Troubleshooting

## Scenarios to Practice

1.  API is slow
2.  Database is slow
3.  Kafka consumer is lagging
4.  Kafka message is duplicated
5.  Payment succeeds but order fails
6.  Inventory reservation fails
7.  Service is unavailable
8.  Gateway returns 502/503
9.  Database connection pool is exhausted
10. Memory usage increases
11. CPU spikes
12. Kubernetes pod keeps restarting
13. Deployment fails
14. Downstream service times out
15. Correlation ID is missing
16. Logs cannot be traced across services
17. Duplicate order is created
18. Duplicate payment occurs
19. Kafka consumer crashes
20. Database transaction rolls back

## Senior Engineer Troubleshooting Flow

``` text
Issue
  |
  v
Correlation ID / Trace ID
  |
  v
Logs
  |
  v
Metrics
  |
  v
Distributed Trace
  |
  v
Service
  |
  v
Database / Kafka
  |
  v
Root Cause
  |
  v
Fix
  |
  v
Prevention
```

------------------------------------------------------------------------

# 29. Current Progress

## Completed / Covered

### Foundation

-   Simple Order Service
-   Spring Boot structure
-   REST Controller
-   Service
-   Repository
-   Entity
-   DTO
-   Database integration
-   Basic order creation
-   Basic order retrieval

### API Gateway

-   Gateway project
-   Gateway configuration
-   Routing
-   Correlation ID generation
-   `OncePerRequestFilter`
-   Request wrapper
-   Response correlation header
-   MDC
-   Correlation ID logging concept
-   Immutable `HttpServletRequest`
-   `HttpServletRequestWrapper`
-   `Enumeration`
-   Correlation ID propagation architecture
-   Understanding that each new downstream HTTP call needs propagation

### Microservices Concepts

-   Why microservices
-   Service decomposition
-   Product Service extraction concept
-   Inventory Service concept
-   Payment Service concept
-   Shipping Service concept
-   Notification Service concept
-   Distributed data ownership concept
-   Synchronous vs asynchronous communication

### Distributed Systems Concepts

-   Idempotency concept
-   Strategy Pattern concept
-   Duplicate Kafka message concept
-   Idempotent consumer concept

------------------------------------------------------------------------

# 30. Currently In Progress

## API Gateway

Current focus:

-   Correlation ID
-   Correlation ID propagation
-   HTTP interceptor
-   Logging

Next Gateway work:

-   JWT
-   Rate limiting
-   Circuit breaker
-   Retry
-   Timeout
-   Request validation
-   Header manipulation
-   Path rewrite
-   Load balancing
-   Security hardening

## Order Service

Upgrade the existing simple implementation to production quality.

------------------------------------------------------------------------

# 31. Pending

## Business Services

-   Product Service
-   Pricing Service
-   Payment Service
-   Inventory Service
-   Shipping Service
-   Notification Service

## Architecture

-   Service Discovery
-   Complete API Gateway
-   Distributed data ownership implementation
-   End-to-end service communication

## Distributed Systems

-   Kafka implementation
-   Idempotency implementation
-   Saga Pattern
-   Outbox Pattern
-   State Machine
-   Event-driven architecture
-   Eventual consistency

## Production Engineering

-   Distributed tracing
-   Metrics
-   Centralized logging
-   Security hardening
-   Automated testing
-   Performance testing
-   Docker
-   Kubernetes
-   CI/CD
-   AWS deployment
-   Monitoring
-   Alerting
-   Production troubleshooting

------------------------------------------------------------------------

# 32. Recommended Execution Order

``` text
1.  Simple Order Service                    [DONE]
        |
2.  Production Order Service               [CURRENT]
        |
3.  Product Service
        |
4.  Pricing Service
        |
5.  Payment Service
        |
6.  Strategy Pattern
        |
7.  Inventory Service
        |
8.  State Machine
        |
9.  Shipping Service
        |
10. Notification Service
        |
11. Microservices Architecture
        |
12. Service Discovery
        |
13. API Gateway
        |
14. JWT Security
        |
15. Correlation ID + Propagation
        |
16. Logging + Observability
        |
17. Resilience
        |
18. Kafka
        |
19. Idempotency + Duplicate Handling
        |
20. Saga Pattern
        |
21. Outbox Pattern
        |
22. Distributed Data Ownership
        |
23. Automated Testing
        |
24. Docker
        |
25. Kubernetes
        |
26. CI/CD
        |
27. AWS
        |
28. Production Architecture
        |
29. Monitoring + Alerting
        |
30. Production Troubleshooting
        |
        v
PRODUCTION-READY CLOUD APPLICATION
```

------------------------------------------------------------------------

# 33. Final End-to-End Target

The final order flow should look approximately like this:

``` text
Client
   |
   v
Load Balancer
   |
   v
API Gateway
   |
   | JWT validation
   | Correlation ID
   | Rate limiting
   | Timeout
   | Circuit breaker
   |
   v
Order Service
   |
   +---- HTTP ----> Product Service
   |
   +---- HTTP ----> Pricing Service
   |
   +---- HTTP ----> Payment Service
   |
   +---- HTTP ----> Inventory Service
   |
   v
Order State Machine
   |
   v
Outbox
   |
   v
Kafka
   |
   +----> Shipping Service
   |
   +----> Notification Service
   |
   +----> Other Consumers
```

Cross-cutting concerns:

``` text
Security
   +
Correlation ID
   +
Distributed Tracing
   +
Centralized Logging
   +
Metrics
   +
Resilience
   +
Idempotency
   +
Saga
   +
Outbox
   +
Kafka
   +
Docker
   +
Kubernetes
   +
CI/CD
   +
AWS
   =
Production-Ready Cloud Application
```

------------------------------------------------------------------------

# 34. Learning Method for Every Chapter

For every major topic, follow this sequence:

``` text
1. Why do we need it?
2. What problem does it solve?
3. Where does it fit in our architecture?
4. HLD
5. LLD
6. Request/Event flow
7. Failure scenarios
8. Production considerations
9. Implementation
10. Testing
11. Debugging
12. Interview explanation
```

Do not move to the next major topic until the current topic is
understood, implemented and tested in the application.

------------------------------------------------------------------------

# Final Goal

By completing this roadmap, the project should not just be a collection
of Spring Boot microservices.

It should demonstrate that you can:

-   Design microservices
-   Define service boundaries
-   Design APIs
-   Design databases
-   Implement synchronous communication
-   Implement asynchronous communication
-   Handle distributed transactions
-   Handle failures
-   Build resilient services
-   Secure APIs
-   Trace requests across services
-   Debug production issues
-   Containerize applications
-   Deploy to Kubernetes
-   Build CI/CD pipelines
-   Deploy to AWS
-   Monitor production systems
-   Explain the architecture as a Senior Backend Engineer
