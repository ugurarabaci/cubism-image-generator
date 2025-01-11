# Cubism Art Generator 🎨

Live Demo: [https://cubism-image-generator-production.up.railway.app/](https://cubism-image-generator-production.up.railway.app/)

## Overview

Cubism Art Generator is a web application that transforms images into Cubism-style artworks using AI. It offers two main functionalities:

1. **Text to Image**: Generate Cubism art from text descriptions
2. **Image to Image**: Transform existing images into Cubism style

## Features

- 🖼️ Two generation methods:
   - Text-to-Image generation
   - Image-to-Image transformation
- 🎯 User-friendly interface with drag-and-drop support
- 📱 Responsive design that works on both desktop and mobile
- ⬇️ Easy image download functionality
- 🚀 Fast processing using Hugging Face's state-of-the-art models

## Tech Stack

- **Backend**: Spring Boot 3.x
- **Frontend**: HTML5, Tailwind CSS, JavaScript
- **AI Model**: Hugging Face Stable Diffusion
- **Deployment**: Docker, Railway

## Local Development

### Prerequisites

- Java 17 or higher
- Maven
- Docker (optional)

### Running Locally

1. Clone the repository:
```bash
git clone https://github.com/ugurarabaci/cubism-image-generator.git
cd cubism-image-generator
```

2. Set up environment variables:
```bash
HUGGINGFACE_API_TOKEN=your_token
```

3. Build and run:
```bash
mvn clean package
java -jar target/CubismImageGenerator-0.0.1-SNAPSHOT.jar
```

### Using Docker

```bash
docker build -t cubism-image-generator .
docker run -p 8080:8080 -e HUGGINGFACE_API_TOKEN=your_token cubism-image-generator
```

## API Endpoints

- `POST /api/images/generate`: Generate image from text
- `POST /api/images/transform`: Transform uploaded image

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.