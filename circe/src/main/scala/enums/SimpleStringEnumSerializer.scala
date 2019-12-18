package enums

import io.circe.{Decoder, Encoder}
import cats.implicits._

class SimpleStringEnumSerializer[T <: Nameable](enum: Enumerable[T]) {
  implicit val decoder: Decoder[T] = Decoder.decodeString.emap(x =>
    Either.fromOption(enum.withName(x), s"Unknown enum " + s"value: $x")
  )

  implicit val encoder: Encoder[T] = Encoder.encodeString.contramap(_.name)
}
