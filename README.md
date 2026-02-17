# Java 17 ETL Pipeline: RIF Tax Calculations

## 1. Project Overview
This project implements a specialized **ETL (Extract, Transform, Load)** pipeline developed in **Java 17**. Its primary objective is the automation of fiscal calculations for the Mexican **Régimen de Incorporación Fiscal (RIF)**. The system processes taxpayer data from Excel workbooks and generates structured financial reports based on official tax brackets.

## 2. Business Logic and Fiscal Context

### 2.1 Overview of RIF (2014–2021)
The Régimen de Incorporación Fiscal was a tax scheme in Mexico designed for small taxpayers and individuals with business activities. The calculation logic integrated into this software adheres to the following principles:

* **Cash Flow Basis:** Taxable profit is determined by subtracting effectively paid expenses from effectively collected income within a two-month period (bimestral).
* **Progressive Tax Brackets:** Instead of a fixed percentage, the Income Tax (ISR) is calculated using a progressive table consisting of lower limits, fixed fees, and surplus percentages.
* **Decreasing Fiscal Stimulus:** A significant feature of this regime was a tax reduction stimulus that granted a 100% discount during the first year of operations, decreasing by 10 percentage points annually over a 10-year period.

### 2.2 Calculation Formula
The transformation engine follows the standard fiscal algorithm:
1. **Taxable Profit** = Income - Deductible Expenses.
2. **Gross ISR** = ((Taxable Profit - Lower Limit) * Surplus Percentage) + Fixed Fee.
3. **Net ISR to Pay** = Gross ISR * (1 - Reduction Percentage based on Year).

### 3. Legal Basis and Reference Data
* **Primary Reference:** Annex 8 of the Miscellaneous Tax Resolution (RMF).
* **Source Document:** The official PDF is included in this repository at `./docs/Anexo+8+RMF+2021+DOF+11012021.pdf`.
* **Data Precision:** The system utilizes the "Tarifa aplicable a los pagos bimestrales" for both **Period 1** and **Period 2**.

## 4. Technical Stack
* **Language:** Java 17.
* **Build Tool:** Maven.
* **Libraries:** * **Apache POI:** For extracting data from .xlsx files.
    * **Log4j:** For application logging and error management.
* **Architecture:** ETL (Extract, Transform, Load).

### 5. Pipeline Stages
1. **Extract:** Automated ingestion of `data_rif.xlsx`. The system iterates through all workbook sheets; each sheet represents a specific tax period (e.g., Sheet 1 = Bimestre 1).
2. **Transform:** * Range-based search within tax brackets indexed by period.
    * Application of the decreasing 10% annual stimulus.
3. **Load:** Structured console output with aligned financial data, showing Gross ISR, Stimulus, and Net ISR.

## 6. Setup and Execution
1. Clone the repository.
2. Ensure **Java 17** and **Maven** are configured in the environment.
3. Verify that the input file `data_rif.xlsx` is located in the root directory.
4. Execute via IDE or terminal using `mvn clean install`.

---
*Note: This implementation is currently hosted on the `feature/anexo8-logic` branch for advanced tax bracket integration.*

---

# Pipeline ETL en Java 17: Cálculo de Impuestos RIF

## 1. Resumen del Proyecto
Este proyecto implementa un pipeline **ETL (Extracción, Transformación y Carga)** especializado, desarrollado en **Java 17**. Su objetivo principal es la automatización de los cálculos fiscales para el **Régimen de Incorporación Fiscal (RIF)** en México. El sistema procesa datos de contribuyentes desde libros de Excel y genera reportes financieros estructurados basados en las tarifas oficiales.

## 2. Lógica de Negocio y Contexto Fiscal

### 2.1 Descripción del RIF (2014–2021)
El Régimen de Incorporación Fiscal fue un esquema tributario en México diseñado para pequeños contribuyentes y personas físicas con actividades empresariales. La lógica de cálculo integrada en este software se adhiere a los siguientes principios:

* **Base de Flujo de Efectivo:** La utilidad gravable se determina restando los gastos efectivamente pagados de los ingresos efectivamente cobrados en un periodo bimestral.
* **Tarifas Progresivas:** En lugar de un porcentaje fijo, el Impuesto Sobre la Renta (ISR) se calcula utilizando una tabla progresiva que consta de límites inferiores, cuotas fijas y porcentajes sobre el excedente.
* **Estímulo Fiscal Decreciente:** Una característica clave de este régimen fue un estímulo de reducción de impuestos que otorgaba un descuento del 100% durante el primer año de operaciones, disminuyendo 10 puntos porcentuales anualmente durante un periodo de 10 años.

### 2.2 Fórmula de Cálculo
El motor de transformación sigue el algoritmo fiscal estándar:
1. **Utilidad Gravable** = Ingresos - Gastos Deducibles.
2. **ISR Causado** = ((Utilidad Gravable - Límite Inferior) * Porcentaje sobre Excedente) + Cuota Fija.
3. **ISR Neto a Pagar** = ISR Causado * (1 - Porcentaje de Reducción según el Año).

### 3. Fundamento Legal y Datos de Referencia
* **Referencia Primaria:** Anexo 8 de la Resolución Miscelánea Fiscal (RMF).
* **Documento Fuente:** `./docs/Anexo+8+RMF+2021+DOF+11012021.pdf`.
* **Precisión de Datos:** El sistema utiliza las tarifas bimestrales para el **Bimestre 1** y **Bimestre 2**.

## 4. Stack Técnico
* **Lenguaje:** Java 17.
* **Herramienta de Construcción:** Maven.
* **Librerías:** * **Apache POI:** Para la extracción de datos de archivos .xlsx.
    * **Log4j:** Para el registro de eventos de la aplicación y gestión de errores.
* **Arquitectura:** ETL (Extract, Transform, Load).

### 5. Etapas del Pipeline
1. **Extracción:** Ingesta automatizada de `data_rif.xlsx`. El sistema recorre todas las pestañas del libro; cada pestaña se asigna automáticamente a un periodo fiscal (Hoja 1 = Bimestre 1, Hoja 2 = Bimestre 2).
2. **Transformación:** * Búsqueda por rangos dentro de las tarifas del Anexo 8 correspondientes al bimestre de la pestaña.
    * Aplicación del estímulo anual decreciente del 10%.
3. **Carga:** Salida formateada en consola con alineación de columnas para facilitar la auditoría fiscal.


## 6. Configuración y Ejecución
1. Clonar el repositorio.
2. Asegurar que **Java 17** y **Maven** estén configurados en el entorno.
3. Verificar que el archivo de entrada `data_rif.xlsx` esté en el directorio raíz.
4. Ejecutar vía IDE o terminal usando `mvn clean install`.

---
*Note: This implementation is hosted on the `main` branch. Current Version: **v1.2.0 - Multi-Sheet Support**.*

*Nota: Esta implementación se encuentra en la rama `main`. Versión actual: **v1.2.0 - Soporte Multi-Pestaña**.*