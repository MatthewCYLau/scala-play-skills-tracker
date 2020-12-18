package skill.controllers

import java.util.UUID

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import skill.models.{CreateSkill, Skill}
import skill.services.SkillService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SkillController @Inject()(val controllerComponents: ControllerComponents,
                                skillService: SkillService)(implicit ec: ExecutionContext)
    extends BaseController {

  def getSkills(): Action[AnyContent] = Action.async { implicit request =>
    skillService.getSkills().map { skills =>
      Ok(Json.toJson(skills))
    }
  }

  def getSkillById(id: UUID): Action[AnyContent] = Action.async { implicit request =>
    skillService.getSkillById(id).map {
      case Some(skill) => Ok(Json.toJson(skill))
      case None        => NotFound
    }
  }

  def deleteSkillById(id: UUID): Action[AnyContent] = Action.async { implicit request =>
    skillService.deleteSkillById(id).map { res =>
      res match {
        case 1 => Ok("Ok")
        case 0 => BadRequest("Error when deleting skill.")
      }
    }
  }

  def createSkill: Action[JsValue] = Action(parse.json).async { implicit request =>
    request.body
      .validate[CreateSkill]
      .fold(errors => Future { BadRequest(errors.mkString) }, newSkill => {
        skillService.createSkill(Skill(UUID.randomUUID(), newSkill.name)).map { _ =>
          Ok("Ok")
        }
      })
  }

  def updateSkillById(id: UUID): Action[JsValue] = Action(parse.json).async { implicit request =>
    request.body
      .validate[Skill]
      .fold(
        errors => Future { BadRequest(errors.mkString) },
        skill => {
          skillService.updateSkillById(id, skill).map { res =>
            res match {
              case 1 => Ok("Ok")
              case 0 => BadRequest("Error when updating skill.")
            }
          }
        }
      )
  }
}
