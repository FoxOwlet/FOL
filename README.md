# FOL project

## Syntax example

```fol
ns com.foxowlet.demo
import random
import json :only [read]
export :all :except [names map set]

class Fox {
  val name: String
  var age: Int = 10

  constructor(name: String = "fox", age: Int) default // assign values to fields with matching names

  constructor(kw: Keyword) {
    val name: String = kw.name()
    this(name, 42)
    println("Secondary constructor called")
  }

  def fromJson(json: String): Fox {
    val obj: Map<String, Any> = read(json)
    Fox(obj["name"], obj["age"])
  }
}

// literals for sequences/collections
val names: String[] = ["foo" "bar" "buz"]
val map: Map<String, Int> = {"a": 1, "b": 2}
val set: Set<Int> = #{1 2 3}

def genFox(): Fox {
  val name: String = names[random.int(names.size())]
  val age: Int = random.int(20)
  Fox(age: age, name: name)
}

def syntaxTest(): Unit->Unit { // returns no-args function that returns nothing
  for (i : range(10)) { // loop over sequence
    when (i > 5) println("foo")
    println(if (i == 3) "three" else i.toString()) // if is an expression
    try
      i / 0
    catch e: Exception
      println("oops")
    finally
      println("finally")
    #(x: Int){println(x)}(x=12) // declare lambda and call it
    #(){println(10)} // return no-arg lambda
  }
}

def withVarargs(& ints: Int[]): Unit { // varargs declared with the & syntax
  println(ints.size())
}
withVarargs(1,2,3) // pass varargs explicitly
withVarargs(...[1,2,3]) // pass collection as varargs, same as withVarargs(1,2,3)

println(...{:obj 42}) // same as println(obj=42)
```