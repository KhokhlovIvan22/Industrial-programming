JAVAC = javac
JAVA = java
JAR = jar
MAIN = Main
JARFILE = program.jar

SRC = src/Main.java src/Fraction.java
CLASSES = Main.class Fraction.class

all: build

classes: $(SRC)
	$(JAVAC) -d . $(SRC)

build: classes
	$(JAR) cfe $(JARFILE) $(MAIN) $(CLASSES)

run: build
	$(JAVA) -ea -jar $(JARFILE)

clean:
	rm -f $(JARFILE)
	find . -name "*.class" -delete
