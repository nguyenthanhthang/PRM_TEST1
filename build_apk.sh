#!/bin/bash

# Build script for Danh sách món ăn Android App
echo "Building APK for Danh sách món ăn app..."

# Clean and build debug APK
./gradlew clean assembleDebug

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "📱 APK location: app/build/outputs/apk/debug/app-debug.apk"
    echo "📁 You can find the APK in the app/build/outputs/apk/debug/ directory"
else
    echo "❌ Build failed. Please check the error messages above."
    exit 1
fi
