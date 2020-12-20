package achievement.repository

import java.util.UUID

import achievement.models.Achievement

import scala.concurrent.Future

trait AchievementRepository {
  def getAchievements(): Future[List[Achievement]]
  def getAchievementById(id: UUID): Future[Option[Achievement]]
  def createAchievement(achievement: Achievement): Future[Boolean]
  def deleteAchievementById(id: UUID): Future[Int]
  def updateAchievementById(id: UUID, achievement: Achievement): Future[Int]
}
