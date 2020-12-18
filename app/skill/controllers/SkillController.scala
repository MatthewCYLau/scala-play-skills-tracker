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
    implicit val createSkillReads                  = Json.reads[CreateSkill]
    val createSkillFromJson: JsResult[CreateSkill] = Json.fromJson[CreateSkill](request.body)

    createSkillFromJson match {
      case JsSuccess(createSkill, _) =>
        val newSkill = Skill(UUID.randomUUID(), createSkill.name)
        skillService.createSkill(newSkill).map { _ =>
          Ok("Ok")
        }
      case e: JsError =>
        Future { BadRequest("Error when creating skill " + JsError.toJson(e).toString()) }
    }
  }

  def updateSkillById(id: UUID): Action[JsValue] = Action(parse.json).async { implicit request =>
    implicit val skillReads            = Json.reads[Skill]
    val skillFromJson: JsResult[Skill] = Json.fromJson[Skill](request.body)

    skillFromJson match {
      case JsSuccess(skill, _) =>
        skillService.updateSkillById(id, skill).map { res =>
          res match {
            case 1 => Ok("Ok")
            case 0 => BadRequest("Error when updating skill.")
          }
        }
      case e: JsError =>
        Future { BadRequest("Error when updating skill " + JsError.toJson(e).toString()) }
    }
  }
}
