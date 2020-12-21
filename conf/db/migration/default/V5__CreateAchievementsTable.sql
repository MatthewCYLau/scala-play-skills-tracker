CREATE TABLE IF NOT EXISTS achievements(
   achievement_id UUID NOT NULL,
   profile_id UUID NOT NULL,
   skill_id UUID NOT NULL,
   PRIMARY KEY (achievement_id),
   CONSTRAINT fk_profile
      FOREIGN KEY(profile_id)
	  REFERENCES profiles(profile_id),
   CONSTRAINT fk_skill
      FOREIGN KEY(skill_id)
      REFERENCES skills(skill_id)
);