package achievement.models

import java.util.UUID

import play.api.libs.json.Json

case class DatabaseAchievement(achievement_id: UUID, user_id: UUID, skill_id: UUID)

object DatabaseAchievement {
  implicit val achievementFormat = Json.format[DatabaseAchievement]
}
