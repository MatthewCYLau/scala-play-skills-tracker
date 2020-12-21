package achievement.models
import java.util.UUID

import play.api.libs.json.Json

case class CreateAchievement(profile_id: UUID, skill_id: UUID)

object CreateAchievement {
  implicit val createAchievementFormat = Json.format[CreateAchievement]
}
