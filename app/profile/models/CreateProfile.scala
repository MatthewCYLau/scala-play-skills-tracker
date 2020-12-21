package profile.models
import play.api.libs.json.Json

case class CreateProfile(name: String, email: String)

object CreateProfile {
  implicit val createUserFormat = Json.format[CreateProfile]
}
