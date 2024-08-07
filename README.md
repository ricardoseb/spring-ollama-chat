# Spring AI - Analyzes pdf files and images

This project demonstrates the integration of Spring AI with Ollama, utilizing the Llava model for AI-powered functionalities.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Features](#features)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

Before you begin, ensure you have met the following requirements:
- Java JDK 22
- Maven
- [Ollama](https://ollama.com/download) installed and running on your system
- Llava model downloaded and available in Ollama

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/ricardoseb/spring-ollama-chat.git
   ```

2. Navigate to the project directory:
   ```
   cd spring-ollama-chat
   ```

3. Build the project:
   ```
   ./mvnw clean install
   ```

## Configuration

1. Open `application.properties` (or `application.yml`) and configure the Ollama endpoint:
   ```
   spring.ai.ollama.model=llava:13b
   ```

2. Adjust any other settings as needed for your specific use case.

## Usage

Describe how to run and use your application. For example:

1. Start the Spring Boot application:
   ```
   ./mvnw spring-boot:run
   ```

2. Access the API endpoints (provide examples of available endpoints and how to use them).

## Features

List and briefly describe the main features of your project. For example:
- Image analysis using Llava model
- Natural language processing capabilities
- Integration with Spring AI for seamless AI operations

## Contributing

Contributions to this project are welcome. Please follow these steps:
1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature-name`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature-name`)
5. Create a new Pull Request

## License

This project is licensed under the [MIT License](LICENSE).
