package achievement.repository

import java.util.UUID

import anorm._
import javax.inject.Inject
import play.api.db.Database
import achievement.models.DatabaseAchievement

import scala.concurrent.{ExecutionContext, Future}

class AchievementRepository @Inject()(db: Database, databaseExecutionContext: ExecutionContext) {

  val parser: RowParser[DatabaseAchievement] = Macro.namedParser[DatabaseAchievement]

  def getAchievements(): Future[List[DatabaseAchievement]] = {
    Future {
      db.withConnection { implicit conn =>
        SQL"SELECT * FROM achievements".as(parser.*)
      }
    }(databaseExecutionContext)
  }

  def getAchievementById(id: UUID): Future[Option[DatabaseAchievement]] = {
    Future {
      db.withConnection { implicit conn =>
        SQL"SELECT achievement_id, user_id, skill_id FROM achievements WHERE achievement_id = ${id}::uuid"
          .as(parser.singleOpt)
      }
    }(databaseExecutionContext)
  }

  def createAchievement(achievement: DatabaseAchievement): Future[Boolean] = {
    Future {
      db.withConnection { implicit conn =>
        SQL"INSERT INTO achievements (achievement_id, user_id, skill_id) VALUES (${achievement.achievement_id}::uuid, ${achievement.user_id}::uuid, ${achievement.skill_id}::uuid)"
          .execute()
      }
    }(databaseExecutionContext)
  }

  def deleteAchievementById(id: UUID): Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        SQL"DELETE FROM achievements WHERE achievement_id = ${id}::uuid".executeUpdate()
      }
    }(databaseExecutionContext)
  }

  def updateAchievementById(id: UUID, achievement: DatabaseAchievement): Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        SQL"UPDATE achievements SET user_id = ${achievement.user_id}::uuid, skill_id = ${achievement.skill_id}::uuid WHERE achievement_id = ${id}::uuid"
          .executeUpdate()
      }
    }(databaseExecutionContext)
  }
}
