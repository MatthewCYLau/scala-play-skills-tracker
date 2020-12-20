package skill.repository

import java.util.UUID

import anorm._
import javax.inject.Inject
import play.api.db.Database
import skill.models.Skill

import scala.concurrent.{ExecutionContext, Future}

class SkillRepositoryImpl @Inject()(db: Database,
                                    databaseExecutionContext: ExecutionContext,
                                    skillRepositoryDAO: SkillRepositoryDAO)
    extends SkillRepository {

  val parser: RowParser[Skill] = Macro.namedParser[Skill]

  def getSkills(): Future[List[Skill]] = {
    Future {
      db.withConnection { implicit conn =>
        skillRepositoryDAO.get()
      }
    }(databaseExecutionContext)
  }

  def getSkillById(id: UUID): Future[Option[Skill]] = {
    Future {
      db.withConnection { implicit conn =>
        skillRepositoryDAO.getBy(id)
      }
    }(databaseExecutionContext)
  }

  def createSkill(skill: Skill): Future[Boolean] = {
    Future {
      db.withConnection { implicit conn =>
        skillRepositoryDAO.insert(skill)
      }
    }(databaseExecutionContext)
  }

  def updateSkillById(id: UUID, skill: Skill): Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        skillRepositoryDAO.update(id, skill)
      }
    }(databaseExecutionContext)
  }

  def deleteSkillById(id: UUID): Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        skillRepositoryDAO.delete(id)
      }
    }(databaseExecutionContext)
  }
}
