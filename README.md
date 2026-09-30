# **Práctica 1:**

# **Gasolinera**

## **1. Entorno de Desarrollo y Requisitos**

**Lenguaje:** Java

**Versión del JDK:** OpenJDK 17

**Librerías externas:** Ninguna. Se utiliza exclusivamente la biblioteca estándar de Java

## **2. Instrucciones de Compilación y Ejecución**

### **Desde la consola / terminal:**

1. **Compilar el proyecto:**  
Asegúrate de estar en la carpeta raíz del proyecto donde se encuentran los archivos .java:  
javac -d bin \*.java
2. **Ejecutar la aplicación:**  
java -cp bin Main

## **3. Ubicación y Formato de los Ficheros de Datos**

Los datos persistentes se almacenan dentro de la carpeta datos/ relativa a la raíz de ejecución. Los ficheros se crean automáticamente en la primera ejecución si no existen.

**Codificación:** UTF-8

**Formato:** CSV

### **Ficheros:**

**1)datos/clientes.csv**

**Campos:** ID;Nombre;Teléfono;Matrícula

**Ejemplo:** 1;Ana López;600123456;1234ABC

\*\*Reglas:\*\*No puede haber 2 matriculas iguales y se muestran en mayusculas.

**2)datos/repostajes.csv**

**Campos:** ID;ID\_Cliente;Fecha;Importe;Litros;Combustible

**Ejemplo:** 1;1;2026-09-11;40.50;25.00;Gasolina 95

**Reglas:** Las fechas se muestran en formato ISO (dd/MM/aaaa)

## **4. Decisiones de Diseño**

Estas serían mis decisiones de diseño:

**1)Clase Main:**

Se encarga únicamente del menú interactivo, la lectura inicial de datos por consola y la validación previa del formato de entrada.

**2)Clase Gasolinera:**

Contiene la lógica del dominio de la gasolinera: cálculo de nuevos IDs, búsqueda parcial de clientes,funciones(comoprocesarPago,agregarCliente,etc).

**3)Clase GestorArchivos:**

Tiene todo el acceso al sistema de archivos usando la API java.nio.file.Files y Path. Separa completamente la lectura/escritura física del resto de la lógica de la aplicación.

**4)Clases Cliente y Repostaje:**

Son las clases con sus atributos y el encapsulamiento (getters y setters).

**5)Clase Utilidades :**

Esta es una clase auxiliar que reutiliza la funcion comprobarVacio

6\)**Interfaz AlmacenamientoDatos:**

Esta interfaz sirve para que la clase GestorArchivos herede los métodos que tengo que hacer y asi,si quiero hacer los ficheros en otros formatos(por ejemplo en JSON),las clases GestorArchivos de esos otros formatos esten condicionadas a hacer los métodos que herede de la interfaz.

## **5. Indicaciones de Diseño y Respuesta a Cambios**

### **Indicaciones trabajadas en clase aplicadas:**

**Principio de Responsabilidad Única (SRP):** La clase Gasolinera no sabe cómo ni dónde se guardan los datos en el disco y solo le pide a GestorArchivos que lea o guarde los objetos.GestorArchivos desconoce la lógica de la gasolinera ya que eso lo harían otras clases(Principalmente Gasolinera).

**Gestión de Recursos con java.nio.file:** Para la lectura y escritura de los archivos he utilizado Files.readAllLines y Files.write. Decidí hacerlo así porque estas funciones se encargan de gestionar los archivos de forma limpia en memoria y evitan que queden abiertos por error.

**Manejo de Errores e Integridad:** Si ocurre un problema con los archivos o el formato está corrupto, el programa esta preparado para avisar al usuario sin borrar datos hechos antes(esto lo he hecho en el constructor de GestorArchivos).

### **¿Qué partes tendrían que cambiar si se modificara el formato de almacenamiento?**

* Si el formato cambia (por ejemplo de CSV a XML,JSON,etc):

  * La unica clase afectada seria GestorArchivos ya que se tendrían que crear una nueva clase (o reescribir la ya hecha) con los métodos de lectura/escritura (cargarClientes, guardarCliente, cargarRepostajes, guardarRepostaje) adaptados al formato que se pide.

