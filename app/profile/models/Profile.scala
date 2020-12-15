package profile.models

import play.api.libs.json.Json

case class Profile(id: Long, name: String, email: String)

object Profile {
  implicit val profileFormat = Json.format[Profile]
}