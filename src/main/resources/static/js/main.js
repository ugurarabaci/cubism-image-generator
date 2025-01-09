class CubismApp {
    constructor() {
        this.initializeElements();
        this.attachEventListeners();
    }

    initializeElements() {
        // ... (mevcut element seçicileri)
    }

    attachEventListeners() {
        // ... (mevcut event listener'lar)
    }

    async handleImageUpload(file) {
        try {
            ImageUtils.validateFile(file);
            const compressedFile = await ImageUtils.compressImage(file);
            this.showPreview(compressedFile);
        } catch (error) {
            this.showError(error.message);
        }
    }

    showPreview(file) {
        const reader = new FileReader();
        reader.onload = (e) => {
            this.imagePreview.src = e.target.result;
            this.previewContainer.classList.remove('hidden');
            this.previewContainer.classList.add('slide-up');
        };
        reader.readAsDataURL(file);
    }

    // ... (diğer metodlar)
}

// Initialize app
document.addEventListener('DOMContentLoaded', () => {
    new CubismApp();
}); 