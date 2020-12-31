package skill.repository

import java.sql.Connection
import java.util.UUID

import anorm.{Macro, RowParser, SQL}
import apiError.models.APIError
import javax.inject.Inject
import org.postgresql.util.PSQLException
import play.api.Logging
import play.api.db.Database
import skill.models.Skill

class SkillRepositoryDAO @Inject()(db: Database) extends Logging {
  val parser: RowParser[Skill] = Macro.namedParser[Skill]

  def get()(implicit conn: Connection): List[Skill] = {
    SQL("SELECT * FROM skills").as(parser.*)
  }

  def getBy(id: UUID)(implicit conn: Connection): Option[Skill] = {
    SQL("SELECT skill_id, name FROM skills WHERE skill_id = {id}::uuid")
      .on("id" -> id)
      .as(parser.singleOpt)
  }

  def insert(skill: Skill)(implicit conn: Connection): Either[APIError, Skill] = {
    try {
      SQL("INSERT INTO skills (skill_id, name) VALUES ({skill_id}::uuid, {name})")
        .on("skill_id" -> skill.skill_id, "name" -> skill.name)
        .execute()
      Right(skill)
    } catch {
      case e: PSQLException => {
        logger.error(e.getMessage())
        Left(APIError("PSQL error when inserting skill"))
      }
      case e: Exception => {
        logger.error(e.getMessage())
        Left(APIError("Unknown error when inserting skill"))
      }
    }
  }

  def update(id: UUID, skill: Skill)(implicit conn: Connection): Int = {
    SQL("UPDATE skills SET name = {name} WHERE skill_id = {id}::uuid")
      .on("id" -> skill.skill_id, "name" -> skill.name)
      .executeUpdate()
  }

  def delete(id: UUID)(implicit conn: Connection): Int = {
    SQL("DELETE FROM skills WHERE skill_id = {id}::uuid").on("id" -> id).executeUpdate()
  }
}
