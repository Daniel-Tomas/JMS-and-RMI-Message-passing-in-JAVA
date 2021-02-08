> Memoria Práctica 1 JMS


## Contenido del directorio raíz del proyecto:

Nuestro directorio raíz contiene los siguientes apartados: makefile, los archivos de texto (stock_news.txt y stock_prices.txt, README.txt)  y la carpeta src dividida en dos paquetes, la básica y la extra.

En la básica encontraremos las clases: Client, ClientType1, ClientType2, ClientType3, Enterprise, Quotation, StockBroker y Value.

En la extra encontraremos las clases: Client, ClientType1, ClientType2, ClientType3, Enterprise, Quotation, StockBroker, Value, StockNews, News y Stocks.

## Cómo ejecutar la práctica: ##

Tener instalada jdk 1.8 o versión superior y añadida las rutas de las librerias imq.jar y jms.jar en la variable de entorno CLASSPATH del sistema operativo.

Para ejecutar la práctica utilizaremos la terminal.

El mandato make, así el makefile compilara todas las clases de los dos paquetes del proyecto.

Tanto para la básica como para la extra primero nos pondrémos en el directorio raíz del proyecto y ejecutaremos:

```imqbrokerd -tty```
```make```

Para ejecutar la funcionalidad básica:

		1. En una terminal diferente ejecutar la clase ClientType1: java basica.ClientType1
		2. En una terminal diferente ejecutar la clase ClientType2: java basica.ClientType2
		3. En una terminal diferente ejecutar la clase ClientType3: java basica.ClientType3
		4. En una terminal diferente ejecutar la clase StockBroker: java basica.StockBroker
		5. Mirar los resultados en las terminales donde se ha ejecutado cada cliente.

Sin embargo, para la funcionalidad extra, a pesar de ser muy similar, tiene unos cambios pequeños en los comandos para ejecutar las clases.

Para ejecutar la funcionalidad extra:


		1. En una terminal diferente ejecutar la clase ClientType1: java extra.ClientType1
		2. En una terminal diferente ejecutar la clase ClientType2: java extra.ClientType2
		3. En una terminal diferente ejecutar la clase ClientType3: java extra.ClientType3
		4. En una terminal diferente ejecutar la clase Stocks: 	    java extra.Stocks
		5. Mirar los resultados en las terminales donde se ha ejecutado cada cliente.

Así es como se ejecutan las dos partes de la práctica.

## Distintas clases y su descripción en la funcionalidad básica ##

**Client**: 		Clase abstracta que representa a un cliente.

**ClientType1**: 	Crea al cliente del primer tipo. Tratará de comprar cada día una acción de los sectores Technology y Health.

**ClientType2**: 	Crea el cliente del segundo tipo. Compra cada dos días dos acciones de GOOGL, RO y REP.

**ClientType3**: 	Crea el cliente del tercer tipo. Compra una acción de las compañías que tengan una caída inferior al -5% al día.

**Enterprise**: 	Clase que representa una compañía registrada en el banco con la información de su nombre, sector y su ticker.

**Quotation**: 		Clase que representa la información leida de una frase del texto. Contiene la información necesaria para que cada tipo de cliente pueda hacer una decisión de si comprar o no.

**StockBroker**: 	Hace la comunicación entre los compradores y el banco de inversión, recibe la información necesaria y la procesa para mandarla.

**Value**: 			Clase que representa el historial de que acciones ha comprado un cliente, cuanto ha gastado y cual es el valor actual de las acciones.

## Distintas clases y su descripción en la funcionalidad extra ##

Se reutilizan las clases anteriores para añadir la nueva funcionalidad explicada junto a dos nuevas clases.

**News**: 			Clase que representa la informacion de las noticias leída en el texto.

**StockNews**: 		Recibe la información necesaria, la procesa para mandarla y comunica las noticias de las compañías a los compradores.

**Stocks**: 		Es el main encargado de ejecutar el StockBroker y StockNews.

## Funcionalidad básica implementada ##

**StockBroker**

La implementación descrita anteriormente es la funcionalidad que se exige. Haciendo la comunicación entre las compañias y los compradores para mandarla de modo petición/respuesta.

**ClientType 1, 2, 3**

La impletación de los tres tipos de clientes pedida esta hecha en tres clases distintas que representarán a cada tipo de cliente. Habrá una clase Client general que extenderan estas tres clases comentadas.

## Funcionalidad extra implementada ##

**StockNews y News**

Son las dos nuevas clases encargadas de hacer que se cumpla esta funcionalidad. News es una clase que contiene la información de las noticias leídas del texto, mientas que StockNews comunica los compradores con las noticias y las envia con el modo peticion/respuesta pedido. 

**Stocks**

Se encarga de que StockNews y StockBroker ejecuten.