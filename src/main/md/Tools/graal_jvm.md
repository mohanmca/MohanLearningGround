# Graal
* JIT with API (Truffle layer)
* Modular JIT compiler
  * https://github.com/oracle/graal/tree/master/compiler/src
  * core, debug, hotspot, api, asm
  * loop, phases, truffle
  * amd64, sparc
* No circular dependency
* Better inlining and escape analysis
* Graal on JDK-8 requires bootstrap, as graal itself written in Java, it should be compiled and hotspot JVM should compile
* Graal on JDK-9 was already compiled using AOT, hence it doesn't require bootstrap
* PS Scavange cycles are 2.5% better for than non Graal vms for twitter (it is due to escape analysis)
* Old gen are 40MB more than non Graal vms for twitter (it is due to Graal itself requires additional RAM on JDK8)
* Graal uses 11% lesser CPU than C2 Hotspot. i.e $127 saving per CPU.
* java -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler => can unlock GraalVM on JDK9
* Twitter observed lots of performance improvement due to Scala usage, and they suspect Java may not get 10% of improvement, since Scala polymorphism is complext than Java
* Enterprise graal was giving 22% improvement compared to Open source Graal
  * It is doing better inline, and better escape analysis
  * It is not free 

# How Graal works
* Polyglot JIT VM
  * Languages are implemented again to suit Graal-JIT-VM
  * Call from Ruby to JS, and return would be within GraalVM
  * No performance punishment
* Graal :: function(bytecode) => Machinecode
* Graal :: JIT (+ JIT Library)
* Truffle is a layer on top of Grall, and provides lots of interfaces for other languages to use JIT
* Prior to Graal, Host language compiled into bytecode, and bytecode at runtime compiled into machine code, and host langauge itself can't access JIT compiler (one way)  
* In Graal, language can interact with JIT (two way)  using Truffle, and change the parameters of optimization
* Works using technique called "AST self specialization"
  * A node starts as generic type with un-initialized stated
  * Over time its type becomes specialized, and compiled to optimized machine code
  * Compilation using Partial evaluation produces Machine code
  * Partial evaluation
    * Take all the java code that interprets host language
    * Inline the above with the host-language specialized AST tree
    * Produce optimized machine code (Tom Stuart - https://www.youtube.com/watch?v=n_k6O50Nd-4) 
* Using truffle we can deoptimize the machine code and deconstruct the AST
  * It is required for languages like JS, Ruby are so dynamic, anything could happen there
* Cross language AST can be combined
  * If ruby method often calls JS
  * JS method is cloned and inlined into Ruby methods AST


# Truffle
* Infrastructure for languages to interoperate
* All the lanugages are implemented using common interface inside VM
* So two different language can invoke each other since they are implemented using common infrastructure
* 
```javascript
Truffle::Interop.eval('application/javascript',"
function add(a,b) { return a + b; }
Interop.export('add',add.bind(this));
")
add = Truffle::Interop.import('add');
puts add.call(42,24) 
``` 

# Hotspot JVM

* Java HotSpot VM 
  * Just-in-time (JIT) compiler
  * Byte codes are converted into highly optimized machine code
  
* When Hotspot vm kicks-in  
  * A method become eligible to be compiled once it was invoked many times, It is scheduled for compilation into machine code
  * Compilation happens in separate thread, in parallel to execution of non-compiled method invoked
  * -XX:+PrintCompilation - can be used to know the compilation details

* Hotspot optimization  
  * Java HotSpot VM will try to inline methods that contain less than 35 bytes of JVM bytecode.
  * Java HotSpot VM makes is monomorphic dispatch
    * All method call has only one implementation
    * There is only a single observed type per call site (i.e. the call site is "monomorphic")
    * There are two observed types per call site (i.e. the call site is "bimorphic")
    * Eliminate the overhead of doing virtual method lookup
  * Loop optimization, type sharpening, dead-code elimination, and intrinsics 

* Threee modes
  * Java HotSpot VM, there are actually two separate JIT compiler modes
  * C1 is used for applications where quick startup and rock-solid optimization are required
    * GUI application
    * -client mode
  * C2 was originally intended for long-running, predominantly server-side applications.
    * -server mode
  * Tiered compilation
    * Start with C1
    * Switch to C2
    * Default mode of Java SE 8

* Logging Hotspot JVM
  * -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation
    * Will produce hotspot_pid<PID>.log
  * -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation  -XX:LogFile=MyProcHSJVM.log
    * Customize the log file name  

# Hotspot JVM Issues

* Old and complex
* Old code base
* No real optimization in the last two years (as on 2018)

# Other

* Java 9 introduced compact string
  * Many characters require 16 bits to represent them but statistically most require only 8 bits — LATIN-1 character representation. 
  * private final byte coder; static final byte LATIN1 = 0; static final byte UTF16 = 1; //in java.lang.String
    * coder can be either LATIN1 or UTF16
  * java -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler => can unlock GraalVM on JDK9

# Tools

* Heapster provides an agent library to do heap profiling for JVM processes with output compatible with Google perftools. The goal of Heapster is to be able to do meaningful (sampled) heap profiling in a production setting.
* gperftools is a collection of a high-performance multi-threaded malloc() implementation, plus some pretty nifty performance analysis tools. 
 

# References

* [GeeCON Prague 2017: Chris Thalinger - Twitter's quest for a wholly Graal runtime](https://www.youtube.com/watch?v=pR5NDkIZBOA)
* [Graal: High-Performance Polyglot Runtime by Thomas Wuerthinger and Aleksandar Prokopec](https://www.youtube.com/watch?v=TQMKPRc6cbE
* [Compilers For Free by Tom Stuart](https://www.youtube.com/watch?v=n_k6O50Nd-4))
* https://www.slideshare.net/ThomasWuerthinger/graal-truffle-ethdec2013   
* https://shipilev.net/blog/2015/black-magic-method-dispatch/#_monomorphic_cases
* http://www.oracle.com/technetwork/articles/java/architect-evans-pt1-2266278.html
* https://github.com/gperftools/gperftools
* https://github.com/mariusae/heapster
* http://www.baeldung.com/java-9-compact-string

