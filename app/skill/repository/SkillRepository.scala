package skill.repository

import java.util.UUID

import apiError.models.APIError
import skill.models.Skill

import scala.concurrent.Future

trait SkillRepository {
  def getSkills(): Future[List[Skill]]
  def getSkillById(id: UUID): Future[Option[Skill]]
  def createSkill(skill: Skill): Future[Either[APIError, Skill]]
  def deleteSkillById(id: UUID): Future[Int]
  def updateSkillById(id: UUID, skill: Skill): Future[Int]
}
