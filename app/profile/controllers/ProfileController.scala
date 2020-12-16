package profile.controllers

import java.util.UUID
import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import profile.models.Profile
import profile.services.ProfileService
import scala.concurrent.ExecutionContext
import java.util.UUID.randomUUID

@Singleton
class ProfileController @Inject()(val controllerComponents: ControllerComponents, profileService: ProfileService)(implicit ec: ExecutionContext) extends BaseController {

  val inMemoryProfiles = List(Profile(randomUUID(), "Jon", "jon@doe.com"), Profile(randomUUID(), "Jane", "jane@doe.com"))

  def getProfiles(): Action[AnyContent] = Action.async { implicit request =>
    profileService.getProfiles().map { profiles =>
      Ok(Json.toJson(profiles))
    }
  }

  def getProfileById(id: UUID): Action[AnyContent] = Action.async { implicit request =>
    profileService.getProfileById(id).map {
      case Some(profile) => Ok(Json.toJson(profile))
      case None => NotFound
    }
  }

  def deleteProfileById(id: Long): Action[AnyContent] = Action {
    inMemoryProfiles.find(profile => profile.profile_id == id).map { profile =>
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
