# SQL Injection Vulnerable Project (Spring Boot + PostgreSQL)

This project is an intentionally vulnerable **Java Spring Boot** web application designed to demonstrate and experiment with different types of **SQL Injection (SQLi)** attacks. It was developed as part of the coursework *"SQL Injection Attack on Web Application Database"* and serves as a compact, practical lab environment for understanding SQLi techniques in a real backend–database setup.

> ⚠️ **Warning:** This application is deliberately insecure. Use it **only** in a controlled, local environment.

---

## Overview

The application simulates a simple web system backed by a PostgreSQL database. It includes:

* A **login page** vulnerable to classic SQL injection.
* A **personal page** allowing error-based and union-based SQLi.
* A **products filter page** vulnerable to boolean‑based and time‑based SQLi.
* A **user information page** demonstrating second‑order SQLi.

The backend uses deliberately unsafe SQL query construction (string concatenation) to expose practical attack vectors.

---

## Purpose

The project aims to:

* Demonstrate how SQL injection works on a real Java Spring Boot + PostgreSQL stack.
* Provide reproducible scenarios of:

  * **Classic SQLi** (authentication bypass)
  * **Error‑based SQLi** (leaking database structure)
  * **Union‑based SQLi** (extracting data)
  * **Boolean‑based SQLi** (true/false behavior)
  * **Time‑based SQLi** (delays via `pg_sleep`)
  * **Second‑order SQLi** (stored payloads executed later)
* Support security students, penetration testers, and developers in learning how insecure SQL handling leads to vulnerabilities.

---

## Technology Stack

**Backend:** Java Spring Boot (MVC)
**Database:** PostgreSQL
**Build Tool:** Maven
**Views:** HTML/CSS server‑rendered templates
**Environment:** Local, isolated testing setup

---

## Key Vulnerable Features

### 1. Login Page – Classic SQLi

Demonstrates authentication bypass using payloads such as `' OR 1=1--`.

### 2. Personal Page – Error‑Based & Union‑Based SQLi

Allows attackers to:

* Trigger SQL errors to infer database structure.
* Use `UNION SELECT` to enumerate tables, columns, and extract user data.

### 3. Products Filter Page – Blind SQLi

Shows:

* Boolean‑based SQLi via conditional expressions.
* Time‑based SQLi using `pg_sleep()` to leak information through response timing.

### 4. User Info Page – Second‑Order SQLi

Stored payloads entered during registration are later executed when reused in SQL queries.

---

## How to Run

### Requirements

* Java 11+
* Maven
* PostgreSQL

### Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/AlikhanBegzhan/SQL-Injection-vulnerable-project
   ```
2. Configure your PostgreSQL database and update `application.properties` with your credentials.
3. Create the required tables (`users`, `products`, etc.) using SQL scripts or manually.
4. Start the application:

   ```bash
   mvn spring-boot:run
   ```
5. Access the app at: **[http://localhost:8080](http://localhost:8080)**

---

## Educational Notes

This project provides a hands‑on environment to:

* Understand SQL injection mechanics.
* Study the impact of insecure SQL query construction.
* Practice extracting data, enumerating schemas, and analysing DB responses.
* Explore how small backend mistakes lead to full compromise.
