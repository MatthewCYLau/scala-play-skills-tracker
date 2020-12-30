package auth.models
import play.api.libs.json.Json

case class User(username: String, password: String, authToken: String, isAdmin: Boolean)

object User {
  implicit val userFormat = Json.format[User]
}
