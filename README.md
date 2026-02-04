# Java 17 ETL Pipeline: RIF Tax Calculations

###  Project Overview
This project is a specialized **ETL (Extract, Transform, Load)** pipeline built with **Java 17**. It automates the fiscal calculations for the Mexican RIF (*Régimen de Incorporación Fiscal*), processing semi-structured data from Excel into actionable financial reports.

### Tech Stack
* **Language:** Java 17.
* **Build Tool:** Maven.
* **Key Libraries:**
    * **Apache POI:** Used for high-performance extraction of data from `.xlsx` files.
    * **Log4j:** Implemented for robust internal logging and error tracking.
* **Design Pattern:** ETL Architecture.

###  Key Features
* **Extract:** Automated reading of taxpayer financial records (Income, Expenses, Tenure).
* **Transform:** Implementation of complex fiscal business logic, specifically calculating the gradual 10% annual ISR discount according to RIF regulations.
* **Load:** Generation of processed reports via console output (scalable to Database/API integration).

### How to Run
1. Clone this repository.
2. Ensure you have **Java 17** and **Maven** installed.
3. Place your data file named `datos_rif.xlsx` in the root directory (same level as `pom.xml`).
4. Run the `Main.java` class from your IDE or use `mvn clean install`.