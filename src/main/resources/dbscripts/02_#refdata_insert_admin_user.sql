INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('admin', 'admin', 'admin@ss.com', 'admin', 'ROLE_ADMIN', 'first');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_1', '1', '0', '0', 'description to project 1');
-- it doesn't work with INSERT INTO projectRelease syntax
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description, isDeleted)
VALUES ('1', '1', 'OPEN', 'RELEASE 1', '0');

INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('manager', 'manager', 'manager@ss.com', 'manager', 'ROLE_PROJECT_MANAGER', 'pm', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('developer', 'developer', 'developer@ss.com', 'developer', 'ROLE_DEVELOPER', 'developer', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('quality_e', 'quality_e', 'quality_e@ss.com', 'quality_e', 'ROLE_QA', 'qa', '1');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_2', '1', '1', '0', 'description to project 2');
INSERT INTO ProjectRelease (id, projectId, version, releaseStatus, description, isDeleted)
VALUES ('2', '2', '2', 'OPEN', 'RELEASE 2', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('manager2', 'manager2', 'manager1@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_PROJECT_MANAGER', 'pm', '2');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId) VALUES
  ('developer2', 'developer2', 'developer1@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_DEVELOPER', 'developer', '2');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('quality_e2', 'quality_e2', 'quality_e1@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_QA', 'qa', '2');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_3', '1', '1', '1', 'description to project 3');
INSERT INTO ProjectRelease (id, projectId, version, releaseStatus, description, isDeleted)
VALUES ('3', '3', '3', 'OPEN', 'RELEASE 3', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('manager3', 'manager3', 'manager2@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_PROJECT_MANAGER', 'pm', '3');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId) VALUES
  ('developer3', 'developer3', 'developer2@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_DEVELOPER', 'developer', '3');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('quality_e3', 'quality_e3', 'quality_e2@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_QA', 'qa', '3');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_4', '0', '0', '0', 'description to project 2');
INSERT INTO ProjectRelease (id, projectId, version, releaseStatus, description, isDeleted)
VALUES ('4', '4', '4', 'OPEN', 'RELEASE 4', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('manager4', 'manager4', 'manager3@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_PROJECT_MANAGER', 'pm', '4');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId) VALUES
  ('developer4', 'developer4', 'developer3@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_DEVELOPER', 'developer', '4');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('quality_e4', 'quality_e4', 'quality_e3@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_QA', 'qa', '4');

INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('user1', 'user1', 'user1@ss.com', 'user1', 'ROLE_USER', 'user1');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('user2', 'user2', 'user2@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_USER', 'user2');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('user3', 'user3', 'user3@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_USER', 'user3');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('user4', 'user4', 'user4@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_USER', 'user4');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('user5', 'user5', 'user5@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_USER', 'user5');

INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('guest1', 'guest1', 'guest1@ss.com', 'guest1', 'ROLE_GUEST', 'guest1');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('guest2', 'guest2', 'guest2@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_GUEST', 'guest2');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('guest3', 'guest3', 'guest3@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_GUEST', 'guest3');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('guest4', 'guest4', 'guest4@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_GUEST', 'guest4');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('guest5', 'guest5', 'guest5@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_GUEST', 'guest5');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('guest6', 'guest6', 'guest6@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_GUEST', 'guest6');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('guest7', 'guest7', 'guest7@ss.com', '21232f297a57a5a743894a0e4a801fc3', 'ROLE_GUEST', 'guest7');

INSERT INTO Issue (title, type, priority, status, projectReleaseId, assigneeId, createTime, dueDate, lastUpdateDate, estimateTime, parentId, description, editAbility, isDeleted)
VALUES ('Issue1', 'TASK', 'LOW', 'OPEN', '2', '2', '2016-01-01', '2016-02-02', '2016-03-03', '32100000', '1','some text', '0', '0');
INSERT INTO Issue (title, type, priority, status, projectReleaseId, assigneeId, createTime, dueDate, lastUpdateDate, estimateTime, parentId, description, editAbility, isDeleted)
VALUES ('Issue2', 'BUG', 'MEDIUM', 'OPEN', '3', '3', '2016-01-01', '2016-02-02', '2016-03-03', '434500000', '2','some text', '0', '0');
INSERT INTO Issue (title, type, priority, status, projectReleaseId, assigneeId, createTime, dueDate, lastUpdateDate, estimateTime, parentId, description, editAbility, isDeleted)
VALUES
  ('Issue3', 'IMPROVEMENT', 'HIGH', 'OPEN', '4', '4', '2016-01-01', '2016-02-02', '2016-03-03', '534500000', '3','some text', '0', '0');
INSERT INTO Issue (title, type, priority, status, projectReleaseId, assigneeId, createTime, dueDate, lastUpdateDate, estimateTime, parentId, description, editAbility, isDeleted)
VALUES ('Issue4', 'EPIC', 'CRITICAL', 'OPEN', '3', '3', '2016-01-01', '2016-02-02', '2016-03-03', '53450000', '4','some text', '0', '0');
INSERT INTO Issue (title, type, priority, status, projectReleaseId, assigneeId, createTime, dueDate, lastUpdateDate, estimateTime, parentId, description, editAbility, isDeleted)
VALUES ('Issue5', 'TASK', 'BLOCKER', 'OPEN', '4', '4', '2016-01-01', '2016-02-02', '2016-03-03', '56450000', '5','some text', '0', '0');

INSERT INTO User (firstName, lastName, email, password, role, description, enabled)
VALUES ('user6', 'user6', 'user6@ss.com', 'user6', 'ROLE_USER', 'user6', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, enabled)
VALUES ('user7', 'user7', 'user7@ss.com', 'user7', 'ROLE_USER', 'user7', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, enabled)
VALUES ('user8', 'user8', 'user8@ss.com', 'user8', 'ROLE_USER', 'user8', '0');


INSERT INTO User (firstName, lastName, email, password, role, description, projectId, enabled)
VALUES ('developer5', 'developer5', 'developer5@ss.com', 'developer5', 'ROLE_DEVELOPER', 'developer5', '1', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, enabled)
VALUES ('developer6', 'developer6', 'developer6@ss.com', 'developer6', 'ROLE_DEVELOPER', 'developer6', '1', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, enabled)
VALUES ('developer7', 'developer7', 'developer7@ss.com', 'developer7', 'ROLE_DEVELOPER', 'developer7', '2', '0');


INSERT INTO User (firstName, lastName, email, password, role, description, projectId, enabled)
VALUES ('manager5', 'manager5', 'manager5@ss.com', 'manager5', 'ROLE_PROJECT_MANAGER', 'pm', '1', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, enabled)
VALUES ('manager6', 'manager6', 'manager6@ss.com', 'manager6', 'ROLE_PROJECT_MANAGER', 'pm', '2', '0');


INSERT INTO User (firstName, lastName, email, password, role, description, isDeleted)
VALUES ('user9', 'user9', 'user9@ss.com', 'user9', 'ROLE_USER', 'user9', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, isDeleted)
VALUES ('user10', 'user10', 'user10@ss.com', 'user10', 'ROLE_USER', 'user10', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, isDeleted)
VALUES ('user11', 'user11', 'user11@ss.com', 'user11', 'ROLE_USER', 'user11', '1');


INSERT INTO User (firstName, lastName, email, password, role, description, projectId, isDeleted)
VALUES ('developer8', 'developer8', 'developer8@ss.com', 'developer8', 'ROLE_DEVELOPER', 'developer8', '1', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, isDeleted)
VALUES ('developer9', 'developer9', 'developer9@ss.com', 'developer9', 'ROLE_DEVELOPER', 'developer9', '1', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, isDeleted)
VALUES ('developer10', 'developer10', 'developer10@ss.com', 'developer10', 'ROLE_DEVELOPER', 'developer10', '2', '1');


INSERT INTO User (firstName, lastName, email, password, role, description, projectId, isDeleted)
VALUES ('manager7', 'manager7', 'manager7@ss.com', 'manager7', 'ROLE_PROJECT_MANAGER', 'pm', '1', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, isDeleted)
VALUES ('manager8', 'manager8', 'manager8@ss.com', 'manager8', 'ROLE_PROJECT_MANAGER', 'pm', '2', '1');

UPDATE  User SET imageData = LOAD_FILE('/var/lib/mysql-files/large.jpg'), imageFilename='large.jpg' WHERE `role`='ROLE_ADMIN';
UPDATE  User SET imageData = LOAD_FILE('/var/lib/mysql-files/martin_schoeller1.jpg'), imageFilename='martin_schoeller1.jpg' WHERE `role`='ROLE_USER';
UPDATE  User SET imageData = LOAD_FILE('/var/lib/mysql-files/mel-gibson-mugshot.jpg'), imageFilename='mel-gibson-mugshot.jpg' WHERE `role`='ROLE_PROJECT_MANAGER';
UPDATE  User SET imageData = LOAD_FILE('/var/lib/mysql-files/martin-schoeller-clint.jpg'), imageFilename='martin-schoeller-clint.jpg' WHERE `role`='ROLE_DEVELOPER';
UPDATE  User SET imageData = LOAD_FILE('/var/lib/mysql-files/martin-schoeller-bill.jpg'), imageFilename='martin-schoeller-bill.jpg' WHERE `role`='ROLE_QA';
UPDATE  User SET imageData = LOAD_FILE('/var/lib/mysql-files/famous1_mini.jpg'), imageFilename='famous1_mini.jpg' WHERE `role`='ROLE_GUEST';

INSERT INTO Label (`title`, `isDeleted`) VALUES ('Java', '0');
INSERT INTO Label (`title`, `isDeleted`) VALUES ('C#', '0');
INSERT INTO Label (`title`, `isDeleted`) VALUES ('Python', '0');
INSERT INTO Label (`title`, `isDeleted`) VALUES ('HTML', '0');
INSERT INTO Label (`title`, `isDeleted`) VALUES ('CSS', '0');

