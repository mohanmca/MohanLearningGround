package ground.worksheet

import ground.learning.reactive.signal._

object SignalWS {
  var t = Signal(3)                               //> t  : ground.learning.reactive.signal.Signal[Int] = myValue :: 3, observers :
                                                  //| =  
  var b = t() + 3                                 //> java.lang.AssertionError: assertion failed: Cyclic signal definition
                                                  //| 	at scala.Predef$.assert(Predef.scala:165)
                                                  //| 	at ground.learning.reactive.signal.Signal.apply(Signal.scala:32)
                                                  //| 	at ground.worksheet.SignalWS$$anonfun$main$1.apply$mcV$sp(ground.workshe
                                                  //| et.SignalWS.scala:7)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:75)
                                                  //| 	at ground.worksheet.SignalWS$.main(ground.worksheet.SignalWS.scala:5)
                                                  //| 	at ground.worksheet.SignalWS.main(ground.worksheet.SignalWS.scala)

}