package enums

import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class EnumUtilsSpec extends Specification {

  "EnumUtils" should {
    import enums.EnumUtils._

    "enable searching for name" in new Scope {
      sealed trait SomeEnum extends Nameable
      object SomeEnum extends Enumerable[SomeEnum] {
        case object SomeValue extends SomeEnum { def name = "SomeValue" }
        val values = Set(SomeValue)
      }
      SomeEnum.withName("SomeValue") mustEqual Some(SomeEnum.SomeValue)
    }
  }

  "reflection.EnumUtils defined at top-level" should {

    "enable searching for name without explicitly defining the name in the enum" in {
      SomeReflectiveEnum.withName("SomeReflectiveValue") mustEqual Some(SomeReflectiveEnum.SomeReflectiveValue)
    }
  }
}

import enums.reflection.EnumUtils._
sealed trait SomeReflectiveEnum extends Nameable
object SomeReflectiveEnum extends Enumerable[SomeReflectiveEnum] {
  case object SomeReflectiveValue extends SomeReflectiveEnum
  val values = Set(SomeReflectiveValue)
}


