# Spring AI - Analyzes pdf files and images for free and without limits

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

- 8 GB Ram, 16 GB or + preferred
- [Java 22](https://jdk.java.net/22/) recommended
- [Maven](https://maven.apache.org/download.cgi) version 3.9.0 or higher (Ideally installed for command line use)
- [Ollama](https://ollama.com/download) installed and running on your system
- Llava model downloaded and available in Ollama for image analysis
- Llama3.1 model downloaded and available in Ollama for general knowledge
- [Redis Cloud](https://app.redislabs.com/#/)

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

1. Set up the Redis URI environment variable: Open `Edit Configurations...` and configure the required environment variable.
2. This project is pre-configured for Ollama models. If you need to set up any other model, open the application.properties (or application.yml) file and configure the desired model there.
   ```
   spring.ai.ollama.model=desiredModel
   ```

3. Adjust any other settings as needed for your specific use case.

## Usage

Describe how to run and use your application. For example:

1. Start the Spring Boot application:
   ```
   ./mvnw spring-boot:run
   ```

2. Access the API endpoints (provide examples of available endpoints and how to use them).

## Features

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
