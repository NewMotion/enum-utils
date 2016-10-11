package localization

import scala.util.Try
import java.util.Locale

trait CountryCode {
  def iso2: String
  def iso3: String
  def display: String
  override def toString = iso3
}

object CountryCode {

  val list: List[CountryCode] = (for {
    countryISO2 <- Locale.getISOCountries
    locale <- Try(new Locale("en", countryISO2)).toOption
  } yield CountryCodeImpl(countryISO2.toUpperCase, locale.getISO3Country.toUpperCase, locale.getDisplayCountry)).toList

  def findIso3(s: String): Option[CountryCode] = list.find(_.iso3 == s.toUpperCase)

  def findIso2(s: String): Option[CountryCode] = list.find(_.iso2 == s.toUpperCase)

  def isValidIso3(s: String): Boolean = list.exists(_.iso3 == s.toUpperCase)

  def isValidIso2(s: String): Boolean = list.exists(_.iso2 == s.toUpperCase)

  @throws(classOf[IllegalArgumentException])
  def apply(value: String): CountryCode = option(value).getOrElse {
    throw new IllegalArgumentException(s"Not a valid ISO2 or ISO3 country code: $value")
  }

  def option(value: String): Option[CountryCode] =
    value match {
      case s if isValidIso3(s) => findIso3(s)
      case s if isValidIso2(s) => findIso2(s)
      case _ => None
    }
}

@SerialVersionUID(0)
private case class CountryCodeImpl(iso2: String, iso3: String, display: String) extends CountryCode {
  override def equals(that: Any): Boolean = that match {
    case x: CountryCodeImpl => x.iso3.equals(this.iso3)
    case _                  => false
  }
}
