JAVAC = javac
JAVA = java
JAR = jar
MAIN = Main
JARFILE = program.jar

SRC = $(wildcard src/*.java)

all: build

classes: $(SRC)
	$(JAVAC) -encoding UTF-8 -d . $(SRC)

build: classes
	$(JAR) cfe $(JARFILE) $(MAIN) .

run: build
	$(JAVA) -ea -jar $(JARFILE)

clean:
	rm -f $(JARFILE)
	find . -name "*.class" -delete