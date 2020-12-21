package achievement.models

import java.util.UUID

import play.api.libs.json.Json
import skill.models.Skill
import profile.models.Profile

case class Achievement(achievement_id: UUID, profile: Profile, skill: Skill)

object Achievement {
  implicit val achievementFormat = Json.format[Achievement]
}
