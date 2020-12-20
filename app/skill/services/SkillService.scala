package skill.services
import java.util.UUID

import javax.inject.Inject
import play.api.db.Database
import skill.models.Skill
import skill.repository.SkillRepositoryImpl

import scala.concurrent.{ExecutionContext, Future}

class SkillService @Inject()(db: Database,
                             databaseExecutionContext: ExecutionContext,
                             skillRepository: SkillRepositoryImpl) {

  def getSkills(): Future[List[Skill]] = {
    skillRepository.getSkills()
  }

  def getSkillById(id: UUID): Future[Option[Skill]] = {
    skillRepository.getSkillById(id)
  }

  def createSkill(skill: Skill): Future[Boolean] = {
    skillRepository.createSkill(skill)
  }

  def deleteSkillById(id: UUID): Future[Int] = {
    skillRepository.deleteSkillById(id)
  }

  def updateSkillById(id: UUID, skill: Skill): Future[Int] = {
    skillRepository.updateSkillById(id, skill)
  }
}
