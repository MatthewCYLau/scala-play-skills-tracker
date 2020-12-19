package achievement.repository

import java.util.UUID

import anorm._
import javax.inject.Inject
import play.api.db.Database
import achievement.models.{Achievement, DatabaseAchievement}
import skill.models.Skill
import user.models.User

import scala.concurrent.{ExecutionContext, Future}

class AchievementRepository @Inject()(db: Database, databaseExecutionContext: ExecutionContext) {

  val parser: RowParser[DatabaseAchievement] = Macro.namedParser[DatabaseAchievement]
  val skillParser: RowParser[Skill]          = Macro.namedParser[Skill]
  val userParser: RowParser[User]            = Macro.namedParser[User]

  def getAchievements(): Future[List[Achievement]] = {
    Future {
      val databaseAchievementsList = db.withConnection { implicit conn =>
        SQL"SELECT * FROM achievements".as(parser.*)
      }

      def getSkillById(uuid: UUID): Skill = {
        db.withConnection { implicit conn =>
          SQL"SELECT skill_id, name FROM skills WHERE skill_id = ${uuid}::uuid".as(
            skillParser.single)
        }
      }

      def getUserById(uuid: UUID): User = {
        db.withConnection { implicit conn =>
          SQL"SELECT user_id, name, email FROM users WHERE user_id = ${uuid}::uuid".as(
            userParser.single)
        }
      }

      val achievementsList = databaseAchievementsList.map(
        databaseAchievement =>
          Achievement(databaseAchievement.achievement_id,
                      getUserById(databaseAchievement.user_id),
                      getSkillById(databaseAchievement.skill_id)))
      achievementsList
    }(databaseExecutionContext)
  }

  def getAchievementById(id: UUID): Future[Option[Achievement]] = {
    Future {
      val databaseAchievement = db.withConnection { implicit conn =>
        SQL"SELECT achievement_id, user_id, skill_id FROM achievements WHERE achievement_id = ${id}::uuid"
          .as(parser.singleOpt)
      }

      def getSkillById(uuid: UUID): Skill = {
        db.withConnection { implicit conn =>
          SQL"SELECT skill_id, name FROM skills WHERE skill_id = ${uuid}::uuid".as(
            skillParser.single)
        }
      }

      def getUserById(uuid: UUID): User = {
        db.withConnection { implicit conn =>
          SQL"SELECT user_id, name, email FROM users WHERE user_id = ${uuid}::uuid".as(
            userParser.single)
        }
      }

      databaseAchievement match {
        case Some(databaseAchievement) => {
          val achievement =
            Achievement(databaseAchievement.achievement_id,
                        getUserById(databaseAchievement.user_id),
                        getSkillById(databaseAchievement.skill_id))
          Option(achievement)
        }
        case None => None
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
