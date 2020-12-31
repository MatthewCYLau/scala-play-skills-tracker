ALTER TABLE achievements
   DROP CONSTRAINT fk_profile
 , ADD  CONSTRAINT fk_profile
   FOREIGN KEY (profile_id) REFERENCES profiles (profile_id) ON DELETE CASCADE;