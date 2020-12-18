package skill.models

import java.util.UUID

import play.api.libs.json.Json

case class Skill(skill_id: UUID, name: String)

object Skill {
  implicit val skillFormat = Json.format[Skill]
}
