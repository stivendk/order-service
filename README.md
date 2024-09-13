- [Introducción](#introducción)
  - [Endpoints](#endpoints)
- [DevOps](#devops)
- [Documentación de la Base de Datos](#documentación-de-la-base-de-datos)
- [Reglas de Negocio](#reglas-de-negocio)

# Introducción

Esta API proporciona una serie de endpoints para gestionar órdenes (`Order`). A continuación se detalla la funcionalidad de cada endpoint disponible en el controlador `OrderController`.

# Tabla de Contenido

## Características

- **Crear, leer, actualizar y eliminar pedidos, items y productos.**
- **Documentación de API generada automáticamente con Swagger.**

## Requisitos

- Java 17


### Base de Datos

La aplicación usa H2 Database por defecto para almacenamiento en memoria.

# Ejecutar la Aplicación

Para ejecutar la aplicación localmente:

1. Descarga las dependencias:
```bash
./gradlew build
```
2. Ejecuta la aplicación:
```bash
./gradlew bootRun
```
La aplicación se levantará en http://localhost:8082.


## Endpoints

### Crear un Nuevo Pedido

- **Método:** `POST`
- **Ruta:** `/orders`
- **Descripción:** Crea una nueva orden con los detalles proporcionados.
- **Parámetros de Solicitud:**
    - `requestOrderDTO` (Objeto JSON): Contiene los detalles de la nueva orden.
- **Respuesta:**
    - **Código de Estado:** `201 Created`
    - **Cuerpo de la Respuesta:** Un objeto `ResponseDTO` que incluye la orden creada y un mensaje.

**Ejemplo de Solicitud:**
```json
{
  "orderItems": [
    {
      "productId": 123,
      "quantity": 2
    }
  ]
}
```

### Obtener Todas las Órdenes
Método: GET

Ruta: /orders

Descripción: Obtiene una lista de todas las órdenes.

Respuesta:

Código de Estado: 200 OK

Cuerpo de la Respuesta: Un objeto ResponseDTO que incluye una lista de órdenes y un mensaje.

**Ejemplo de Solicitud:**
```json
{
  "data": [
    {
      "id": 1,
      "totalAmount": 199.99,
      "status": "NEW_ORDER",
      "items": [
        {
          "productId": 123,
          "quantity": 2
        }
      ],
      "createdAt": "2024-09-12T14:30:00",
      "updatedAt": "2024-09-12T15:00:00"
    }
  ],
  "message": "Order list"
}
```
### Obtener una Orden por ID
Método: GET

Ruta: /orders/{id}

Descripción: Obtiene los detalles de una orden específica por su ID.

Parámetros de Ruta:

id (Long): ID de la orden a recuperar.
Respuesta:

Código de Estado: 200 OK

Cuerpo de la Respuesta: Un objeto ResponseDTO que incluye la orden solicitada y un mensaje.
```json
{
  "data": {
    "id": 1,
    "totalAmount": 199.99,
    "status": "NEW_ORDER",
    "items": [
      {
        "productId": 123,
        "quantity": 2
      }
    ],
    "createdAt": "2024-09-12T14:30:00",
    "updatedAt": "2024-09-12T15:00:00"
  },
  "message": "Order 1"
}
```

### Actualizar una Orden
Método: PUT

Ruta: /orders/{id}

Descripción: Actualiza una orden existente identificada por su ID.

Parámetros de Ruta:id (Long): ID de la orden a actualizar.

Parámetros de Solicitud: requestUpdateOrderDTO (Objeto JSON): Contiene los datos de actualización y un indicador de si la orden está pendiente de pago.

Ejemplo de Solicitud:

PUT /orders/1 HTTP/1.1
Content-Type: application/json

```json
{
  "isPendingPaymentUpdate": true
}
```

Respuesta:

Código de Estado: 200 OK

Cuerpo de la Respuesta: Un objeto ResponseDTO que incluye la orden actualizada y un mensaje que indica si la orden está pendiente de pago o ha sido pagada.

```json
{
  "data": {
    "id": 1,
    "totalAmount": 199.99,
    "status": "UPDATED_ORDER",
    "items": [
      {
        "productId": 123,
        "quantity": 2
      }
    ],
    "createdAt": "2024-09-12T14:30:00",
    "updatedAt": "2024-09-12T15:00:00"
  },
  "message": "Order updated"
}
```

### Obtener la Orden Activa

Método: GET

Ruta: /orders/active

Descripción: Obtiene la orden activa actual.

Respuesta:

Código de Estado: 200 OK

Cuerpo de la Respuesta: Un objeto ResponseDTO que incluye la orden activa y un mensaje.

Ejemplo de Respuesta:

```json
{
  "data": {
    "id": 1,
    "totalAmount": 199.99,
    "status": "ACTIVE_ORDER",
    "items": [
      {
        "productId": 123,
        "quantity": 2
      }
    ],
    "createdAt": "2024-09-12T14:30:00",
    "updatedAt": "2024-09-12T15:00:00"
  },
  "message": "Active order"
}
```

## Endpoints de OrderItems o Items del pedido

### Obtener Todos los Artículos de Orden

- **Método:** `GET`
- **Ruta:** `/order-items`
- **Descripción:** Obtiene una lista de todos los artículos de orden.
- **Respuesta:**

- **Código de Estado:** `200 OK`
- **Cuerpo de la Respuesta:** Un objeto `ResponseDTO` que incluye una lista de artículos de orden y un mensaje.

**Ejemplo de Respuesta:**

```json
{
  "data": [
    {
      "id": 1,
      "quantity": 2,
      "priceAtPurchase": 99.99,
      "product": {
        "id": 1,
        "name": "Product Name",
        "description": "Product Description",
        "price": 99.99
      }
    }
  ],
  "message": "Order items list"
}
```

### Obtener un Artículo de Orden por ID

Método: GET

Ruta: /order-items/{id}

Descripción: Obtiene los detalles de un artículo de orden específico por su ID.

Parámetros de Ruta:

id (Long): ID del artículo de orden a recuperar.
Respuesta:

Código de Estado: 200 OK

Cuerpo de la Respuesta: Un objeto ResponseDTO que incluye el artículo de orden solicitado y un mensaje.

Ejemplo de Respuesta:

```json
{
  "data": {
    "id": 1,
    "quantity": 2,
    "priceAtPurchase": 99.99,
    "product": {
      "id": 1,
      "name": "Product Name",
      "description": "Product Description",
      "price": 99.99
    }
  },
  "message": "Order item 1"
}
```

### Agregar un Nuevo Artículo de Orden

Método: POST

Ruta: /order-items/add

Descripción: Agrega un nuevo artículo de orden con los detalles proporcionados.

Parámetros de Solicitud:
requestOrderItemDTO (Objeto JSON): Contiene los detalles para agregar el nuevo artículo de orden.

Ejemplo de Solicitud:
```json
POST /order-items/add HTTP/1.1
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2,
  "orderId": 1
}
```
### Actualizar un Artículo de Orden
Método: PUT

Ruta: /order-items/{id}

Descripción: Actualiza un artículo de orden existente identificado por su ID.

Parámetros de Ruta:

id (Long): ID del artículo de orden a actualizar.

Parámetros de Solicitud:
requestUpdateOrderItemDTO (Objeto JSON): Contiene los datos de actualización del artículo de orden.

Ejemplo de Solicitud:
```json
PUT /order-items/1 HTTP/1.1
Content-Type: application/json

{
  "productId": 1,
  "quantity": 3
}
```

### Eliminar un Artículo de Orden

Método: DELETE

Ruta: /order-items/{id}

Descripción: Elimina un artículo de orden existente por su ID.

Parámetros de Ruta:

id (Long): ID del artículo de orden a eliminar.
Respuesta:

Código de Estado: 200 OK

Cuerpo de la Respuesta: Un objeto ResponseDTO que incluye un mensaje de confirmación.

```json
{
  "data": null,
  "message": "Item removed"
}
```

## Endpoints para la gestion de producto

### Obtener un Producto por ID

- **Método:** `GET`
- **Ruta:** `/products/{id}`
- **Descripción:** Obtiene los detalles de un producto específico por su ID.
- **Parámetros de Ruta:**
    - `id` (Long): ID del producto a recuperar.
- **Respuesta:**

- **Código de Estado:** `200 OK`
- **Cuerpo de la Respuesta:** Un objeto `ResponseDTO` que incluye el producto solicitado y un mensaje.

**Ejemplo de Respuesta:**

```json
{
  "data": {
    "id": 1,
    "name": "Laptop",
    "description": "High-performance laptop with 16GB RAM",
    "price": 999.99,
    "stock": 50,
    "imageUrl": "http://example.com/image.jpg",
    "status": "AVAILABLE"
  },
  "message": "Product 1"
}
```

### Obtener Todos los Productos
Método: GET

Ruta: /products

Descripción: Obtiene una lista de todos los productos.

Respuesta:

Código de Estado: 200 OK

Cuerpo de la Respuesta: Un objeto ResponseDTO que incluye una lista de productos y un mensaje.

Ejemplo de Respuesta:

```json
{
  "data": [
    {
      "id": 1,
      "name": "Laptop",
      "description": "High-performance laptop with 16GB RAM",
      "price": 999.99,
      "stock": 50,
      "imageUrl": "http://example.com/image.jpg",
      "status": "AVAILABLE"
    }
  ],
  "message": "Product list"
}
```

### Agregar un Nuevo Producto

Método: POST

Ruta: /products

Descripción: Agrega un nuevo producto con los detalles proporcionados.

Parámetros de Solicitud:
requestProductDTO (Objeto JSON): Contiene los detalles para agregar el nuevo producto.

Ejemplo de Solicitud:

```json
POST /products HTTP/1.1
Content-Type: application/json

{
  "name": "Laptop",
  "description": "High-performance laptop with 16GB RAM",
  "price": 999.99,
  "stock": 50,
  "imageUrl": "http://example.com/image.jpg"
}
```

### Actualizar un Producto

Método: PUT

Ruta: /products/{id}

Descripción: Actualiza un producto existente identificado por su ID.

Parámetros de Ruta:
id (Long): ID del producto a actualizar.

Parámetros de Solicitud:
requestUpdateProductDTO (Objeto JSON): Contiene los datos de actualización del producto.

Ejemplo de Solicitud:

```json
PUT /products/1 HTTP/1.1
Content-Type: application/json

{
  "name": "Updated Laptop",
  "description": "Updated description of the high-performance laptop",
  "price": 1099.99,
  "stock": 30,
  "imageUrl": "http://example.com/updated-image.jpg"
}
```

### Documentación de la API

La documentación de la API está disponible en Swagger UI en:

http://localhost:8082/swagger-ui.html

## Nota 

Alternativamente en la carpeta **"coleccion postman"**, se encontrará una colección con los anteriores endpoints, los cuales podrán ser ejecutados despues de haber desplegado la app como se menciona anteriormente.

___
# DevOps

## Pipelines

Este paso a paso es para indicar la configuración del pipeline para desplegar por jenkins:

1. Dirigirse a Jenkins y crea un nuevo proyecto de tipo "Pipeline".

2. En la configuración del job, especifica que el Jenkinsfile se encuentra en tu repositorio.

3. En el campo "Pipeline", selecciona "Pipeline script from SCM".

4. Elige "Git" como tipo de SCM.

5. Ingresa la URL de tu repositorio Git.

6. Jenkins buscará el archivo Jenkinsfile en la raíz del repositorio.

___

# Documentación de la Base de Datos

## Descripción General

Esta documentación detalla la estructura de la base de datos utilizada para el sistema de gestión de pedidos. La base de datos está diseñada para almacenar información sobre productos, órdenes y artículos de pedido. A continuación se describen las tablas y sus relaciones.

## Estructura de la Base de Datos

### 1. Tabla `tbl_products`

**Descripción**: Esta tabla almacena información sobre los productos disponibles en el sistema.

- **id**: `BIGINT`, clave primaria, autoincremental. Identificador único del producto.
- **name**: `VARCHAR(255)`, no nulo. Nombre del producto.
- **description**: `TEXT`, no nulo. Descripción detallada del producto.
- **price**: `DECIMAL(10, 2)`, no nulo. Precio del producto.
- **stock**: `INT`, no nulo. Cantidad disponible en stock.
- **image_url**: `VARCHAR(255)`, opcional. URL de la imagen del producto.
- **status**: `VARCHAR(20)`, no nulo. Estado del producto. Valores permitidos: `'AVAILABLE'`, `'UNAVAILABLE'`.

**Índices y Restricciones**:
- Clave primaria en el campo `id`.
- Restricción de `CHECK` en el campo `status` para asegurar que el valor esté entre `'AVAILABLE'` y `'UNAVAILABLE'`.

### 2. Tabla `tbl_orders`

**Descripción**: Esta tabla almacena información sobre las órdenes realizadas en el sistema.

- **id**: `BIGINT`, clave primaria, autoincremental. Identificador único de la orden.
- **total_amount**: `DECIMAL(10, 2)`, no nulo. Monto total de la orden.
- **status**: `VARCHAR(20)`, no nulo. Estado de la orden. Valores permitidos: `'NEW_ORDER'`, `'PENDING_PAYMENT'`, `'PAID'`.
- **created_at**: `TIMESTAMP`, por defecto `CURRENT_TIMESTAMP`. Fecha y hora en que se creó la orden.
- **updated_at**: `TIMESTAMP`, por defecto `CURRENT_TIMESTAMP`. Fecha y hora de la última actualización de la orden.

**Índices y Restricciones**:
- Clave primaria en el campo `id`.
- Restricción de `CHECK` en el campo `status` para asegurar que el valor esté entre `'NEW_ORDER'`, `'PENDING_PAYMENT'`, y `'PAID'`.

### 3. Tabla `tbl_order_items`

**Descripción**: Esta tabla almacena los artículos asociados a cada orden, incluyendo el producto, la cantidad y el precio al momento de la compra.

- **id**: `BIGINT`, clave primaria, autoincremental. Identificador único del artículo del pedido.
- **order_id**: `BIGINT`. Identificador de la orden a la que pertenece el artículo.
- **product_id**: `BIGINT`. Identificador del producto incluido en el artículo del pedido.
- **quantity**: `INT`. Cantidad del producto en la orden.
- **price_at_purchase**: `DECIMAL(10, 2)`. Precio del producto en el momento de la compra.

**Índices y Restricciones**:
- Clave primaria en el campo `id`.
- Clave externa en el campo `order_id` que referencia a `tbl_orders(id)`.
- Clave externa en el campo `product_id` que referencia a `tbl_products(id)`.

## Relaciones entre Tablas

- **`tbl_products` y `tbl_order_items`**:
  - Relación de uno a muchos (1:N) entre `tbl_products` y `tbl_order_items`. Un producto puede estar en múltiples artículos de pedido.

- **`tbl_orders` y `tbl_order_items`**:
  - Relación de uno a muchos (1:N) entre `tbl_orders` y `tbl_order_items`. Una orden puede contener múltiples artículos de pedido.

## Acceso y Gestión de Datos

- **Acceso a Datos**:
  - Los datos en la base de datos se pueden acceder utilizando consultas SQL estándar. Se pueden realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) a través de sentencias SQL.

- **Gestión de Datos**:
  - **Creación**: Utilizar `INSERT INTO` para agregar nuevos registros a las tablas.
  - **Lectura**: Utilizar `SELECT` para consultar datos.
  - **Actualización**: Utilizar `UPDATE` para modificar registros existentes.
  - **Eliminación**: Utilizar `DELETE` para eliminar registros.

- **Mantenimiento**:
  - Asegurar que las restricciones de integridad referencial se mantengan mediante las claves foráneas.
  - Realizar copias de seguridad periódicas de la base de datos.
  - Monitorear el rendimiento y realizar ajustes según sea necesario.
___

# Reglas de Negocio

## Orden (Order)

1. **Creación de una Orden**:
  - Una orden se crea sin datos iniciales, ya que los datos fundamentales se establecen por defecto. Estos datos son:
    - **Fecha de Creación**: Se establece automáticamente en el momento de la creación de la orden.
    - **Estado**: El estado inicial de la orden es `NEW_ORDER`.
    - **Monto Total**: El monto total de la orden se establece en `0.0`.

2. **Gestión del Estado de la Orden**:
  - Solo puede haber una orden activa en el sistema en cualquier momento dado. Los estados activos son `NEW_ORDER` o `PENDING_PAYMENT`.
  - Cuando el estado de una orden cambia a `PAID`, se debe generar automáticamente una nueva orden con los mismos datos predeterminados para que el sistema pueda seguir gestionando nuevos pedidos.

3. **Actualización del Stock de Productos**:
  - El stock de los productos se actualizará cuando el estado de una orden cambie a `PAID`. Esta actualización debe reflejar la cantidad total de productos en la orden.

## Artículo de Orden (OrderItem)

1. **Manejo de Artículos de Orden Existentes**:
  - Cuando se intenta agregar un nuevo `OrderItem` a una orden que ya contiene un artículo con el mismo producto, el sistema debe realizar lo siguiente:
    - **Actualización de Cantidad**: La cantidad del artículo existente se debe actualizar sumando la cantidad del nuevo `OrderItem`.
    - **Verificación de Stock**: Antes de realizar la suma, el sistema debe verificar que la cantidad total del producto en la orden no exceda el stock disponible. Si la cantidad total supera el stock disponible del producto, se debe lanzar una excepción indicando que el stock es insuficiente. Esta excepción previene la adición de más artículos de orden hasta que la cantidad sea compatible con el stock disponible.

---

