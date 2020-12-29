package achievement.repository

import java.util.UUID

import javax.inject.Inject
import play.api.db.Database
import achievement.models.{Achievement, DatabaseAchievement}
import apiError.models.APIError
import skill.repository.SkillRepositoryDAO
import profile.repository.ProfileRepositoryDAO

import scala.concurrent.{ExecutionContext, Future}

class AchievementRepositoryImpl @Inject()(db: Database,
                                          databaseExecutionContext: ExecutionContext,
                                          achievementRepositoryDAO: AchievementRepositoryDAO,
                                          skillRepositoryDAO: SkillRepositoryDAO,
                                          profileRepositoryDAO: ProfileRepositoryDAO) {

  def getAchievements(): Future[List[Achievement]] = {
    Future {
      val databaseAchievementsList = db.withConnection { implicit conn =>
        achievementRepositoryDAO.get()
      }

      val achievementsList = databaseAchievementsList.map(
        databaseAchievement =>
          Achievement(
            databaseAchievement.achievement_id,
            db.withConnection { implicit conn =>
              profileRepositoryDAO.getBy(databaseAchievement.profile_id).get
            },
            db.withConnection { implicit conn =>
              skillRepositoryDAO.getBy(databaseAchievement.skill_id).get
            }
        ))
      achievementsList
    }(databaseExecutionContext)
  }

  def getAchievementById(id: UUID): Future[Option[Achievement]] = {
    Future {
      val databaseAchievement = db.withConnection { implicit conn =>
        achievementRepositoryDAO.getBy(id)
      }

      databaseAchievement match {
        case Some(databaseAchievement) => {
          val achievement =
            Achievement(
              databaseAchievement.achievement_id,
              db.withConnection { implicit conn =>
                profileRepositoryDAO.getBy(databaseAchievement.profile_id).get
              },
              db.withConnection { implicit conn =>
                skillRepositoryDAO.getBy(databaseAchievement.skill_id).get
              }
            )
          Option(achievement)
        }
        case None => None
      }

    }(databaseExecutionContext)
  }

  def createAchievement(achievement: DatabaseAchievement): Future[Option[APIError]] = {
    Future {
      db.withConnection { implicit conn =>
        achievementRepositoryDAO.insert(achievement)
      }
    }(databaseExecutionContext)
  }

  def deleteAchievementById(id: UUID): Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        achievementRepositoryDAO.delete(id)
      }
    }(databaseExecutionContext)
  }

  def updateAchievementById(id: UUID, achievement: DatabaseAchievement): Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        achievementRepositoryDAO.update(id, achievement)
      }
    }(databaseExecutionContext)
  }

  def countAchievementByProfileIdAndSkillId(profile_id: UUID, skill_id: UUID): Int = {
    db.withConnection { implicit conn =>
      achievementRepositoryDAO.countAchievementByProfileIdAndSkillId(profile_id, skill_id)
    }
  }
}
