# For installing
MINDUSTRY := $(HOME)/.local/share/Mindustry

JAVAC := javac
JAVACFLAGS := -g -Xlint:all
# Auto-import files in the same package
override JAVACFLAGS += -sourcepath src
# Add .jar libraries
override JAVACFLAGS += -classpath "libs/*"
# Compile against java 8 abi - d8 needs this
override JAVACFLAGS += --release 8

JARFLAGS := -C build/classes .
override JARFLAGS += -C assets .

sources := $(shell find src -type f -name "*.java")
assets := $(shell find assets -type f)
classes := $(patsubst src/%.java, build/classes/%.class, $(sources))

D8 := d8
D8FLAGS := --min-api 14

# Mindustry & arc version to link against
mindustryVersion := 0fa26b1b0f
arcVersion := dfcb21ce56

all: build

libs := core-release arc-core
libs := $(libs:%=libs/%.jar)

define newlib
libs/$(1).jar:
	@printf "\033[33m> LIB\033[0m\t%s\n" $$@
	@mkdir -p $$(@D)
	@curl 'https://jitpack.io/com/github/$(2)/$(3)/$(4)/$(3)-$(4).jar.sha1' -so $$@.sha1
	@curl 'https://jitpack.io/com/github/$(2)/$(3)/$(4)/$(3)-$(4).jar' -so $$@
	@printf "\t%s" "$$@" >> $$@.sha1
	@sha1sum -c $$@.sha1 || (rm $$@ && exit 1)
	@rm $$@.sha1
endef

$(eval $(call newlib,core-release,Anuken/MindustryJitpack,core,$(mindustryVersion)))
$(eval $(call newlib,arc-core,Anuken/Arc,arc-core,$(arcVersion)))

build: ExampleJavaMod-Desktop.jar
android: build ExampleJavaMod.jar

build/classes/%.class: src/%.java $(libs)
	@printf "\033[32m> JAVAC\033[0m\t%s\n" $@
	@mkdir -p `dirname $@`
	@$(JAVAC) $(JAVACFLAGS) $< -d build/classes

ExampleJavaMod-Desktop.jar: $(classes) $(assets)
	@printf "\033[33m> JAR\033[0m\t%s\n" $@
	@jar -cf $@ $(JARFLAGS) || rm $@

ExampleJavaMod.jar: ExampleJavaMod-Desktop.jar
	@printf "\033[33m> D8\033[0m\t%s\n" $@
	@$(D8) $(D8FLAGS) --output build $^
	@cp ExampleJavaMod-Desktop.jar $@
	@cd build; zip -qg ../$@ classes.dex

install: build
	cp ExampleJavaMod-Desktop.jar $(MINDUSTRY)/mods

clean:
	rm -rf build

reset:
	rm -rf build libs *.jar

help:
	@printf "\033[97;1mAvailable tasks:\033[0m\n"
	@printf "\t\033[32mbuild \033[90m(default)\033[0m\n"
	@printf "\t  Compile the mod into \033[97;1m%s\033[0m\n" ExampleJavaMod-Desktop.jar
	@printf "\t\033[32mandroid\033[0m\n"
	@printf "\t  Dex the mod into \033[91;1m%s\033[0m\n" ExampleJavaMod.jar
	@printf "\t  Compatible with PC and Android.\n"
	@printf "\t\033[32minstall\033[0m\n"
	@printf "\t  Install the desktop version to \033[97;1m%s\033[0m\n" $(MINDUSTRY)
	@printf "\t\033[32mclean\033[0m\n"
	@printf "\t  Remove compiled classes.\n"
	@printf "\t\033[31mreset\033[0m\n"
	@printf "\t  Remove compiled classes and downloaded libraries.\n"

.PHONY: all libs build android install clean reset help
