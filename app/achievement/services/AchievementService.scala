package achievement.services
import java.util.UUID

import javax.inject.Inject
import achievement.models.{Achievement, DatabaseAchievement}
import achievement.repository.AchievementRepositoryImpl

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future}

class AchievementService @Inject()(achievementRepository: AchievementRepositoryImpl) {

  def getAchievements(): Future[List[Achievement]] = {
    achievementRepository.getAchievements()
  }

  def getAchievementById(id: UUID): Future[Option[Achievement]] = {
    achievementRepository.getAchievementById(id)
  }

  def createAchievement(achievement: DatabaseAchievement): Future[Boolean] = {
    checkIfCanCreateAchievement(achievement) match {
      case true  => achievementRepository.createAchievement(achievement)
      case false => Future { false }
    }
  }

  def deleteAchievementById(id: UUID): Future[Int] = {
    achievementRepository.deleteAchievementById(id)
  }

  def updateAchievementById(id: UUID, achievement: DatabaseAchievement): Future[Int] = {
    achievementRepository.updateAchievementById(id, achievement)
  }

  private def checkIfCanCreateAchievement(achievement: DatabaseAchievement): Boolean = {
    val count = achievementRepository.countAchievementByProfileIdAndSkillId(achievement.profile_id,
                                                                            achievement.skill_id)
    count match {
      case 0 => true
      case _ => false
    }
  }
}
