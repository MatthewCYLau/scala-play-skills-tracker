package profile.models

import java.util.UUID

import play.api.libs.json.Json

case class Profile(profile_id: Option[UUID]  = None , name: String  = "", email: String  = "")

object Profile {
  implicit val profileFormat = Json.format[Profile]
}