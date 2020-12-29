package achievement.repository

import java.sql.Connection
import java.util.UUID

import anorm.{Macro, RowParser, SQL}
import anorm.SqlParser.scalar
import javax.inject.Inject
import play.api.db.Database
import achievement.models.DatabaseAchievement
import apiError.models.APIError
import org.postgresql.util.PSQLException
import play.api.Logging

class AchievementRepositoryDAO @Inject()(db: Database) extends Logging {

  val parser: RowParser[DatabaseAchievement] = Macro.namedParser[DatabaseAchievement]

  def get()(implicit conn: Connection): List[DatabaseAchievement] = {
    SQL("SELECT * FROM achievements").as(parser.*)
  }

  def getBy(id: UUID)(implicit conn: Connection): Option[DatabaseAchievement] = {
    SQL(
      "SELECT achievement_id, profile_id, skill_id FROM achievements WHERE achievement_id = {id}::uuid")
      .on("id" -> id)
      .as(parser.singleOpt)
  }

  def insert(achievement: DatabaseAchievement)(implicit conn: Connection): Option[APIError] = {
    try {
      SQL(
        "INSERT INTO achievements (achievement_id, profile_id, skill_id) VALUES ({achievement_id}::uuid, {profile_id}::uuid, {skill_id}::uuid)")
        .on("achievement_id" -> achievement.achievement_id,
            "profile_id"     -> achievement.profile_id,
            "skill_id"       -> achievement.skill_id)
        .execute()
      None
    } catch {
      case e: PSQLException => {
        logger.error((e.getMessage()))
        Some(APIError("PSQL error when inserting achievement"))
      }
      case e: Exception => {
        logger.error((e.getMessage()))
        Some(APIError("Unknown error when inserting achievement"))
      }
    }
  }

  def update(id: UUID, achievement: DatabaseAchievement)(implicit conn: Connection): Int = {
    SQL(
      "UPDATE achievements SET profile_id = {profile_id}::uuid, skill_id = {skill_id}::uuid WHERE achievement_id = {id}::uuid")
      .on("id"         -> achievement.achievement_id,
          "profile_id" -> achievement.profile_id,
          "skill_id"   -> achievement.skill_id)
      .executeUpdate()
  }

  def delete(id: UUID)(implicit conn: Connection): Int = {
    SQL("DELETE FROM achievements WHERE achievement_id = {id}::uuid").on("id" -> id).executeUpdate()
  }

  def countAchievementByProfileIdAndSkillId(profile_id: UUID, skill_id: UUID)(
      implicit conn: Connection): Int = {
    SQL(
      "SELECT COUNT(achievement_id) FROM achievements WHERE profile_id = {profile_id}::uuid AND skill_id = {skill_id}::uuid")
      .on("profile_id" -> profile_id, "skill_id" -> skill_id)
      .as(scalar[Int].single)
  }
}
