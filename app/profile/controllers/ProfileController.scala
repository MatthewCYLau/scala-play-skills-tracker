package profile.controllers

import java.util.UUID

import auth.services.AuthService
import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import profile.models.{CreateProfile, Profile}
import profile.services.ProfileService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProfileController @Inject()(val controllerComponents: ControllerComponents,
                                  profileService: ProfileService,
                                  authService: AuthService)(implicit ec: ExecutionContext)
    extends BaseController {

  def getProfiles(): Action[AnyContent] = Action.async { implicit request =>
    authService.withUser(_ =>
      profileService.getProfiles().map { profiles =>
        Ok(Json.toJson(profiles))
    })
  }

  def getProfileById(id: UUID): Action[AnyContent] = Action.async { implicit request =>
    authService.withUser(_ =>
      profileService.getProfileById(id).map {
        case Some(profile) => Ok(Json.toJson(profile))
        case None          => NotFound
    })
  }

  def deleteProfileById(id: UUID): Action[AnyContent] = Action.async { implicit request =>
    authService.withUser(_ =>
      profileService.deleteProfileById(id).map { res =>
        res match {
          case 1 => Ok("Ok")
          case 0 => BadRequest("Error when deleting profile.")
        }
    })
  }

  def createProfile: Action[JsValue] = Action(parse.json).async { implicit request =>
    request.body
      .validate[CreateProfile]
      .fold(
        errors => Future { BadRequest(errors.mkString) },
        newProfile => {
          profileService
            .createProfile(Profile(UUID.randomUUID(), newProfile.name, newProfile.email))
            .map { res =>
              res match {
                case Some(_) => BadRequest("Error when creating profile.")
                case _       => Ok("Ok")
              }
            }
        }
      )
  }

  def updateProfileById(id: UUID): Action[JsValue] = Action(parse.json).async { implicit request =>
    request.body
      .validate[Profile]
      .fold(
        errors => Future { BadRequest(errors.mkString) },
        profile => {
          profileService.updateProfileById(id, profile).map { res =>
            res match {
              case 1 => Ok("Ok")
              case 0 => BadRequest("Error when updating profile.")
            }
          }
        }
      )
  }
}
