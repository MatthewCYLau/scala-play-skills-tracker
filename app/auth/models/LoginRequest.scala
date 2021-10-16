package auth.models
import play.api.libs.json.Json

case class LoginRequest(username: String, password: String)

object LoginRequest {
  implicit val loginRequestFormat = Json.format[LoginRequest]
}
