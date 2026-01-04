JAVAC = javac
JAVA  = java
JAR   = jar
MAIN = Main
JARFILE = program.jar
SRC = $(wildcard src/*.java)
CLASSES = $(SRC:.java=.class)

all: build

classes: $(SRC)
	$(JAVAC) -d . $(SRC)

build: classes
	$(JAR) cfe $(JARFILE) $(MAIN) -C . .

run: build
	$(JAVA) -jar $(JARFILE)

clean:
	rm -f $(JARFILE)
	find . -name "*.class" -delete

