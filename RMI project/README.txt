> Memoria Práctica 2 RMI

## Contenido del directorio raíz del proyecto:

El directorio raíz contiene los siguientes apartados: makefile, los archivos de texto (stock_news.txt y stock_prices.txt, README.txt)  y la carpeta src dividida en dos paquetes, la básica y la extra.

En la básica se encuentran dos paquetes:

		1. Un paquete cliente donde se encuentran las clases: Client, ClientType1, ClientType2, ClientType3 y la interfaz ClientInfo.
		2. Un paquete servidor donde se encuentran las clases: Enterprise, Quotation, StockBroker, Value y la interfaz BrokerService.

En la extra se encuentran dos paquetes:

		1. Un paquete cliente donde se encuentran las clases: Client, ClientType1, ClientType2, ClientType3 y la interfaz ClientInfo.
		2. Un paquete servidor donde se encuentran las clases: Enterprise, News, Quotation, StockBroker, StockNews, Value y la interfaz BrokerService y NewsService.

## Cómo ejecutar la práctica: ##

Tener instalada jdk 1.8 o versión superior.

Para ejecutar la práctica se utilizará la terminal.

Tanto para la básica como para la extra primero habrá que ponerse en el directorio raíz del proyecto:

Primero se ejecuta el mandato make, así el makefile compilará todas las clases de los dos paquetes del proyecto.

Para la funcionalidad básica:

		1. En una terminal diferente ejecutar la clase StockBroker: java basica.server.StockBroker
		2. En una terminal diferente ejecutar la clase ClientType1: java basica.client.ClientType1
		3. En una terminal diferente ejecutar la clase ClientType2: java basica.client.ClientType2
		4. En una terminal diferente ejecutar la clase ClientType3: java basica.client.ClientType3
		5. Mirar los resultados en la terminal donde se ejecutó StockBroker.

Sin embargo, para la funcionalidad extra, a pesar de ser muy similar, tiene unos cambios pequeños en los comandos para ejecutar las clases.

Para la funcionalidad extra:

		1. En una terminal diferente ejecutar la clase StockBroker: java extra.server.StockBroker
		2. En una terminal diferente ejecutar la clase StockNews: 	java extra.server.StockNews
		3. En una terminal diferente ejecutar la clase ClientType1: java extra.client.ClientType1
		4. En una terminal diferente ejecutar la clase ClientType2: java extra.client.ClientType2
		5. En una terminal diferente ejecutar la clase ClientType3: java extra.client.ClientType3
		6. Mirar los resultados en la terminal donde se ejecutó StockBroker.

Así es como se ejecutan las dos partes de la práctica.

## Distintas clases y su descripción en la funcionalidad básica ##

**Client**: 		Clase abstracta que representa a un cliente de donde heredarán los siguientes tipos de cliente.

**ClientInfo**:		Interfaz de los clientes, contiene métodos remotos usados por el StockBroker donde puede enviar información de las cotizaciones a los clientes y le devuelve estos las acciones que desean comprar.

**ClientType1**: 	Crea al cliente del primer tipo. Tratará de comprar cada día una acción de los sectores Technology y Health.

**ClientType2**: 	Crea el cliente del segundo tipo. Compra cada dos días dos acciones de GOOGL, RO y REP.

**ClientType3**: 	Crea el cliente del tercer tipo. Compra una acción de las compañías que tengan una caída inferior al -5% al día.

**BrokerService**:	Interfaz del StockBroker y ofrece unos servicios para los clientes, en concreto el de registrar un cliente

**Enterprise**: 	Clase que representa una compañía registrada en el banco con la información de su nombre, sector y su ticker.

**Quotation**: 		Clase que representa la información leída de una frase del texto. Contiene la información necesaria para que cada tipo de cliente pueda hacer una decisión de si comprar o no.

**StockBroker**: 	Hace la comunicación entre los compradores y el banco de inversión, recibe la información necesaria y la procesa para mandarla.

**Value**: 			Clase que representa el historial de qué acciones ha comprado un cliente, cuánto ha gastado y cuál es el valor actual de las acciones.

## Distintas clases y su descripción en la funcionalidad extra ##

Se reutilizan las clases anteriores para añadir la nueva funcionalidad explicada junto a dos nuevas clases.

**News**: 			Clase que representa la informacion de las noticias leída en el texto.

**NewsService**:	Interfaz del StockNews y ofrece unos servicios para que los clientes puedan acceder a las noticias.

**StockNews**: 		Recibe la información necesaria, la procesa para mandarla y comunica las noticias de las compañías a los compradores.

## Funcionalidad básica implementada ##

**StockBroker**

La implementación descrita anteriormente es la funcionalidad que se exige. Haciendo la comunicación entre compradores y banco de inversión en modo RMI tal y como se especifica.

**ClientType 1, 2, 3**

La impletación de los tres tipos de clientes pedida esta hecha en tres clases distintas que representarán a cada tipo de cliente. Habrá una clase Client general que extenderan estas tres clases comentadas.

## Funcionalidad extra implementada ##

**StockNews, News y NewsService**

Son las dos nuevas clases y la interfaz encargadas de hacer que se cumpla esta funcionalidad. News es una clase que contiene la información de las noticias leídas del texto, mientas que StockNews comunica las noticias con los compradores en el modo Java RMI pedido.
