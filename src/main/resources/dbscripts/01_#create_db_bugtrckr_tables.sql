CREATE TABLE `User` (
  `id`            INT         NOT NULL AUTO_INCREMENT,
  `firstName`     VARCHAR(32) NOT NULL,
  `lastName`      VARCHAR(32) NOT NULL,
  `email`         VARCHAR(64) NOT NULL UNIQUE,
  `password`      VARCHAR(32),
  `role`          VARCHAR(20) NOT NULL,
  `projectId`     INT,
  `description`   TEXT,
  `isDeleted`     BOOLEAN     NOT NULL DEFAULT '0',
  /*For spring security*/
  `enabled`       TINYINT     NOT NULL DEFAULT 1,
  `imageData`     MEDIUMBLOB,
  `imageFilename` VARCHAR(64),
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `Project` (
  `id`                INT          NOT NULL AUTO_INCREMENT,
  `title`             VARCHAR(100) NOT NULL,
  `guestView`         BOOLEAN      NOT NULL,
  `guestCreateIssues` BOOLEAN      NOT NULL,
  `guestAddComment`   BOOLEAN      NOT NULL,
  `description`       TEXT,
  `isDeleted`         BOOLEAN      NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

# Words such as ProjectRelease and status are reserved in MySQL
# https://dev.mysql.com/doc/refman/5.7/en/keywords.html

CREATE TABLE `ProjectRelease` (
  `id`            INT         NOT NULL AUTO_INCREMENT,
  `projectId`     INT         NOT NULL,
  `version`       VARCHAR(32) NOT NULL,
  `releaseStatus` VARCHAR(11) NOT NULL,
  `description`   TEXT,
  `isDeleted`     BOOLEAN     NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `Issue` (
  `id`               INT         NOT NULL AUTO_INCREMENT,
  `title`            VARCHAR(32) NOT NULL,
  `type`             VARCHAR(32) NOT NULL,
  `priority`         VARCHAR(32) NOT NULL,
  `status`           VARCHAR(32) NOT NULL DEFAULT 'OPEN',
  `projectReleaseId` INT         NOT NULL,
  `assigneeId`       INT         NOT NULL,

  #TODO: createTime must be initialized only one time, lastUpdateDate => when anything is changed, just update this time.
  #   `createTime`       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  #   `dueDate`          DATE,
  #   `lastUpdateDate`   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  `createTime`       DATETIME    NOT NULL DEFAULT "2016-01-01",
  `dueDate`          DATE,
  `lastUpdateDate`   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `estimateTime`     INT         NOT NULL,
  `parentId`         INT,
  `description`      TEXT,
  `editAbility`      BOOLEAN     NOT NULL DEFAULT '0',
  `isDeleted`        BOOLEAN     NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `Label` (
  `id`        INT         NOT NULL AUTO_INCREMENT,
  `title`     VARCHAR(32) NOT NULL,
  `isDeleted` BOOLEAN     NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `History` (
  `id`               INT         NOT NULL AUTO_INCREMENT,
  `issueId`          INT         NOT NULL,
  `assignedToUserId` INT         NOT NULL,
  `changedByUserId`  INT         NOT NULL,
  `createDate`       TIMESTAMP   NOT NULL,
  `issueStatus`      VARCHAR(32) NOT NULL,
  `action`           VARCHAR(15) NOT NULL,
  `isDeleted`        BOOLEAN     NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `WorkLog` (
  `id`           INT     NOT NULL AUTO_INCREMENT,
  `issueId`      INT     NOT NULL,
  `userId`       INT     NOT NULL,
  `startTime`    DATE    NOT NULL,
  `amountOfTime` INT     NOT NULL,
  `isDeleted`    BOOLEAN NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `LabelIssue` (
  `labelId` INT,
  `IssueId` INT
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

ALTER TABLE `User`
  ADD CONSTRAINT `User_fk0` FOREIGN KEY (`projectId`) REFERENCES `Project` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `ProjectRelease`
  ADD CONSTRAINT `ProjectRelease_fk0` FOREIGN KEY (`projectId`) REFERENCES `Project` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `Issue`
  ADD CONSTRAINT `Issue_fk0` FOREIGN KEY (`projectReleaseId`) REFERENCES `ProjectRelease` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `Issue`
  ADD CONSTRAINT `Issue_fk1` FOREIGN KEY (`assigneeId`) REFERENCES `User` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `Issue`
  ADD CONSTRAINT `Issue_fk2` FOREIGN KEY (`parentId`) REFERENCES `Issue` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `History`
  ADD CONSTRAINT `History_fk0` FOREIGN KEY (`issueId`) REFERENCES `Issue` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `History`
  ADD CONSTRAINT `History_fk1` FOREIGN KEY (`assignedToUserId`) REFERENCES `User` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `History`
  ADD CONSTRAINT `History_fk3` FOREIGN KEY (`changedByUserId`) REFERENCES `User` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `WorkLog`
  ADD CONSTRAINT `WorkLog_fk0` FOREIGN KEY (`issueId`) REFERENCES `Issue` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `WorkLog`
  ADD CONSTRAINT `WorkLog_fk1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `LabelIssue`
  ADD CONSTRAINT `LabelIssue_fk0` FOREIGN KEY (`labelId`) REFERENCES `Label` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `LabelIssue`
  ADD CONSTRAINT `LabelIssue_fk1` FOREIGN KEY (`IssueId`) REFERENCES `Issue` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;