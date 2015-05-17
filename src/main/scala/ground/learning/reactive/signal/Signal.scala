package ground.learning.reactive.signal

import scala.util.DynamicVariable

class Signal[T](expr: => T) {
  import Signal._
  private var myExpr: () => T = _
  private var myValue: T = _
  private var observers: Set[Signal[_]] = Set()
  private var observed: List[Signal[_]] = Nil

  update(expr)

  def update(expr: => T): Unit = {
    myExpr = () => expr
    computeValue()
  }

  def computeValue(): Unit = {
    for (sig <- observed)
      sig.observers -= this
    observed = Nil
    val newValue = caller.withValue(this)(myExpr())
    myValue = newValue
    val oldObs = observers
    observers = Set[Signal[_]]()
    oldObs.foreach { dependentSignal => dependentSignal.computeValue() }
  }

  def apply() = {
    observers += caller.value
    assert(caller.value.observers.contains(this), "Cyclic signal definition");
    caller.value.observed ::= this
    myValue
  }

  override def toString: String = {
    "myValue :: " + myValue + ", " + "observers :=  " + observers.mkString(",") 
  }
}

object Signal {
  //private var caller = new StackableVariable[Signal[_]](NoSignal)
  val caller = new DynamicVariable[Signal[_]](NoSignal)

  def apply[T](expr: => T): Signal[T] = new Signal(expr)
}

object NoSignal extends Signal[Nothing](???) {
  override def computeValue(): Unit = ()
}

class Var[T](expr: => T) extends Signal[T](expr) {
  override def update(expr: => T): Unit = super.update(expr)
}

object Var {
  def apply[T](expr: => T): Signal[T] = new Var(expr)
}

class StackableVariable[T](init: T) {
  private var values: List[T] = List(init)
  def value = values.head
  def getValues = values
  def withValue[R](newValue: T)(op: => R): R = {
    values = newValue :: values
    try op finally values = values.tail
  }
}

/**
val caller = new StackableVariable(initSignal)
caller.withVariable(otherSignal) {}
....caller.value...
**/