package skill.repository

import java.util.UUID

import anorm._
import javax.inject.Inject
import play.api.db.Database
import skill.models.Skill

import scala.concurrent.{ExecutionContext, Future}

class SkillRepository @Inject()(db: Database, databaseExecutionContext: ExecutionContext) {

  val parser: RowParser[Skill] = Macro.namedParser[Skill]

  def getSkills(): Future[List[Skill]] = {
    Future {
      db.withConnection { implicit conn =>
        SQL"SELECT * FROM skills".as(parser.*)
      }
    }(databaseExecutionContext)
  }

  def getSkillById(id: UUID): Future[Option[Skill]] = {
    Future {
      db.withConnection { implicit conn =>
        SQL"SELECT skill_id, name FROM skills WHERE skill_id = ${id}::uuid".as(parser.singleOpt)
      }
    }(databaseExecutionContext)
  }

  def createSkill(skill: Skill): Future[Boolean] = {
    Future {
      db.withConnection { implicit conn =>
        SQL"INSERT INTO skills (skill_id, name) VALUES (${skill.skill_id}::uuid, ${skill.name})"
          .execute()
      }
    }(databaseExecutionContext)
  }

  def deleteSkillById(id: UUID): Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        SQL"DELETE FROM skills WHERE skill_id = ${id}::uuid".executeUpdate()
      }
    }(databaseExecutionContext)
  }

  def updateSkillById(id: UUID, skill: Skill): Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        SQL"UPDATE skills SET name = ${skill.name} WHERE skill_id = ${id}::uuid"
          .executeUpdate()
      }
    }(databaseExecutionContext)
  }
}
