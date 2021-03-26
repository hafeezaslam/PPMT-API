-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


DROP TABLE IF EXISTS `sql6401436`.`project` ;

CREATE TABLE IF NOT EXISTS `sql6401436`.`project` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `project_identifier` VARCHAR(5) NOT NULL,
                                                `project_name` VARCHAR(30) NOT NULL,
                                                `description` VARCHAR(60) NOT NULL,
                                                `project_leader` VARCHAR(45) NOT NULL,
                                                `start_date` VARCHAR(30) NULL,
                                                `end_date` VARCHAR(30) NULL,
                                                `created_at` VARCHAR(30) NULL,
                                                `updated_at` VARCHAR(30) NULL,
                                                `PT_sequence` INT NULL,
                                                `user_id` INT NOT NULL,
                                                PRIMARY KEY (`id`),
                                                UNIQUE INDEX `project_identifier_UNIQUE` (`project_identifier` ASC),
                                                CONSTRAINT `FK_user_id`
                                                    FOREIGN KEY (`user_id`)
                                                        REFERENCES `sql6401436`.`user` (`id`));

DROP TABLE IF EXISTS `sql6401436`.`project_task` ;

CREATE TABLE IF NOT EXISTS `sql6401436`.`project_task` (
                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                     `project_sequence` VARCHAR(10) NULL,
                                                     `summary` VARCHAR(45) NOT NULL,
                                                     `acceptance_criteria` VARCHAR(30) NULL,
                                                     `status` VARCHAR(15) NULL,
                                                     `priority` INT NULL,
                                                     `due_date` VARCHAR(30) NULL,
                                                     `project_identifier` VARCHAR(5) NULL,
                                                     `created_at` VARCHAR(30) NULL,
                                                     `updated_at` VARCHAR(30) NULL,
                                                     `project_id` INT NOT NULL,
                                                     PRIMARY KEY (`id`),
                                                     CONSTRAINT `FK_project_id`
                                                         FOREIGN KEY (`project_id`)
                                                             REFERENCES `sql6401436`.`project` (`id`));

DROP TABLE IF EXISTS `sql6401436`.`user` ;

CREATE TABLE IF NOT EXISTS `sql6401436`.`user` (
                                             `id` INT NOT NULL AUTO_INCREMENT,
                                             `username` VARCHAR(45) NULL,
                                             `full_name` VARCHAR(45) NULL,
                                             `password` VARCHAR(68) NULL,
                                             `created_at` VARCHAR(30) NULL,
                                             `updated_at` VARCHAR(30) NULL,
                                             PRIMARY KEY (`id`),
                                             UNIQUE INDEX `username_UNIQUE` (`username`));

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
