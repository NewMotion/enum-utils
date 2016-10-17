package enums.reflection

import org.specs2.mutable.Specification
import spray.json._

class SerializerSpec extends Specification with DefaultJsonProtocol{

  "simple string enums defined using reflection" should {
    "serialize/deserialize to json" in {
      import SomeReflectiveEnum._
      case class Enclosing(value: SomeReflectiveEnum)
      implicit val EnumJF = new SimpleStringEnumSerializer[SomeReflectiveEnum](SomeReflectiveEnum).enumFormat
      implicit val EnclosingJF = jsonFormat1(Enclosing)

      Enclosing(SomeReflectiveValue).toJson mustEqual """{"value": "SomeReflectiveValue"}""".parseJson
      """{"value": "SomeReflectiveValue"}""".parseJson.convertTo[Enclosing] mustEqual Enclosing(SomeReflectiveValue)
    }
  }
}

import enums.reflection.EnumUtils._
sealed trait SomeReflectiveEnum extends Nameable
object SomeReflectiveEnum extends Enumerable[SomeReflectiveEnum] {
  case object SomeReflectiveValue extends SomeReflectiveEnum
  val values = Set(SomeReflectiveValue)
}