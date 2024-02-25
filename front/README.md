# MDD App (Monde de Dév) Angular Frontend Project (MVP (Minimum Viable Product))

This repository contains an Angular Frontend for the MDD MVP Project.

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 17.0.0.

## Table of Contents

- [MDD App Angular Frontend Project](#mdd-app-monde-de-dév-angular-frontend-project-mvp-minimum-viable-product)
  - [Contents](#table-of-contents)
  - [Prerequisite Requirements](#prerequisite-requirements)
  - [Installation Guide](#installation-guide)
  - [Project Architecture](#project-architecture)

## Prerequisite Requirements

### 1. Node Package Manager (NPM)

**Installing NPM:**

Before working with the MDD Angular Frontend, ensure that Node Package Manager (NPM) is installed on your system. NPM is essential for managing dependencies and running scripts. Follow the steps below:

1. Download and install [Node.js](https://nodejs.org/).
2. Confirm the successful installation by running the following commands in your terminal or command prompt:

```shell
node -v
npm -v
```

You should see versions for both Node.js and NPM.

### 2. Angular CLI

**Installing Angular CLI:**

Angular CLI is a command-line interface for Angular applications. Install the latest version globally using the following command:

```shell
npm install -g @angular/cli
```

Confirm the successful installation by running:

```shell
ng --version
```

You should see information about the installed Angular CLI version

## Installation Guide

**Cloning the project:**

1. Clone this repository from GitHub: `git clone https://github.com/Mickael-Klein/OpenClassRooms-Dev-FullStack-Projet_6.git`

2. Navigate to front folder

```shell
cd front
```

3. Install dependencies

```shell
npm install
```

**This project works with the API provided in the Backend part of the application, don't forget to install it and run it before running the Frontend.**

4. Launch Frontend

```shell
npm start
```

## Project Architecture

```
├───app
│   ├───component
│   │   ├───button
│   │   ├───comment
│   │   ├───header
│   │   ├───nav
│   │   ├───post-card
│   │   └───theme-card
│   ├───core
│   │   ├───model
│   │   └───service
│   │       ├───api
│   │       │   ├───common
│   │       │   └───interface
│   │       │       ├───comment
│   │       │       │   ├───request
│   │       │       │   └───response
│   │       │       ├───post
│   │       │       │   ├───request
│   │       │       │   └───response
│   │       │       ├───subject
│   │       │       ├───user
│   │       │       │   ├───request
│   │       │       │   └───response
│   │       │       └───userAuth
│   │       │           ├───request
│   │       │           └───response
│   │       ├───route
│   │       └───session
│   ├───enviroment
│   ├───guard
│   ├───interceptor
│   ├───interface
│   ├───page
│   │   ├───article
│   │   ├───articles
│   │   ├───home
│   │   ├───login
│   │   ├───me
│   │   ├───new-article
│   │   ├───not-found
│   │   ├───register
│   │   └───theme
│   └───style
└───assets
```
