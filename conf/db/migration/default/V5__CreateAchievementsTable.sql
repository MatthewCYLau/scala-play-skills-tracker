CREATE TABLE IF NOT EXISTS achievements(
   achievement_id UUID NOT NULL,
   user_id UUID NOT NULL,
   skill_id UUID NOT NULL,
   PRIMARY KEY (achievement_id),
   CONSTRAINT fk_user
      FOREIGN KEY(user_id)
	  REFERENCES users(user_id),
   CONSTRAINT fk_skill
      FOREIGN KEY(skill_id)
      REFERENCES skills(skill_id)
);