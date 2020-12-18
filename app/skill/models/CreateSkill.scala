package skill.models
import play.api.libs.json.Json

case class CreateSkill(name: String)

object CreateSkill {
  implicit val createSkillFormat = Json.format[CreateSkill]
}
