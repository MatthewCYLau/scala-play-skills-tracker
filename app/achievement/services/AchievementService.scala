package achievement.services
import java.util.UUID

import javax.inject.Inject
import play.api.db.Database
import achievement.models.{Achievement, DatabaseAchievement}
import achievement.repository.AchievementRepository

import scala.concurrent.{ExecutionContext, Future}

class AchievementService @Inject()(db: Database,
                                   databaseExecutionContext: ExecutionContext,
                                   achievementRepository: AchievementRepository) {

  def getAchievements(): Future[List[Achievement]] = {
    achievementRepository.getAchievements()
  }

  def getAchievementById(id: UUID): Future[Option[Achievement]] = {
    achievementRepository.getAchievementById(id)
  }

  def createAchievement(achievement: DatabaseAchievement): Future[Boolean] = {
    achievementRepository.createAchievement(achievement)
  }

  def deleteAchievementById(id: UUID): Future[Int] = {
    achievementRepository.deleteAchievementById(id)
  }

  def updateAchievementById(id: UUID, achievement: DatabaseAchievement): Future[Int] = {
    achievementRepository.updateAchievementById(id, achievement)
  }
}
