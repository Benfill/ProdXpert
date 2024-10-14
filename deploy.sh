#!/bin/bash

# Variables
PROJECT_DIR="/home/mhachami/Desktop/projects/A2_brief/ProdXpert"
TOMCAT_DIR="/opt/apache-tomcat-9.0.95"
TOMCAT_WEBAPPS_DIR="$TOMCAT_DIR/webapps" 

build_project() {
    echo "Building the project..."
    cd "$PROJECT_DIR" || exit
    mvn clean package
    if [ $? -ne 0 ]; then
        echo "Build failed. Exiting."
        exit 1
    fi
    echo "Build successful."
}

copy_war_file() {
    echo "Copying WAR file to Tomcat webapps directory..."
    
    WAR_FILE="$PROJECT_DIR/target/*.war" 

    WAR_FILE_PATH=$(find $WAR_FILE -type f | head -n 1)

    if [ -z "$WAR_FILE_PATH" ]; then
        echo "WAR file not found in $PROJECT_DIR/target."
        exit 1
    fi

    APP_NAME=$(basename "$WAR_FILE_PATH" .war)
    if [ -d "$TOMCAT_WEBAPPS_DIR/$APP_NAME" ]; then
        echo "Removing old application version..."
        rm -rf "$TOMCAT_WEBAPPS_DIR/$APP_NAME"
    fi

    if [ -f "$TOMCAT_WEBAPPS_DIR/$APP_NAME.war" ]; then
        echo "Removing old WAR file..."
        rm "$TOMCAT_WEBAPPS_DIR/$APP_NAME.war"
    fi

    cp "$WAR_FILE_PATH" "$TOMCAT_WEBAPPS_DIR"
    echo "WAR file copied successfully."
}

build_project
copy_war_file
