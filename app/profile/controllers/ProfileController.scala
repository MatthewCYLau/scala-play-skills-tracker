package profile.controllers

import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import profile.models.Profile
import profile.services.ProfileService

@Singleton
class ProfileController @Inject()(val controllerComponents: ControllerComponents, profileService: ProfileService) extends BaseController {

  val profiles = List(Profile(1, "Jon", "jon@doe.com"), Profile(2, "Jane", "jane@doe.com"))

  def getProfiles(): Action[AnyContent] = Action { implicit request =>
    val profiles = profileService.listProfiles()
    Ok(Json.toJson(profiles))
  }

  def getProfileById(id: Long): Action[AnyContent] = Action {
    profiles.find(profile => profile.id == id).map { profile =>
      Ok(Json.toJson(profile))
    }.getOrElse(NotFound)
  }

  def deleteProfileById(id: Long): Action[AnyContent] = Action {
    profiles.find(profile => profile.id == id).map { profile =>
      Ok(Json.toJson(profile))
    }.getOrElse(NotFound)
  }

  def createProfile: Action[AnyContent] = Action { request: Request[AnyContent] =>
    val body: AnyContent = request.body
    val jsonBody: Option[JsValue] = body.asJson
    jsonBody
      .map { json =>
        Ok(json)
      }
      .getOrElse {
        BadRequest("Expecting application/json request body")
      }
  }
}
