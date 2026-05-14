-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE,
    SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
-- Table `projects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projects` (
                                          `id` BIGINT NOT NULL AUTO_INCREMENT,
                                          `name` VARCHAR(100) NOT NULL,
                                          `status` ENUM('ACTIVE', 'DORMANT', 'CLOSED') NOT NULL DEFAULT 'ACTIVE',
                                          `admin_member_id` BIGINT NOT NULL,
                                          `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                                          `updated_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
                                              ON UPDATE CURRENT_TIMESTAMP(6),
                                          PRIMARY KEY (`id`),
                                          INDEX `idx_projects_admin_member_id` (`admin_member_id`),
                                          INDEX `idx_projects_status` (`status`)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `project_members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_members` (
                                                 `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                 `project_id` BIGINT NOT NULL,
                                                 `member_id` BIGINT NOT NULL,
                                                 `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

                                                 PRIMARY KEY (`id`),

                                                 UNIQUE KEY `uk_project_members_project_member`
                                                     (`project_id`, `member_id`),

                                                 KEY `idx_project_members_member_id`
                                                     (`member_id`),

                                                 CONSTRAINT `fk_project_members_project`
                                                     FOREIGN KEY (`project_id`)
                                                         REFERENCES `projects` (`id`)
                                                         ON DELETE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `milestones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `milestones` (
                                            `id` BIGINT NOT NULL AUTO_INCREMENT,
                                            `project_id` BIGINT NOT NULL,
                                            `name` VARCHAR(100) NOT NULL,
                                            `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                                            `updated_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
                                                ON UPDATE CURRENT_TIMESTAMP(6),

                                            PRIMARY KEY (`id`),

                                            UNIQUE KEY `uk_milestones_project_name`
                                                (`project_id`, `name`),

                                            CONSTRAINT `fk_milestones_project`
                                                FOREIGN KEY (`project_id`)
                                                    REFERENCES `projects` (`id`)
                                                    ON DELETE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tasks` (
                                       `id` BIGINT NOT NULL AUTO_INCREMENT,
                                       `project_id` BIGINT NOT NULL,
                                       `milestone_id` BIGINT NULL,
                                       `title` VARCHAR(200) NOT NULL,
                                       `content` TEXT NULL,
                                       `writer_member_id` BIGINT NOT NULL,
                                       `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                                       `updated_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
                                           ON UPDATE CURRENT_TIMESTAMP(6),

                                       PRIMARY KEY (`id`),

                                       KEY `idx_tasks_project_id` (`project_id`),
                                       KEY `idx_tasks_milestone_id` (`milestone_id`),
                                       KEY `idx_tasks_writer_member_id` (`writer_member_id`),

                                       CONSTRAINT `fk_tasks_project`
                                           FOREIGN KEY (`project_id`)
                                               REFERENCES `projects` (`id`)
                                               ON DELETE CASCADE,

                                       CONSTRAINT `fk_tasks_milestone`
                                           FOREIGN KEY (`milestone_id`)
                                               REFERENCES `milestones` (`id`)
                                               ON DELETE SET NULL
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comments` (
                                          `id` BIGINT NOT NULL AUTO_INCREMENT,
                                          `task_id` BIGINT NOT NULL,
                                          `writer_member_id` BIGINT NOT NULL,
                                          `content` TEXT NOT NULL,
                                          `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                                          `updated_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
                                              ON UPDATE CURRENT_TIMESTAMP(6),

                                          PRIMARY KEY (`id`),

                                          KEY `idx_comments_task_id` (`task_id`),
                                          KEY `idx_comments_writer_member_id` (`writer_member_id`),

                                          CONSTRAINT `fk_comments_task`
                                              FOREIGN KEY (`task_id`)
                                                  REFERENCES `tasks` (`id`)
                                                  ON DELETE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tags` (
                                      `id` BIGINT NOT NULL AUTO_INCREMENT,
                                      `project_id` BIGINT NOT NULL,
                                      `name` VARCHAR(50) NOT NULL,
                                      `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                                      `updated_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
                                          ON UPDATE CURRENT_TIMESTAMP(6),

                                      PRIMARY KEY (`id`),

                                      UNIQUE KEY `uk_tags_project_name`
                                          (`project_id`, `name`),

                                      CONSTRAINT `fk_tags_project`
                                          FOREIGN KEY (`project_id`)
                                              REFERENCES `projects` (`id`)
                                              ON DELETE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `task_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `task_tags` (
                                           `id` BIGINT NOT NULL AUTO_INCREMENT,
                                           `task_id` BIGINT NOT NULL,
                                           `tag_id` BIGINT NOT NULL,

                                           PRIMARY KEY (`id`),

                                           UNIQUE KEY `uk_task_tags_task_tag`
                                               (`task_id`, `tag_id`),

                                           KEY `idx_task_tags_task_id` (`task_id`),
                                           KEY `idx_task_tags_tag_id` (`tag_id`),

                                           CONSTRAINT `fk_task_tags_task`
                                               FOREIGN KEY (`task_id`)
                                                   REFERENCES `tasks` (`id`)
                                                   ON DELETE CASCADE,

                                           CONSTRAINT `fk_task_tags_tag`
                                               FOREIGN KEY (`tag_id`)
                                                   REFERENCES `tags` (`id`)
                                                   ON DELETE CASCADE
) ENGINE=InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;