CREATE TABLE `User` (
	`id` INT NOT NULL,
	`firstName` varchar(32) NOT NULL,
	`lastName` varchar(32) NOT NULL,
	`email` varchar(64) NOT NULL UNIQUE,
	`password` varchar(32),
	`role` varchar(15) NOT NULL,
	`projectId` INT,
	`description` TEXT,
	`isDeleted` BOOLEAN NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `Project` (
	`id` INT NOT NULL,
	`title` varchar(100) NOT NULL,
	`projectManagerId` INT NOT NULL,
	`guestView` BOOLEAN NOT NULL,
	`guestCreateIssues` BOOLEAN NOT NULL,
	`guestAddComment` BOOLEAN NOT NULL,
	`description` TEXT,
	`isDeleted` BOOLEAN NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `Release` (
	`id` INT NOT NULL,
	`projectId` INT NOT NULL,
	`vesrion` varchar(32) NOT NULL,
	`status` varchar(11) NOT NULL,
	`description` TEXT,
	`isDeleted` BOOLEAN NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `Issue` (
	`id` INT NOT NULL,
	`title` varchar(32) NOT NULL,
	`type` varchar(32) NOT NULL,
	`priority` varchar(32) NOT NULL,
	`status` varchar(32) NOT NULL,
	`releaseId` INT NOT NULL,
	`assigneeId` INT NOT NULL,
	`createTime` DATE NOT NULL,
	`dueDate` DATE,
	`lastUpdateDate` DATE NOT NULL,
	`estimateTime` INT NOT NULL,
	`parentId` INT,
	`isDeleted` BOOLEAN NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `Label` (
	`id` INT NOT NULL,
	`title` varchar(32) NOT NULL,
	`isDeleted` BOOLEAN NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `History` (
	`id` INT NOT NULL,
	`issueId` INT NOT NULL,
	`assignedToUserId` INT NOT NULL,
	`parentId` INT,
	`changedByUserId` INT NOT NULL,
	`isDeleted` BOOLEAN NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `WorkLog` (
	`id` INT NOT NULL,
	`issueId` INT NOT NULL,
	`userId` INT NOT NULL,
	`time` DATE NOT NULL,
	`amount` INT NOT NULL,
	`isDeleted` BOOLEAN NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `LabelIssue` (
	`labelId` INT,
	`IssueId` INT
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

ALTER TABLE `User` ADD CONSTRAINT `User_fk0` FOREIGN KEY (`projectId`) REFERENCES `Project`(`id`);

ALTER TABLE `Project` ADD CONSTRAINT `Project_fk0` FOREIGN KEY (`projectManagerId`) REFERENCES `User`(`id`);

ALTER TABLE `Release` ADD CONSTRAINT `Release_fk0` FOREIGN KEY (`projectId`) REFERENCES `Project`(`id`);

ALTER TABLE `Issue` ADD CONSTRAINT `Issue_fk0` FOREIGN KEY (`releaseId`) REFERENCES `Release`(`id`);

ALTER TABLE `Issue` ADD CONSTRAINT `Issue_fk1` FOREIGN KEY (`assigneeId`) REFERENCES `User`(`id`);

ALTER TABLE `Issue` ADD CONSTRAINT `Issue_fk2` FOREIGN KEY (`parentId`) REFERENCES `Issue`(`id`);

ALTER TABLE `History` ADD CONSTRAINT `History_fk0` FOREIGN KEY (`issueId`) REFERENCES `Issue`(`id`);

ALTER TABLE `History` ADD CONSTRAINT `History_fk1` FOREIGN KEY (`assignedToUserId`) REFERENCES `User`(`id`);

ALTER TABLE `History` ADD CONSTRAINT `History_fk2` FOREIGN KEY (`parentId`) REFERENCES `History`(`id`);

ALTER TABLE `History` ADD CONSTRAINT `History_fk3` FOREIGN KEY (`changedByUserId`) REFERENCES `User`(`id`);

ALTER TABLE `WorkLog` ADD CONSTRAINT `WorkLog_fk0` FOREIGN KEY (`issueId`) REFERENCES `Issue`(`id`);

ALTER TABLE `WorkLog` ADD CONSTRAINT `WorkLog_fk1` FOREIGN KEY (`userId`) REFERENCES `User`(`id`);

ALTER TABLE `LabelIssue` ADD CONSTRAINT `LabelIssue_fk0` FOREIGN KEY (`labelId`) REFERENCES `Label`(`id`);

ALTER TABLE `LabelIssue` ADD CONSTRAINT `LabelIssue_fk1` FOREIGN KEY (`IssueId`) REFERENCES `Issue`(`id`);

