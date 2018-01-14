package ground.learning.fp.FreeMonad
object FreeMonad {
  println("https://www.youtube.com/watch?v=7xSfLPD6tiQ")
  println("Pure Functional Database Programming with Fixpoint Types—Rob Norris")
  println("https://tpolecat.github.io/presentations/sw2016/slides.html#4")
  //A recursive data type for professors and their Ph.D. students
  object attempt_1 {
    case class Prof(
      name:     String,
      year:     Int,
      students: List[Prof])
    /*
					How do we store the auto-generated primary key, Simple INT won't be right way to do it
					CREATE TABLE prof (
					  id     INTEGER IDENTITY,
					  parent INTEGER     NULL,
					  name   VARCHAR NOT NULL,
					  year   INTEGER NOT NULL,
					  FOREIGN KEY(parent) REFERENCES prof(id)
					)
			  */
  }
  object attempt_2 {
    /**
     * id: Option[Int] - Represents three possibilities
     * 1. Just constructed objected, no ID in DB
     * 2. Just retrieved from DB, there is an id
     * 3. In some computation, where DB-ID is not important for computation
     */
    case class Prof(
      id:       Option[Int],
      name:     String,
      year:     Int,
      students: List[Prof])
    /*
					CREATE TABLE prof (
					  id     INTEGER IDENTITY,
					  parent INTEGER     NULL,
					  name   VARCHAR NOT NULL,
					  year   INTEGER NOT NULL,
					  FOREIGN KEY(parent) REFERENCES prof(id)
					)
			  */
  }
  object attempt_3 {
    /**
     * id: Let us handled ID separately
     * But it creates new problem as we can't keep "students: List[Prof]" since it looses id
     * it should be stored as "students: List[(Int, Prof)]"
     */
    case class Prof(
      name:     String,
      year:     Int,
      students: List[Prof])

    type IdProf = (Int, Prof)
  }
  object attempt_4 {
    /**
     * (Int, Prof) - Was not sure if it make sense, alternatively we can avoid to find the Type by parameterizing
     */
    case class ProfF[A](
      name:     String,
      year:     Int,
      students: List[A])

    object attempt_4_1 {
      //Here there is new problem - class ProfF takes type parameters
      type Prof = ProfF[ProfF]
      type IdProf = (Int, ProfF[(Int, ProfF)])
    }
    object attempt_4_2 {
      //Actually it is infinitely recursive
      type Prof = ProfF[ProfF[ProfF[ProfF[ProfF]]]]
      type IdProf = (Int, ProfF[(Int, ProfF[ProfF[ProfF[ProfF]]])])

    }
    /** Conclusion: Type aliases can't be recursive, but classes can be recursive */
  }

  object attempt_5 {
    /**
     * (Int, Prof) - Was not sure if it make sense, alternatively we can avoid to find the Type by parameterizing
     */
    case class ProfF[A](
      name:     String,
      year:     Int,
      students: List[A])

    object attempt_5_1 {
      case class Prof(value: ProfF[Prof])
      case class IdProf(id: Int, prof: ProfF[IdProf])
    }

    object attempt_5_2 {
      case class Prof[F[_]](value: F[Prof[F]])
      case class IdProf[F[_]](id: Int, prof: F[IdProf[F]])
    }

  }

  import scalaz._
  //Above types could be generalized further using following
  case class Fix[F[_]](unfix: F[Fix[F]])
  case class Cofree[F[_], A](head: A, tail: F[Cofree[F, A]])
  case class Free[F[_], A](head: A \/ F[Free[F, A]])
  
  object using_other_combinator {
  	case class CofreeF[F[_], A, B](head: A, tail: F[B])
  	case class FreeF[F[_], A,B](resume: A \/ F[B])
  	
  	//Below won't compile but ? means "I don't worry about any type"
  	//In typed method signature if _ comes, it means "partially applied"
  	type Cofree[F[_], A] = Fix[CofreeF[F, A, ?]]
  	type Free[F[_], A] = Fix[FreeF[F, A, ?]]
  	
  	//Annotated and un-annotated ProfF trees.
  	type Prof   = Fix[ProfF]
		type IdProf = Cofree[ProfF, Int]
    
  }
}