package achievement.repository

import java.util.UUID

import achievement.models.Achievement
import apiError.models.APIError

import scala.concurrent.Future

trait AchievementRepository {
  def getAchievements(): Future[List[Achievement]]
  def getAchievementById(id: UUID): Future[Option[Achievement]]
  def createAchievement(achievement: Achievement): Future[Option[APIError]]
  def deleteAchievementById(id: UUID): Future[Int]
  def updateAchievementById(id: UUID, achievement: Achievement): Future[Int]
  def countAchievementByProfileIdAndSkillId(profile_id: UUID, skill_id: UUID): Int
}
