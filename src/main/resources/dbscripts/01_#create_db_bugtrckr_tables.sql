CREATE TABLE `User` (
  `id`          INT         NOT NULL AUTO_INCREMENT,
  `firstName`   VARCHAR(32) NOT NULL,
  `lastName`    VARCHAR(32) NOT NULL,
  `email`       VARCHAR(64) NOT NULL UNIQUE,
  `password`    VARCHAR(32),
  `role`        VARCHAR(15) NOT NULL,
  `projectId`   INT,
  `description` TEXT,
  `isDeleted`   BOOLEAN     NOT NULL DEFAULT '0',
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

CREATE TABLE `Release` (
  `id`          INT         NOT NULL AUTO_INCREMENT,
  `projectId`   INT         NOT NULL,
  `vesrion`     VARCHAR(32) NOT NULL,
  `status`      VARCHAR(11) NOT NULL,
  `description` TEXT,
  `isDeleted`   BOOLEAN     NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `Issue` (
  `id`             INT         NOT NULL AUTO_INCREMENT,
  `title`          VARCHAR(32) NOT NULL,
  `type`           VARCHAR(32) NOT NULL,
  `priority`       VARCHAR(32) NOT NULL,
  `status`         VARCHAR(32) NOT NULL,
  `releaseId`      INT         NOT NULL,
  `assigneeId`     INT         NOT NULL,
  `createTime`     DATE        NOT NULL,
  `dueDate`        DATE,
  `lastUpdateDate` DATE        NOT NULL,
  `estimateTime`   INT         NOT NULL,
  `parentId`       INT,
  `isDeleted`      BOOLEAN     NOT NULL DEFAULT '0',
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
  `id`               INT     NOT NULL AUTO_INCREMENT,
  `issueId`          INT     NOT NULL,
  `assignedToUserId` INT     NOT NULL,
  `parentId`         INT,
  `changedByUserId`  INT     NOT NULL,
  `isDeleted`        BOOLEAN NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `WorkLog` (
  `id`        INT     NOT NULL AUTO_INCREMENT,
  `issueId`   INT     NOT NULL,
  `userId`    INT     NOT NULL,
  `startTime`      DATE    NOT NULL,
  `amountOfTime`    INT     NOT NULL,
  `isDeleted` BOOLEAN NOT NULL DEFAULT '0',
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
  ADD CONSTRAINT `User_Project_fk` FOREIGN KEY (`projectId`) REFERENCES `Project` (`id`);

ALTER TABLE `Project`
  ADD CONSTRAINT `Project_User_fk` FOREIGN KEY (`projectManagerId`) REFERENCES `User` (`id`);

ALTER TABLE `Release`
  ADD CONSTRAINT `Release_Project_fk` FOREIGN KEY (`projectId`) REFERENCES `Project` (`id`);

ALTER TABLE `Issue`
  ADD CONSTRAINT `Issue_Release_fk` FOREIGN KEY (`releaseId`) REFERENCES `Release` (`id`);

ALTER TABLE `Issue`
  ADD CONSTRAINT `Issue_User_fk` FOREIGN KEY (`assigneeId`) REFERENCES `User` (`id`);

ALTER TABLE `Issue`
  ADD CONSTRAINT `Issue_Issue_fk` FOREIGN KEY (`parentId`) REFERENCES `Issue` (`id`);

ALTER TABLE `History`
  ADD CONSTRAINT `History_Issue_fk` FOREIGN KEY (`issueId`) REFERENCES `Issue` (`id`);

ALTER TABLE `History`
  ADD CONSTRAINT `History_User_fk` FOREIGN KEY (`assignedToUserId`) REFERENCES `User` (`id`);

ALTER TABLE `History`
  ADD CONSTRAINT `History_History_fk` FOREIGN KEY (`parentId`) REFERENCES `History` (`id`);

ALTER TABLE `History`
  ADD CONSTRAINT `History_User_fk` FOREIGN KEY (`changedByUserId`) REFERENCES `User` (`id`);

ALTER TABLE `WorkLog`
  ADD CONSTRAINT `WorkLog_Issue_fk` FOREIGN KEY (`issueId`) REFERENCES `Issue` (`id`);

ALTER TABLE `WorkLog`
  ADD CONSTRAINT `WorkLog_User_fk` FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

ALTER TABLE `LabelIssue`
  ADD CONSTRAINT `LabelIssue_Label_fk` FOREIGN KEY (`labelId`) REFERENCES `Label` (`id`);

ALTER TABLE `LabelIssue`
  ADD CONSTRAINT `LabelIssue_Issue_fk` FOREIGN KEY (`IssueId`) REFERENCES `Issue` (`id`);

