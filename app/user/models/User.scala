package user.models

import java.util.UUID

import play.api.libs.json.Json

case class User(user_id: Option[UUID]  = None, name: String  = "", email: String  = "")

object User {
  implicit val userFormat = Json.format[User]
}