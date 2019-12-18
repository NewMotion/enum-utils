package enums

import spray.json.{JsString, JsValue, JsonFormat, _}

class SimpleStringEnumSerializer[T <: Nameable](enum: Enumerable[T]) {
  implicit val enumFormat = new JsonFormat[T] {
    def write(x: T) = JsString(x.name)
    def read(value: JsValue) = value match {
      case JsString(x) => enum.withName(x).getOrElse(serializationError(s"Unknown enum " +
        s"value: $x"))
      case x => deserializationError("Expected value as JsString, but got " + x)
    }
  }
}
