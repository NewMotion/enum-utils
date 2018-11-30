package enums.reflection

/**
  * This version of the enum utils can
  * only be used in top-level definitions,
  * otherwise the reflective .getSimpleName
  * call throws an exception.
  */
trait Nameable extends enums.Nameable {
  def name: String = this.getClass.getSimpleName
    .replaceAll("minus", "-").replaceAll("\\$", "")
}
