package user.models
import play.api.libs.json.Json

case class CreateUser(name: String, email: String)

object CreateUser {
  implicit val createUserFormat = Json.format[CreateUser]
}
