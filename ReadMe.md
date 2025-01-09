# Cubism Image Generator API

This project is a backend application that takes an prompt as input and generates a cubism-style version of the image using Stable Diffusion XL.
## API Endpoints

### 1. Text-to-Image
- **POST** `/api/images/generate`
- **Body**: `{ "prompt": "your text description" }`
- Generates a cubist style image from text description

### 2. Image-to-Image
- **POST** `/api/images/transform`
- **Form-data**: `image: file`
- Transforms uploaded image into cubist art style

## Limitations
- Maximum image size: 4MB
- Supported image formats: JPEG, PNG
- API token required for requests

## Web Interface
Application runs at `http://localhost:8080` and provides two main features:
- Generate images from text input
- Transform uploaded images

## How It Works
1. **Text-to-Image**: Uses Stable Diffusion XL to generate cubist artwork from text descriptions
2. **Image-to-Image**: 
   - Analyzes uploaded image using BLIP model
   - Generates description of the image
   - Creates cubist version using Stable Diffusion XL

## License
MIT