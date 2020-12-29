package profile.repository

import java.util.UUID

import apiError.models.APIError
import profile.models.Profile

import scala.concurrent.Future

trait ProfileRepository {
  def getProfiles(): Future[List[Profile]]
  def getProfileById(id: UUID): Future[Option[Profile]]
  def createProfile(user: Profile): Future[Option[APIError]]
  def deleteProfileById(id: UUID): Future[Int]
  def updateProfileById(id: UUID, user: Profile): Future[Int]
}
