package profile.services
import java.util.UUID

import apiError.models.APIError
import javax.inject.Inject
import play.api.db.Database
import profile.models.Profile
import profile.repository.ProfileRepositoryImpl

import scala.concurrent.{ExecutionContext, Future}

class ProfileService @Inject()(db: Database, profileRepository: ProfileRepositoryImpl)(
    implicit ec: ExecutionContext) {

  def getProfiles(): Future[List[Profile]] = {
    profileRepository.getProfiles()
  }

  def getProfileById(id: UUID): Future[Option[Profile]] = {
    for (profile <- profileRepository.getProfileById(id)) yield profile
  }

  def createProfile(profile: Profile): Future[Option[APIError]] = {
    profileRepository.createProfile(profile)
  }

  def deleteProfileById(id: UUID): Future[Int] = {
    profileRepository.deleteProfileById(id)
  }

  def updateProfileById(id: UUID, profile: Profile): Future[Int] = {
    profileRepository.updateProfileById(id, profile)
  }
}
