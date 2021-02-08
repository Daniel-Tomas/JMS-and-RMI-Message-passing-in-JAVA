J_DEBUGGING_FLAG = -g
J_DIRECTORY_CLASS_FLAG = -d
J_DIRECTORY_CLASS_PATH = ./
J_DIRECTORY_CLASS = $(J_DIRECTORY_CLASS_FLAG) $(J_DIRECTORY_CLASS_PATH)
J_DIRECTORY_SOURCE_FLAG = -sourcepath
J_DIRECTORY_SOURCE_PATH = ./src/
J_DIRECTORY_SOURCE = $(J_DIRECTORY_SOURCE_FLAG) $(J_DIRECTORY_SOURCE_PATH)

SRC = \
	src/basica/client/Client.java \
	src/basica/client/ClientType1.java \
	src/basica/client/ClientType2.java \
	src/basica/client/ClientType3.java \
	src/basica/server/Value.java \
	src/basica/server/Quotation.java \
	src/basica/server/Enterprise.java \
	src/basica/server/StockBroker.java \
	src/extra/client/Client.java \
	src/extra/client/ClientType1.java \
	src/extra/client/ClientType2.java \
	src/extra/client/ClientType3.java \
	src/extra/server/Value.java \
	src/extra/server/Quotation.java \
	src/extra/server/Enterprise.java \
	src/extra/server/News.java \
	src/extra/server/StockNews.java \
	src/extra/server/StockBroker.java \



OBJ = $(SRC:.java=.class)
JC = javac
JAVAC_COMPILE = $(JC) $(J_DIRECTORY_SOURCE) $(J_DIRECTORY_CLASS)

default : $(OBJ)

%.class: %.java
		@$(JAVAC_COMPILE) $*.java
