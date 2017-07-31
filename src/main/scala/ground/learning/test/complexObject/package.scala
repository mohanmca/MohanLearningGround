package ground.learning.test

package object complexObject {

  case object LeaveWorkflow {
    val value = Case("Mohan_Submitting_Leave", acttasks, comptasks)
  }

  object Role extends Enumeration {
    type Role = Value
    val employee, manager, hr = Value
  }

  object TaskStatus extends Enumeration {
    type TaskStatus = Value
    val NotStarted, Active, Completed = Value
  }

  import TaskStatus._
  import Role._

  sealed trait workflow
  case class Task(name : String, status : TaskStatus, assignedRole : Role) extends workflow
  case class Case(name : String, activeTasks : List[Task], completedTasks : List[Task]) extends workflow

  private val acttasks = List(Task("HR_Approval", TaskStatus.Active, Role.hr))
  private val comptasks = List(
    Task("Employee_Request", TaskStatus.Completed, Role.employee),
    Task("Manager_Approval", TaskStatus.Completed, Role.manager))

}