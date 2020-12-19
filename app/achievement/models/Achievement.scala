package achievement.models

import java.util.UUID

import play.api.libs.json.Json
import skill.models.Skill
import user.models.User

case class Achievement(achievement_id: UUID, user: User, skill: Skill)

object Achievement {
  implicit val achievementFormat = Json.format[Achievement]
}
