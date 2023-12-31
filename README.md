# FOL project

FOL (FoxOwlet Language) - general purpose OOP/FP programming language made purely
for the entertainment/educational reasons.

Development process (in Ukrainian): [YouTube](https://www.youtube.com/playlist?list=PLVF003xr9wun6H0RipxlT2zjOHt1BmNts)


## Project structure

- fol-emulator - computer model (may be treated as a virtual machine for the FOL):
memory model, CPU emulation, etc.
- fol-ir - internal representation (syntax tree) of the FOL as well as parser implementation.
- fol-interpreter - Java-based interpreter for the FOL.

Upcoming:
- fol-asm - assembly language model (FOAL - FoxOwlet Assembly Language). 
Representation of the machine operations of the fol-emulator's CPU model.
- fol-compiler - compiler from FOL to the machine code (fol-emulator commands).
- fol-lsp - LSP server implementation for the FOL.

## Running FOL

Requires JRE 21+ and FOL interpreter uberjar (`foli.jar`) which might be downloaded 
from the [releases page](https://github.com/FoxOwlet/FOL/releases) or [built from sources](#building-from-sources).

### From REPL

```shell
java -jar <path to foli.jar>
# or, if rlwrap is installed, 
rlwrap -a java -jar <path to foli.jar>
```
```fol
fol> var i: Int = 21 * 2
42
fol> print(i)
42
fol> exit
```

### In the interpreter mode
```shell
java -jar <path to foli.jar> <file> [<file>...]
```
E.g.
```shell
echo 'print(42)' > demo.fol # create a simple FOL program
java -jar foli.jar demo.fol # interpret it
```

## Building from sources

Dependencies:
- JDK 21
- Apache Maven

To obtain the latest interpreter uberjar (executable jar with all the required dependencies):
- Clone the repository locally
  ```shell
  git clone https://github.com/FoxOwlet/FOL.git
  ```
- Switch to the repository directory and run the build
  ```shell
  cd FOL
  mvn clean package -DskipTests
  ```
- `foli.jar` should be generated under the `dist/` directory

To run all the tests:
```shell
mvn clean test
```

### Running from IntelliJ IDEA

Make sure that `fol-ir/target/generated-sources/antlr4/` directory is marked as a Generated Sources Root.

## FOL grammar

See [fol.g4](fol-ir/src/main/antlr4/com/foxowlet/fol/parser/antlr/fol.g4) file for the [ANTLR4](https://github.com/antlr/antlr4/blob/master/doc/getting-started.md) grammar.

## Syntax example

Note: most features are not supported yet, this is present for the demonstration purpose only.

```fol
ns com.foxowlet.demo
import random
import json :only [read]
export :all :except [names map set]

class Fox {
  val name: String
  var age: Int = 10

  constructor(name: String, age: Int) default // assign values to fields with matching names

  constructor(kw: Keyword) {
    val name: String = kw.name()
    this(name, 42)
    println("Secondary constructor called")
  }

  def fromJson(json: String): Fox {
    val obj: Map[String, Any] = read(json) ;; generics with [T] syntax
    Fox(obj("name"), obj("age")) ;; Map is treated as a function from key to value
  }
}

// literals for sequences/collections
val names: Seq[String] = ["foo" "bar" "buz"]
val map: Map[String, Int] = {"a": 1, "b": 2}
val set: Set[Int] = #{1 2 3}

def genFox(): Fox {
  val name: String = names(random.int(names.size())) ;; Seq is treated as a function from Int index to value
  val age: Int = random.int(20)
  Fox(age: age, name: name)
}

def syntaxTest(): Unit->Unit { // returns no-args function that returns nothing
  for (i : range(10)) { // loop over sequence, i var is created implicitly
    when (i > 5) println("foo")
    println(if (i == 3) "three" else i.str()) // if is an expression
    #(x: Int){println(x)}(x: 12) // declare lambda and call it
    #(){println(10)} // return no-arg lambda
  }
}

def withVarargs(& ints: Seq[Int]): Unit { // varargs declared with the & syntax
  println(ints.size())
}
withVarargs(1,2,3) // pass varargs explicitly
withVarargs(...[1,2,3]) // pass collection as varargs, same as withVarargs(1,2,3)

println(...{:obj 42}) // same as println(obj: 42)
```
