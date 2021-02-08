J_DEBUGGING_FLAG = -g
J_DIRECTORY_CLASS_FLAG = -d
J_DIRECTORY_CLASS_PATH = ./
J_DIRECTORY_CLASS = $(J_DIRECTORY_CLASS_FLAG) $(J_DIRECTORY_CLASS_PATH)
J_DIRECTORY_SOURCE_FLAG = -sourcepath
J_DIRECTORY_SOURCE_PATH = ./src/
J_DIRECTORY_SOURCE = $(J_DIRECTORY_SOURCE_FLAG) $(J_DIRECTORY_SOURCE_PATH)

SRC = \
	src/basica/Value.java \
	src/basica/Client.java \
	src/basica/ClientType1.java \
	src/basica/ClientType2.java \
	src/basica/ClientType3.java \
	src/basica/Enterprise.java \
	src/basica/Quotation.java \
	src/basica/StockBroker.java \
	src/extra/Value.java \
	src/extra/News.java \
	src/extra/Client.java \
	src/extra/ClientType1.java \
	src/extra/ClientType2.java \
	src/extra/ClientType3.java \
	src/extra/Enterprise.java \
	src/extra/Quotation.java \
	src/extra/StockBroker.java \
	src/extra/StockNews.java \
	src/extra/Stocks.java \


OBJ = $(SRC:.java=.class)
JC = javac
JAVAC_COMPILE = $(JC) $(J_DIRECTORY_SOURCE) $(J_DIRECTORY_CLASS)

default : $(OBJ)

%.class: %.java
		@$(JAVAC_COMPILE) $*.java