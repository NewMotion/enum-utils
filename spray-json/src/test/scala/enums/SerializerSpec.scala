package enums

import org.specs2.mutable.Specification
import spray.json._

class SerializerSpec extends Specification with DefaultJsonProtocol{

  "simple string enums" should {
    "serialize/deserialize to json" in {
      import SomeEnum._
      case class Enclosing(value: SomeEnum)
      implicit val EnumJF = new SimpleStringEnumSerializer[SomeEnum](SomeEnum).enumFormat
      implicit val EnclosingJF = jsonFormat1(Enclosing)

      Enclosing(SomeValue).toJson mustEqual """{"value": "SomeValue"}""".parseJson
      """{"value": "SomeValue"}""".parseJson.convertTo[Enclosing] mustEqual Enclosing(SomeValue)
    }
  }
}

import enums.EnumUtils._
sealed trait SomeEnum extends Nameable
object SomeEnum extends Enumerable[SomeEnum] {
  case object SomeValue extends SomeEnum {def name = "SomeValue"}
  val values = Set(SomeValue)
}