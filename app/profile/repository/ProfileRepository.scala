package profile.repository

import java.util.UUID

import profile.models.Profile

import scala.concurrent.Future

trait ProfileRepository {
  def getProfiles(): Future[List[Profile]]
  def getProfileById(id: UUID): Future[Option[Profile]]
  def createProfile(user: Profile): Future[Boolean]
  def deleteProfileById(id: UUID): Future[Int]
  def updateProfileById(id: UUID, user: Profile): Future[Int]
}
