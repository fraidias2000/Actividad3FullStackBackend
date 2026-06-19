# Actividad-2-Desarrollo-Web-Fullstack
Desarrollar parte de la arquitectura de microservicios para el proyecto de una aplicación web para una empresa de venta de libros

*Los archivos DDL y DML se encuentran en la ruta \src\main\resources\db\migration en los microservicios catalogue-service, orders-service y users-service


* Arrancar la aplicacion: Dentro de la carpeta donde esté el archivo "docker-compose.yml" ejecutar: docker compose up --build
*Compilar todos los microservicios: Ejecutar build-all.bat
*Eliminar contenedor docker: Dentro de la carpeta donde esté el archivo "docker-compose.yml" ejecutar: docker compose down -v


------------------------------------------------------------
ESTRUCTURA BASES DE DATOS
------------------------------------------------------------
Base de datos catalogue:
   Tabla books
      -id BIGSERIAL
      -title VARCHAR(200)
      -author VARCHAR(150)
      -publication_date DATE
      -category VARCHAR(100)
      -isbn VARCHAR(20)
      -rating INTEGER
      -visible BOOLEAN
      -stock INTEGER
      -price NUMERIC(10,2)


Base de datos orders:
   Tabla orders
      - id BIGSERIAL
      - user_id VARCHAR()
      - status VARCHAR()
      - total_amount NUMERIC(10,2)
      - created_at TIMESTAMP

   Tabla order_items
      - id BIGSERIAL
      - order_id BIGINT
      - book_id BIGINT
      - isbn VARCHAR(20)
      - title VARCHAR(200)
      - quantity INTEGER
      - unit_price NUMERIC(10,2)
      - subtotal NUMERIC(10,2)


Base de datos users:
   Tabla users 
    - id BIGSERIAL 
    - email VARCHAR(180) 
    - password VARCHAR(255) 
    - first_name VARCHAR(100)
    - last_name VARCHAR(100)
    - phone VARCHAR(30)


   Tabla roles 
    - id BIGSERIAL
    - name VARCHAR(50) 
    - description VARCHAR(255)


   Tabla user_roles 
    - user_id BIGINT 
    - role_id BIGINT 


   Tabla refresh_tokens
    - id BIGSERIAL 
    - user_id BIGINT
    - token VARCHAR(255)

------------------------------------------------------------
ENDPOINTS DISPONIBLES
------------------------------------------------------------

Microservicio catalogue-service:
------------------------------------------------------------

	*Obtener todos los libros:
		POST http://localhost:8762/api/books
		Body: 
			{
  				"targetMethod": "GET",
  				"queryParams": null,
  				"body": null
			}

	*Obtener libro por id:
		POST http://localhost:8762/api/books/{id}
		Body:
			{
  				"targetMethod": "GET",
  				"queryParams": null,
  				"body": null
			}

	*Buscar con filtro:
		POST http://localhost:8762/api/books/search
		Body:
			{
  				"targetMethod": "GET",
  				"queryParams": {
    				"title": ["optional title"],
				"author": ["optional author"],
				"publicationDate": ["optional publicationDate"],
				"category": ["optional category"],
				"isbn": ["optional isbn"],
				"rating": ["optional rating"],
				"visible": ["optional visible"],
				"stock": ["optional stock"],
				"price": ["optional price"]
  				},
  				"body": null
				}
		


	*Crear libro:
		POST http://localhost:8762/api/books
		Body:
			{
  			"targetMethod": "POST",
  			"queryParams": null,
  			"body": {
    				"title": "{title}",
    				"author": "{author}",
    				"publicationDate": "{publicationDate}",
    				"category": "{category}",
    				"isbn": "{isbn}",
    				"rating": {rating},
    				"visible": {visible},
    				"stock": {stock},
    				"price": {price}
  				}
			}

	*Actualización total libro:
		POST http://localhost:8762/api/books/{id}
		Body:
			{
 			"targetMethod": "PUT",
  			"queryParams": null,
			body:{
  		   		"title": "{title}",
  		   		"author": "{autor}",
 		   		"publicationDate": "{publicationDate}",
  		   		"category": "{category}",
  		   		"isbn": "{isbn}",
  		   		"rating": {rating},
  		   		"visible": {true or false},
  		   		"stock": {stock},
  		   		"price": {price}
				}
			}	

	*Actualizacion parcial libro:
		POST http://localhost:8762/api/books/{id}
		Body:
			{
 			"targetMethod": "PATCH",
  			"queryParams": null,
			body:{
  		   		"title": "{optional title}",
  		   		"author": "{optional autor}",
 		   		"publicationDate": "{optional publicationDate}",
  		   		"category": "{optional category}",
  		   		"isbn": "{optional isbn}",
  		   		"rating": {optional rating},
  		   		"visible": {optional true or false},
  		   		"stock": {optional stock},
  		   		"price": {optional price}
				}
			}


	*Eliminar libro:
		POST http://localhost:8762/api/books/{id}
		Body:
			{
  				"targetMethod": "DELETE",
  				"queryParams": null,
  				"body": null
			}



Microservicio orders-service:
------------------------------------------------------------

	*Registrar una compra
		POST http://localhost:8762/api/orders
		Headers:
		accessToken: "accessToken"
		Body:
			{
  			"targetMethod": "POST",
  			"queryParams": null,
  			"body": {
    				"userId": "userId",
    				"items": [
      					{
        				"bookId": bookId,
        				"quantity": quantity
      					}
    				]
  				}
			}


	*Obtener una orden por id
		POST http://localhost:8762/api/orders/{id}
		Body:
		{
  			"targetMethod": "GET",
  			"queryParams": null,
  			"body": null
		}

	*Obtener pedidos recientes de un usuario
		POST http://localhost:8762/orders-service/api/orders/users/{userId}/recent
		Body:	
		{
  			"targetMethod": "GET",
  			"queryParams": null,
  			"body": null
		}



Microservicio users-service:
------------------------------------------------------------

	*Hacer login
		POST http://localhost:8083/api/auth/login
		Body:
			{
  				"email": "email",
  				"password": "password"
			}
	
	*Validar token
		POST http://localhost:8083/api/auth/validate
		Body:
			{
  				"accessToken": "accessToken"
			}

	*Obtener perfil usuario con token opaco
		POST: http://localhost:8083/api/users/me
		Headers: "accessToken": "998585e9-e7ac-4cf4-8cb0-1b8892a6151d"

	* Renovar token
		POST http://localhost:8083/api/auth/refresh
		Body:
			{
  				"refreshToken": "refreshToken"
			}


		









