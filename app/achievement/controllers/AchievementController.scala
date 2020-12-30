package achievement.controllers

import java.util.UUID

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import achievement.models.{CreateAchievement, DatabaseAchievement}
import achievement.services.AchievementService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AchievementController @Inject()(
    val controllerComponents: ControllerComponents,
    achievementService: AchievementService)(implicit ec: ExecutionContext)
    extends BaseController {

  def getAchievements(): Action[AnyContent] = Action.async { implicit request =>
    achievementService.getAchievements().map { achievements =>
      Ok(Json.toJson(achievements))
    }
  }

  def getAchievementById(id: UUID): Action[AnyContent] = Action.async { implicit request =>
    achievementService.getAchievementById(id).map {
      case Some(achievement) => Ok(Json.toJson(achievement))
      case None              => NotFound
    }
  }

  def deleteAchievementById(id: UUID): Action[AnyContent] = Action.async { implicit request =>
    achievementService.deleteAchievementById(id).map { res =>
      res match {
        case 1 => Ok("Ok")
        case 0 => BadRequest("Error when deleting achievement")
      }
    }
  }

  def createAchievement: Action[JsValue] = Action(parse.json).async { implicit request =>
    request.body
      .validate[CreateAchievement]
      .fold(
        errors => Future { BadRequest(errors.mkString) },
        newAchievement => {
          achievementService
            .createAchievement(
              DatabaseAchievement(UUID.randomUUID(),
                                  newAchievement.profile_id,
                                  newAchievement.skill_id))
            .map { res =>
              res match {
                case Some(error) => BadRequest(error.errorMessage)
                case _           => Ok("Ok")
              }
            }
        }
      )
  }

  def updateAchievementById(id: UUID): Action[JsValue] = Action(parse.json).async {
    implicit request =>
      request.body
        .validate[DatabaseAchievement]
        .fold(
          errors => Future { BadRequest(errors.mkString) },
          achievement => {
            achievementService.updateAchievementById(id, achievement).map { res =>
              res match {
                case 1 => Ok("Ok")
                case 0 => BadRequest("Error when updating achievement")
              }
            }
          }
        )
  }
}
