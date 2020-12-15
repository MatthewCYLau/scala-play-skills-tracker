package profile.services

import profile.models.Profile

class ProfileService {

  val profiles = List(Profile(3, "Jon", "jon@doe.com"), Profile(4, "Jane", "jane@doe.com"))

  def listProfiles(): List[Profile] = {
    profiles
  }
}
