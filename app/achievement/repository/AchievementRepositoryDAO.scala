package achievement.repository

import java.sql.Connection
import java.util.UUID

import anorm.{Macro, RowParser, SQL}
import javax.inject.Inject
import play.api.db.Database
import achievement.models.DatabaseAchievement

class AchievementRepositoryDAO @Inject()(db: Database) {

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

  def insert(achievement: DatabaseAchievement)(implicit conn: Connection): Boolean = {
    SQL(
      "INSERT INTO achievements (achievement_id, profile_id, skill_id) VALUES ({achievement_id}::uuid, {profile_id}::uuid, {skill_id}::uuid)")
      .on("achievement_id" -> achievement.achievement_id,
          "profile_id"     -> achievement.profile_id,
          "skill_id"       -> achievement.skill_id)
      .execute()
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
}
