CREATE TABLE `User` (
  `id`            INT         NOT NULL AUTO_INCREMENT,
  `firstName`     VARCHAR(20) NOT NULL,
  `lastName`      VARCHAR(20) NOT NULL,
  `email`         VARCHAR(32) NOT NULL UNIQUE,
  `password`      VARCHAR(60) NOT NULL ,
  `role`          VARCHAR(20) NOT NULL,
  `projectId`     INT,
  `description`   TEXT,
  `isDeleted`     BOOLEAN     NOT NULL DEFAULT '0',
  /*For spring security*/
  `enabled`       TINYINT     NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `Project` (
  `id`                INT          NOT NULL AUTO_INCREMENT,
  `title`             VARCHAR(20) NOT NULL,
  `guestView`         BOOLEAN      NOT NULL,
  `guestCreateIssues` BOOLEAN      NOT NULL,
  `guestAddComment`   BOOLEAN      NOT NULL,
  `description`       TEXT,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `ProjectRelease` (
  `id`            INT         NOT NULL AUTO_INCREMENT,
  `projectId`     INT         NOT NULL,
  `version`       VARCHAR(32) NOT NULL,
  `releaseStatus` VARCHAR(11) NOT NULL DEFAULT 'OPEN',
  `description`   TEXT,
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
  `projectId`        INT         NOT NULL,
  `assigneeId`       INT         NOT NULL,
  `createdById`      INT,
  #TODO: createTime must be initialized only one time, lastUpdateDate => when anything is changed, just update this time.
  #   `createTime`       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  #   `dueDate`          DATE,
  #   `lastUpdateDate`   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  `createTime`       DATETIME    NOT NULL DEFAULT "2016-01-01",
  `dueDate`          DATETIME,
  `lastUpdateDate`   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `estimateTime`     INT         NOT NULL,
  `parentId`         INT,
  `description`      TEXT,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `Label` (
  `id`        INT         NOT NULL AUTO_INCREMENT,
  `title`     VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `History` (
  `id`               INT         NOT NULL AUTO_INCREMENT,
  `issueId`          INT         NOT NULL,
  `changedByUserId`  INT         ,
  `createTime`       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `action`           VARCHAR(15) NOT NULL,
  `assignedToUserId` INT         NOT NULL,
  `title`            VARCHAR(32),
  `type`             VARCHAR(32),
  `priority`         VARCHAR(32),
  `status`           VARCHAR(32),
  `description`      TEXT,
  `issuecomment`     TEXT,
  `anonymName`       VARCHAR(32),
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `WorkLog` (
  `id`           INT         NOT NULL AUTO_INCREMENT,
  `issueId`      INT         NOT NULL,
  `userId`       INT         NOT NULL,
  `startDate`    DATETIME    NOT NULL,
  `endDate`      DATETIME    NOT NULL,
  `amountOfTime` INT     NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `Label_Issue` (
  `labelId` INT,
  `issueId` INT,
PRIMARY KEY (`labelId`,`issueId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `IssueComment` (
  `id`            INT       NOT NULL AUTO_INCREMENT,
  `text`          TEXT      NOT NULL,
  `timeStamp`     DATETIME,
  `issueId`       INT       NOT NULL,
  `userId`        INT,
  `isEdited`      BOOLEAN   NOT NULL DEFAULT '0',
  `anonymousName` VARCHAR(32),
  PRIMARY KEY (`id`)
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
  ADD CONSTRAINT `Issue_fk3` FOREIGN KEY (`projectId`) REFERENCES `Project` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `History`
  ADD CONSTRAINT `History_fk0` FOREIGN KEY (`issueId`) REFERENCES `Issue` (`id`)
ON DELETE CASCADE;


ALTER TABLE `WorkLog`
  ADD CONSTRAINT `WorkLog_fk0` FOREIGN KEY (`issueId`) REFERENCES `Issue` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `WorkLog`
  ADD CONSTRAINT `WorkLog_fk1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `Label_Issue`
  ADD CONSTRAINT `Label_Issue_fk0` FOREIGN KEY (`labelId`) REFERENCES `Label` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `Label_Issue`
  ADD CONSTRAINT `Label_Issue_fk1` FOREIGN KEY (`issueId`) REFERENCES `Issue` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `IssueComment`
  ADD CONSTRAINT `IssueComments_fk0` FOREIGN KEY (`issueId`) REFERENCES `Issue` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `IssueComment`
  ADD CONSTRAINT `IssueComments_fk1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;