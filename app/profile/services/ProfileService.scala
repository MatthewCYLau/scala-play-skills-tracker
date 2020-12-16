package profile.services
import java.util.UUID

import javax.inject.Inject
import play.api.db.Database
import profile.models.Profile
import profile.persistence.ProfileRepository

import scala.concurrent.{ExecutionContext, Future}

class ProfileService @Inject() (db: Database, databaseExecutionContext: ExecutionContext, profileRepository: ProfileRepository)  {

  def getProfiles(): Future[List[Profile]]  = {
    profileRepository.getProfiles()
  }

  def getProfileById(id: UUID): Future[Profile]  = {
    profileRepository.getProfileById(id)
  }
}
