CREATE TABLE  `User` (
	`id` INT NOT NULL,
	`firstName` varchar(25) NOT NULL,
	`lastName` varchar(25) NOT NULL,
	`email` varchar(32) NOT NULL,
	`password` varchar(32) NOT NULL,
	`role` varchar(15) NOT NULL,
	`projectId` INT,
	`description` TEXT(10000),
	PRIMARY KEY (`id`)
);

CREATE TABLE `Project` (
	`id` INT NOT NULL,
	`title` varchar(100) NOT NULL,
	`projectManagerId` INT NOT NULL,
	`guestView` BOOLEAN NOT NULL,
	`guestCreateIssues` BOOLEAN NOT NULL,
	`guestAddComment` BOOLEAN NOT NULL,
	`description` varchar(10000),
	PRIMARY KEY (`id`)
);

CREATE TABLE `Release` (
	`id` INT NOT NULL,
	`projectId` INT NOT NULL,
	`vesrion` varchar(32) NOT NULL,
	`status` varchar(25) NOT NULL,
	`description` varchar(10000),
	PRIMARY KEY (`id`)
);

CREATE TABLE `Issue` (
	`id` INT NOT NULL,
	`title` varchar(25) NOT NULL,
	`type` varchar(25) NOT NULL,
	`priority` varchar(25) NOT NULL,
	`status` VARCHAR(255) NOT NULL,
	`releaseId` INT NOT NULL,
	`assigneeId` INT NOT NULL,
	`createTime` DATE NOT NULL,
	`dueDate` DATE NOT NULL,
	`lastUpdateDate` DATE NOT NULL,
	`estimateTime` INT NOT NULL,
	`parentId` INT,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Label` (
	`id` INT NOT NULL,
	`title` varchar(25) NOT NULL,
	`issueId` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `History` (
	`id` INT NOT NULL,
	`issueId` INT NOT NULL,
	`assigneeId` INT NOT NULL,
	`parentId` INT NOT NULL,
	`changedById` INT NOT NULL,
	`status` varchar(10) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `WorkLog` (
	`id` INT NOT NULL,
	`issueId` INT NOT NULL,
	`userId` INT NOT NULL,
	`time` DATE NOT NULL,
	`amount` INT NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `User` ADD CONSTRAINT `User_fk0` FOREIGN KEY (`projectId`) REFERENCES `Project`(`id`);

ALTER TABLE `Project` ADD CONSTRAINT `Project_fk0` FOREIGN KEY (`projectManagerId`) REFERENCES `User`(`id`);

ALTER TABLE `Release` ADD CONSTRAINT `Release_fk0` FOREIGN KEY (`projectId`) REFERENCES `Project`(`id`);

ALTER TABLE `Issue` ADD CONSTRAINT `Issue_fk0` FOREIGN KEY (`releaseId`) REFERENCES `Release`(`id`);

ALTER TABLE `Issue` ADD CONSTRAINT `Issue_fk1` FOREIGN KEY (`assigneeId`) REFERENCES `User`(`id`);

ALTER TABLE `Issue` ADD CONSTRAINT `Issue_fk2` FOREIGN KEY (`parentId`) REFERENCES `Issue`(`id`);

ALTER TABLE `Label` ADD CONSTRAINT `Label_fk0` FOREIGN KEY (`issueId`) REFERENCES `Issue`(`id`);

ALTER TABLE `History` ADD CONSTRAINT `History_fk0` FOREIGN KEY (`issueId`) REFERENCES `Issue`(`id`);

ALTER TABLE `WorkLog` ADD CONSTRAINT `WorkLog_fk0` FOREIGN KEY (`issueId`) REFERENCES `Issue`(`id`);

ALTER TABLE `WorkLog` ADD CONSTRAINT `WorkLog_fk1` FOREIGN KEY (`userId`) REFERENCES `User`(`id`);
