INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('admin', 'admin', 'admin@ss.com', '$2a$12$r.6YLln22ky5r5Wlb38iZ.v2fG30U/77of3CrsWkL4rJGTo3NwW86', 'ROLE_ADMIN',
        'first');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_1', '1', '0', '0', 'description to project 1');
-- it doesn't work with INSERT INTO projectRelease syntax
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description, isDeleted)
VALUES ('1', 'Beta 0.6', 'CLOSED', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.
', '0');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description, isDeleted)
VALUES ('1', 'Beta 0.6.1', 'CLOSED', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium adipisci aperiam assumenda at aut corporis cumque cupiditate doloremque, earum error facilis inventore iste laboriosam magni, natus non officiis placeat quis rem sequi sit suscipit tenetur voluptate? Molestias perspiciatis reiciendis voluptatem!
', '0');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description, isDeleted)
VALUES ('1', 'Beta 0.7.5', 'IN_PROGRESS',
        'Adipisci autem cupiditate deleniti deserunt ducimus ex itaque mollitia non odio repellendus.', '0');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description, isDeleted)
VALUES ('1', '1.0', 'IN_PROGRESS',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.',
        '0');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description, isDeleted)
VALUES ('1', '2.4', 'OPEN',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.',
        '0');

INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('manager', 'manager', 'manager@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
        'ROLE_PROJECT_MANAGER', 'pm', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('developer', 'developer', 'developer@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
        'ROLE_DEVELOPER', 'developer', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('quality_e', 'quality_e', 'quality_e@ss.com', '$2a$12$bTRBMwG6lnCVzvVD4YuyGuIJu4uF3lOaaY/48mo5hoeSjpe5w.JHG',
        'ROLE_QA', 'qa', '1');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_2', '1', '1', '0', 'description to project 2');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description, isDeleted)
VALUES ('2', '2', 'OPEN', 'RELEASE 2', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('manager2', 'manager2', 'manager1@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
        'ROLE_PROJECT_MANAGER', 'pm', '2');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId) VALUES
  ('developer2', 'developer2', 'developer1@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
   'ROLE_DEVELOPER', 'developer', '2');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('quality_e2', 'quality_e2', 'quality_e1@ss.com', '$2a$12$bTRBMwG6lnCVzvVD4YuyGuIJu4uF3lOaaY/48mo5hoeSjpe5w.JHG',
        'ROLE_QA', 'qa', '2');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_3', '1', '1', '1', 'description to project 3');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description, isDeleted)
VALUES ('3', '3', 'OPEN', 'RELEASE 3', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES
  ('manager3', 'manager3', 'manager2@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
   'ROLE_PROJECT_MANAGER', 'pm', '3');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId) VALUES
  ('developer3', 'developer3', 'developer2@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
   'ROLE_DEVELOPER', 'developer',
   '3');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('quality_e3', 'quality_e3', 'quality_e2@ss.com', '$2a$12$bTRBMwG6lnCVzvVD4YuyGuIJu4uF3lOaaY/48mo5hoeSjpe5w.JHG',
        'ROLE_QA', 'qa', '3');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_4', '0', '0', '0', 'description to project 2');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description, isDeleted)
VALUES ('4', '4', 'OPEN', 'RELEASE 4', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES
  ('manager4', 'manager4', 'manager3@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
   'ROLE_PROJECT_MANAGER', 'pm', '4');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId) VALUES
  ('developer4', 'developer4', 'developer3@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
   'ROLE_DEVELOPER', 'developer',
   '4');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('quality_e4', 'quality_e4', 'quality_e3@ss.com', '$2a$12$bTRBMwG6lnCVzvVD4YuyGuIJu4uF3lOaaY/48mo5hoeSjpe5w.JHG',
        'ROLE_QA', 'qa', '4');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_5', '1', '1', '0', 'description to project 5');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_6', '1', '0', '1', 'description to project 6');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_7', '1', '0', '1', 'description to project 7');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_8', '1', '0', '1', 'description to project 8');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_9', '0', '0', '0', 'description to project 9');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_10', '1', '1', '1', 'description to project 10');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_11', '1', '1', '1', 'description to project 11');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_12', '1', '1', '0', 'description to project 12');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_13', '1', '1', '1', 'description to project 13');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_14', '0', '0', '0', 'description to project 14');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_15', '0', '0', '0', 'description to project 15');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_16', '1', '0', '1', 'description to project 16');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_17', '1', '0', '0', 'description to project 17');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_18', '1', '1', '0', 'description to project 18');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_19', '0', '0', '0', 'description to project 19');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_20', '1', '0', '1', 'description to project 20');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('project_21', '1', '1', '1', 'description to project 21');

INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('user1', 'user1', 'user1@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user1');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('user2', 'user2', 'user2@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user2');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('user3', 'user3', 'user3@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user3');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('user4', 'user4', 'user4@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user4');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('user5', 'user5', 'user5@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user5');

INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('guest1', 'guest1', 'guest1@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_GUEST',
   'guest1');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('guest2', 'guest2', 'guest2@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_GUEST',
   'guest2');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('guest3', 'guest3', 'guest3@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_GUEST',
   'guest3');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('guest4', 'guest4', 'guest4@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_GUEST',
   'guest4');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('guest5', 'guest5', 'guest5@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_GUEST',
   'guest5');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('guest6', 'guest6', 'guest6@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_GUEST',
   'guest6');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('guest7', 'guest7', 'guest7@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_GUEST',
   'guest7');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, enabled)
VALUES
  ('developer23', 'developer23', 'developer23@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
   'ROLE_DEVELOPER', 'pm', '1', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, enabled)
VALUES
  ('developer24', 'developer24', 'developer24@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
   'ROLE_DEVELOPER', 'pm', '1', '1');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Remove configs', 'TASK', 'LOW', '1', '2', '2016-02-02', '3', '1', 'some text', '0', 'IN_PROGRESS');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Fix UI in search', 'TASK', 'LOW', '1', '2', '2016-02-02', '10', '1', 'some text', '0', 'IN_PROGRESS');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('GUI doesn`t loads', 'BUG', 'LOW', '1', '26', '2016-02-02', '30', '1', 'some text', '0', 'INVALID');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Fix typos', 'TASK', 'LOW', '1', '2', '2016-02-02', '50', '1', 'some text', '0', 'OPEN');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Update libs', 'BUG', 'LOW', '1', '2', '2016-02-02', '13', '1', 'some text', '0', 'IN_PROGRESS');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Don`t store user', 'TASK', 'LOW', '1', '2', '2016-02-02', '54', '1', 'some text', '0', 'OPEN');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Slow speed', 'EPIC', 'LOW', '1', '2', '2016-02-02', '3', '1', 'some text', '0', 'IN_PROGRESS');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Not logout', 'TASK', 'LOW', '1', '2', '2016-02-02', '58', '1', 'some text', '0', 'QA_VALIDATION');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Add default val', 'TASK', 'LOW', '1', '4', '2016-02-02', '46', '1', 'some text', '0', 'IN_PROGRESS');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Fix JS', 'TASK', 'LOW', '1', '2', '2016-02-02', '12', '1', 'some text', '0', 'IN_PROGRESS');

INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility)
VALUES ('Check Opera browser', 'TASK', 'LOW', '2', '2', '2016-02-02', '40', '1', 'some text', '0');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility)
VALUES ('Fix styles', 'BUG', 'MEDIUM', '3', '3', '2016-02-02', '30', '2', 'some text', '0');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility)
VALUES ('Remove new user', 'IMPROVEMENT', 'HIGH', '4', '4', '2016-02-02', '25', '3', 'some text', '0');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility)
VALUES ('Add some options', 'EPIC', 'CRITICAL', '3', '3', '2016-02-02', '5', '4', 'some text', '0');
INSERT INTO Issue (title, type, priority, projectReleaseId, assigneeId, dueDate, estimateTime, parentId, description, editAbility)
VALUES ('Remove pictures', 'TASK', 'BLOCKER', '4', '4', '2016-02-02', '15', '5', 'some text', '0');

INSERT INTO User (firstName, lastName, email, password, role, description, enabled)
VALUES ('user6', 'user6', 'user6@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user6', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, enabled)
VALUES ('user7', 'user7', 'user7@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user7', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, enabled)
VALUES ('user8', 'user8', 'user8@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user8', '0');

INSERT INTO User (firstName, lastName, email, password, role, description, isDeleted)
VALUES ('user9', 'user9', 'user9@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user9', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, isDeleted)
VALUES
  ('user10', 'user10', 'user10@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user10', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, isDeleted)
VALUES
  ('user11', 'user11', 'user11@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user11', '1');

INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user12', 'user12', 'user12@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user12');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user13', 'user13', 'user13@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user13');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user14', 'user14', 'user14@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user14');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user15', 'user15', 'user15@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user15');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user16', 'user16', 'user16@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user16');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user17', 'user17', 'user17@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user17');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user18', 'user18', 'user18@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user18');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user19', 'user19', 'user19@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user19');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user20', 'user20', 'user20@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user20');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user21', 'user21', 'user21@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user21');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user22', 'user22', 'user22@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user22');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user23', 'user23', 'user23@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user23');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user24', 'user24', 'user24@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user24');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user25', 'user25', 'user25@ss.com', '$2a$12$7f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user25');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user26', 'user26', 'user26@ss.com', '$2a$12$3f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user26');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user27', 'user27', 'user27@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user27');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user28', 'user28', 'user28@ss.com', '$2a$12$7f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user28');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user29', 'user29', 'user29@ss.com', '$2a$12$3f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user29');

INSERT INTO User (firstName, lastName, email, password, role, description, projectId, isDeleted)
VALUES ('developer8', 'developer8', 'developer8@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
        'ROLE_DEVELOPER', 'developer8', '1', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, isDeleted)
VALUES ('developer9', 'developer9', 'developer9@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
        'ROLE_DEVELOPER', 'developer9', '1', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, isDeleted)
VALUES
  ('developer10', 'developer10', 'developer10@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
   'ROLE_DEVELOPER', 'developer10', '2', '1');


INSERT INTO User (firstName, lastName, email, password, role, description, projectId, isDeleted)
VALUES ('manager7', 'manager7', 'manager7@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
        'ROLE_PROJECT_MANAGER', 'pm', '1', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, isDeleted)
VALUES ('manager8', 'manager8', 'manager8@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
        'ROLE_PROJECT_MANAGER', 'pm', '2', '1');

INSERT INTO User (firstName, lastName, email, password, role, description, projectId, isDeleted)
VALUES
  ('developer8', 'developer8', 'developer124@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
   'ROLE_DEVELOPER', 'developer8', '1', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, isDeleted)
VALUES
  ('developer8', 'developer8', 'developer127@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
   'ROLE_DEVELOPER', 'developer8', '1', '1');

UPDATE User
SET imageData = LOAD_FILE('/var/lib/mysql-files/large.jpg'), imageFilename = 'large.jpg'
WHERE `role` = 'ROLE_ADMIN';

UPDATE User
SET imageData = LOAD_FILE('/var/lib/mysql-files/martin_schoeller1.jpg'), imageFilename = 'martin_schoeller1.jpg'
WHERE `role` = 'ROLE_USER';

UPDATE User
SET imageData = LOAD_FILE('/var/lib/mysql-files/mel-gibson-mugshot.jpg'), imageFilename = 'mel-gibson-mugshot.jpg'
WHERE `role` = 'ROLE_PROJECT_MANAGER';

UPDATE User
SET imageData   = LOAD_FILE('/var/lib/mysql-files/martin-schoeller-clint.jpg'),
  imageFilename = 'martin-schoeller-clint.jpg'
WHERE `role` = 'ROLE_DEVELOPER';

UPDATE User
SET imageData = LOAD_FILE('/var/lib/mysql-files/martin-schoeller-bill.jpg'), imageFilename = 'martin-schoeller-bill.jpg'
WHERE `role` = 'ROLE_QA';

UPDATE User
SET imageData = LOAD_FILE('/var/lib/mysql-files/famous1_mini.jpg'), imageFilename = 'famous1_mini.jpg'
WHERE `role` = 'ROLE_GUEST';

INSERT INTO Label (`title`, `isDeleted`) VALUES ('Java', '0');
INSERT INTO Label (`title`, `isDeleted`) VALUES ('C#', '0');
INSERT INTO Label (`title`, `isDeleted`) VALUES ('Python', '0');
INSERT INTO Label (`title`, `isDeleted`) VALUES ('HTML', '0');
INSERT INTO Label (`title`, `isDeleted`) VALUES ('CSS', '0');

