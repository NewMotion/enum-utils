package enums

import org.specs2.mutable.Specification
import io.circe._, io.circe.generic.semiauto._
import io.circe.syntax._
import io.circe.parser._

class SerializerSpec extends Specification {

  case class Enclosing(value: SomeEnum)
  import SomeEnum._

  "simple string enums" should {
    "serialize to json" in {
      implicit val e = new SimpleStringEnumSerializer[SomeEnum](SomeEnum).encoder
      implicit val enclosingEncoder: Encoder[Enclosing] = deriveEncoder[Enclosing]

      val json = parse("""{"value": "SomeValue"}""").getOrElse(throw new RuntimeException("Invalid Json!!"))
      Enclosing(SomeValue).asJson mustEqual json
    }

    "deserialize from json" in {
      implicit val d = new SimpleStringEnumSerializer[SomeEnum](SomeEnum).decoder
      implicit val enclosingDecoder: Decoder[Enclosing] = deriveDecoder[Enclosing]
      parse("""{"value": "SomeValue"}""").flatMap(_.as[Enclosing]) must beRight(Enclosing(SomeValue))
    }
  }
}

sealed trait SomeEnum extends Nameable
object SomeEnum extends Enumerable[SomeEnum] {
  case object SomeValue extends SomeEnum {def name = "SomeValue"}
  val values = Set(SomeValue)
}
