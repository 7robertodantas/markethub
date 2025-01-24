# MarketHub

**MarketHub** is a backend project designed to simulate a commerce hub with an integrated payment wallet. It serves as a platform for studying and testing concepts related to **event-driven architecture**, **distributed systems**, and **communication between services**.

This project is implemented in **Java** and uses **Maven** (`pom.xml`) for dependency management and build processes. It comprises three main services:

1. **Product Service**: Manages product listing and inventory control.
2. **Checkout Service**: Handles purchases and redemptions.
3. **Wallet Service**: Manages user point balances and transaction history.

## Getting Started

Follow the steps below to set up and run the project on your local machine.

### Prerequisites

Ensure the following tools are installed on your system:

- **Java 17** or higher
- **Maven** (optional for building the project)
- **Docker** and **Docker Compose** (for running the services)

### Installation

1. Clone the repository:
   ```shell
   git clone https://github.com/7robertodantas/markethub.git
   cd markethub
   ```

2. Build the projects using docker-compose:
   ```shell
   docker-compose build
   ```

3. Ensure Docker and Docker Compose are installed and running.

---

## Running the Project

The project is containerized and can be run using **Docker Compose**. This will spin up all three services along with necessary dependencies.

1. Navigate to the root of the repository.
2. Start the services:
   ```
   docker-compose up
   ```
3. Access the services on the following ports:
   - **Product Service**: `http://localhost:8081`
   - **Checkout Service**: `http://localhost:8082`
   - **Wallet Service**: `http://localhost:8083`

---

## Project Structure

The project is organized into three main services, each with its own module:

- **`product/`**:
  - Handles product listings and inventory management.
  - Reacts to `checkout_done` events to update stock.

- **`checkout/`**:
  - Manages the submission and status of checkouts.
  - Emits events for successful or failed transactions.

- **`wallet/`**:
  - Maintains user balances and transaction histories.
  - Reacts to `checkout_submitted` events to validate and adjust balances.

Each service contains its own `pom.xml`, configurations, and business logic.

---

## Features

- **Event-Driven Architecture**:
  - Services communicate via events to ensure decoupled and scalable interactions.
  
- **Distributed System**:
  - Each service is autonomous and handles specific responsibilities.

- **REST API**:
  - Exposes endpoints for interaction with each service.

- **Dockerized Deployment**:
  - Easy setup and execution using Docker Compose.

---

## Troubleshooting

### Common Issues

1. **Docker Service Not Starting**
   - Ensure Docker is running and that you have sufficient permissions to execute Docker commands.

2. **Port Conflicts**
   - Check if the default ports (`8081`, `8082`, `8083`) are already in use. Update `docker-compose.yml` to resolve conflicts.

3. **Service Communication Issues**
   - Verify that all containers are running and accessible.
   - Check the logs for each service to identify connection or event handling problems:
     ```shell
     docker-compose logs <service-name>
     ```

4. **Build Errors**
   - Ensure Java and Maven are installed correctly and the environment variables (`JAVA_HOME`, `MAVEN_HOME`) are properly configured.

---

If you encounter further issues or have questions, feel free to open an issue in this repository. Contributions and suggestions are always welcome! 😊
