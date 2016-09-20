INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES ('admin', 'admin', 'admin@ss.com', '$2a$12$r.6YLln22ky5r5Wlb38iZ.v2fG30U/77of3CrsWkL4rJGTo3NwW86', 'ROLE_ADMIN',
        'first');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('BugTrckr', '1', '0', '0', ' Esse forensibus sententiae ut vel, pri te meis lucilius conceptam. Sumo evertitur ea per, populo aliquip dolores ei vis. Blandit expetendis no duo, augue nostro labitur te usu. Nam ne iriure aperiam.
 In veritus signiferumque quo, mea no alii fuisset, has in ipsum invidunt. Ei vero animal sea, et vix quaestio iudicabit signiferumque. Est ut alienum inimicus, enim dicat ea mea. His tantas semper argumentum ei, dictas admodum intellegam pri ad, alienum constituto repudiandae vix ex. Qui eros deseruisse ei, ad natum ludus scriptorem per, in vel lobortis salutandi prodesset.
 Accusamus patrioque nam in. Habemus adipisci eu est, has everti mollis voluptatibus at. Assum volutpat usu ei. Duo ex consulatu instructior, id dicta fierent mediocrem sed, mutat interpretaris te pri. Oratio sapientem adolescens ius id, an molestiae abhorreant eum, his suas dicit definitionem ut. Ut zril.');

INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v1.0.0', 'CLOSED', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus ncidunt sit amet, consectetur adipisicing ipsa magnam architecto dolor illum i, numquam illum incidunt ipsa magnam, numquam perferendis  perferendis quae quos ut? Ducimus eaque  Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.
');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v1.2.0', 'CLOSED', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium adipisci aperiam assumenda at aut corporis cumque cupiditate doloremque, earum error facilis inventore iste laboriosam magni, natus non officiis placeat quis rem sequi sit suscipit tenetur voluptate? Molestias perspiciatis reiciendis voluptatem!
');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v2.0.0', 'IN_PROGRESS',
        'Adipisci autem cupiditate deleniti deserunt ducimus ex itaque mollitia non odio repellendus.');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v2.1.0', 'IN_PROGRESS',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.'
        );
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v3.0.0 Beta', 'OPEN',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.'
        );
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v3.0.0 Rel. cand.', 'OPEN',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.'
        );
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v3.0.0', 'CLOSED',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.'
        );
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v3.1.2', 'IN_PROGRESS',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.'
        );
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v3.2.0', 'CLOSED',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.'
        );
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v3.3.0 Beta 1', 'OPEN',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.'
        );
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v3.3.0 Beta 2', 'CLOSED',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.'
        );
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v3.4.0 Rel. cand.', 'CLOSED',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.'
        );
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v3.4.1', 'OPEN',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.'
        );
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v3.4.2', 'OPEN',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.'
        );
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v4.0.0 Rel. cand.', 'OPEN',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.'
        );
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('1', 'v4.0.0', 'OPEN',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo harum inventore molestiae obcaecati porro quia quisquam repudiandae, sed veniam voluptas voluptates.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet architecto corporis deleniti dolores, eos error esse excepturi explicabo.'
        );

INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('Sergey', 'Brin', 'manager@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
        'ROLE_PROJECT_MANAGER', 'pm', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('Larry', 'Ellison', 'developer@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
        'ROLE_DEVELOPER', 'developer', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('Vint', 'Cerf', 'quality_e@ss.com', '$2a$12$bTRBMwG6lnCVzvVD4YuyGuIJu4uF3lOaaY/48mo5hoeSjpe5w.JHG',
        'ROLE_QA', 'qa', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, enabled)
VALUES ('Richard', 'Stallman', 'developer23@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
   'ROLE_DEVELOPER', 'dev', '1', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId, enabled)
VALUES ('Ken', 'Thompson', 'developer24@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
   'ROLE_DEVELOPER', 'qa', '1', '1');
   INSERT INTO User (firstName, lastName, email, password, role, description, projectId, enabled)
VALUES ('Ada', 'Lovelace', 'developer249@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
   'ROLE_DEVELOPER', 'qa', '1', '1');
   INSERT INTO User (firstName, lastName, email, password, role, description, projectId, enabled)
VALUES ('Tim', 'Bray', 'dev112@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
   'ROLE_QA', 'qa', '1', '1');
      INSERT INTO User (firstName, lastName, email, password, role, description, projectId, enabled)
VALUES ('John', 'Carmack', 'developer224@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
   'ROLE_DEVELOPER', 'qa', '1', '1');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Newhow', '1', '1', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('2', '2', 'OPEN', 'RELEASE 2');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('Kristina', 'Vasquez', 'manager1@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
        'ROLE_PROJECT_MANAGER', 'pm', '2');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId) VALUES
  ('Yvonne', 'Stevenson', 'developer1@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
   'ROLE_DEVELOPER', 'developer', '2');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('Justin', '	Mills', 'quality_e1@ss.com', '$2a$12$bTRBMwG6lnCVzvVD4YuyGuIJu4uF3lOaaY/48mo5hoeSjpe5w.JHG',
        'ROLE_QA', 'qa', '2');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('U-dincon', '1', '1', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('3', '3', 'OPEN', 'RELEASE 3');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES
  ('Dallas', 'Morgan', 'manager2@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
   'ROLE_PROJECT_MANAGER', 'pm', '3');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId) VALUES
  ('Emily', 'Reyes', 'developer2@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
   'ROLE_DEVELOPER', 'developer',
   '3');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('Lisa', 'Maldonado', 'quality_e2@ss.com', '$2a$12$bTRBMwG6lnCVzvVD4YuyGuIJu4uF3lOaaY/48mo5hoeSjpe5w.JHG',
        'ROLE_QA', 'qa', '3');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Zuming ', '0', '0', '0', 'description to project 2');
INSERT INTO ProjectRelease (projectId, version, releaseStatus, description)
VALUES ('4', '4', 'OPEN', 'RELEASE 4');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES
  ('Henrietta', 'Alvarado', 'manager3@ss.com', '$2a$12$xic5wp8Nbgq2gyZtr/K0eevVPYzH/6XnEhOof4WSyChayZgN.unom',
   'ROLE_PROJECT_MANAGER', 'pm', '4');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId) VALUES
  ('Danny', 'Greene', 'developer3@ss.com', '$2a$12$gKyrJQIWc6Q7EQXv9feAKOKnMf/DZWnNxn.5IzoBD2YBK/nVRFRV2',
   'ROLE_DEVELOPER', 'developer',
   '4');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('Eva', 'Beck', 'quality_e3@ss.com', '$2a$12$bTRBMwG6lnCVzvVD4YuyGuIJu4uF3lOaaY/48mo5hoeSjpe5w.JHG',
        'ROLE_QA', 'qa', '4');

INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Bamcare', '1', '1', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Rocky Railroad', '1', '0', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Faxgreen', '1', '0', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Zimcone', '1', '0', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Joyware', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Codetex', '1', '1', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Blue Smoke', '1', '1', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Strongfase', '1', '1', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Freelam', '1', '1', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Lucky Fox', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Zathcan', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Whiteline', '1', '0', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Gamma', '1', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Plexfi', '1', '1', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Spantanhow', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('Eager Cloud', '1', '0', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');
INSERT INTO Project (title, guestView, guestCreateIssues, guestAddComment, description)
VALUES ('ganzphase', '1', '1', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus architecto dolor illum incidunt ipsa magnam, numquam perferendis quae quos ut? Ducimus eaque quis quos.');

INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('Everett',	'Bishop', 'user1@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_DEVELOPER',
        'user1', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('Jamie',	'Payne', 'user2@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_DEVELOPER',
        'user2', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('Alvin',	'Pittman', 'user3@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_DEVELOPER',
        'user3', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('Noah',	'Singleton', 'user4@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_DEVELOPER',
        'user4', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES ('Orlando',	'Gordon', 'user5@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_QA',
        'user5', '1');

INSERT INTO User (firstName, lastName, email, password, role, description, projectId)
VALUES
  ('Barry',	'Hopkins', 'guest1@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_QA',
   'guest1', '1');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Nelson',	'Williams', 'guest2@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_USER',
   'guest2');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Leland',	'Joseph', 'guest3@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_USER',
   'guest3');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Felipe',	'Swanson', 'guest4@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_USER',
   'guest4');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Orville',	'Mullins', 'guest5@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_USER',
   'guest5');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Irving',	'Page', 'guest6@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_USER',
   'guest6');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Joshua',	'Rose', 'guest7@ss.com', '$2a$12$xcB0vHLRtCas3kNsZszwpewlCE35Zlc37fB4ZUJVDG9qiXlPyHxL6', 'ROLE_USER',
   'guest7');

INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Remove configs', 'TASK', 'LOW', '1', '1', '2', '2016-02-02', '3', '1', 'some text', '0', 'IN_PROGRESS');
INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Fix UI in search', 'TASK', 'LOW', '1', '1', '4', '2016-02-02', '10', '1', 'some text', '0', 'IN_PROGRESS');
INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('GUI doesn`t loads', 'BUG', 'LOW', '1', '1', '3', '2016-02-02', '30', '1', 'some text', '0', 'INVALID');
INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Fix typos', 'TASK', 'LOW', '1', '1', '2', '2016-02-02', '50', '1', 'some text', '0', 'OPEN');
INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Update libs', 'BUG', 'LOW', '1', '1', '2', '2016-02-02', '13', '1', 'some text', '0', 'IN_PROGRESS');
INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Don`t store user', 'TASK', 'LOW', '1', '1', '5', '2016-02-02', '54', '1', 'some text', '0', 'OPEN');
INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Slow speed', 'EPIC', 'LOW', '1', '1', '2', '2016-02-02', '3', '1', 'some text', '0', 'IN_PROGRESS');
INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Not logout', 'TASK', 'LOW', '1', '1', '6', '2016-02-02', '58', '1', 'some text', '0', 'QA_VALIDATION');
INSERT INTO Issue (title, type, priority, projectReleaseId, projectId,  assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Add default val', 'TASK', 'LOW', '1', '1', '4', '2016-02-02', '46', '1', 'some text', '0', 'IN_PROGRESS');
INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility, status)
VALUES ('Fix JS', 'TASK', 'LOW', '1', '1', '2', '2016-02-02', '12', '1', 'some text', '0', 'IN_PROGRESS');


INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility)
VALUES ('Fix styles', 'BUG', 'MEDIUM', '3', '1', '3', '2016-02-02', '30', '2', 'some text', '0');
INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility)
VALUES ('Remove new user', 'IMPROVEMENT', 'HIGH', '4', '1', '4', '2016-02-02', '25', '3', 'some text', '0');
INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility)
VALUES ('Add some options', 'EPIC', 'CRITICAL', '3','1',  '3', '2016-02-02', '5', '4', 'some text', '0');
INSERT INTO Issue (title, type, priority, projectReleaseId, projectId, assigneeId, dueDate, estimateTime, parentId, description, editAbility)
VALUES ('Remove pictures', 'TASK', 'BLOCKER', '4', '1', '4', '2016-02-02', '15', '5', 'some text', '0');

INSERT INTO User (firstName, lastName, email, password, role, description, enabled)
VALUES ('Javier',	'Manning', 'user6@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user6', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, enabled)
VALUES ('Seth',	'Lucas', 'user7@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user7', '0');
INSERT INTO User (firstName, lastName, email, password, role, description, enabled)
VALUES ('Raul',	'Jenkins', 'user8@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user8', '0');

INSERT INTO User (firstName, lastName, email, password, role, description, isDeleted)
VALUES ('Enrique',	'Ross', 'user9@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
        'user9', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, isDeleted)
VALUES
  ('Jake',	'Wallace', 'user10@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user10', '1');
INSERT INTO User (firstName, lastName, email, password, role, description, isDeleted)
VALUES
  ('Homer', 'Gregory', 'user11@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user11', '1');

INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Wendell',	'Craig', 'user12@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user12');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user13', 'user13', 'user13@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user13');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Kelly',	'Abbott', 'user14@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user14');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Sean',	'Smith', 'user15@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user15');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Elmer',	'Howard', 'user16@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user16');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Kent',	'Brock', 'user17@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user17');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Rogelio',	'Greer', 'user18@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user18');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Raymond',	'Cole', 'user19@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user19');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Dana',	'Santos', 'user20@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user20');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Luther',	'Boone', 'user21@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user21');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Charlie',	'Wagner', 'user22@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user22');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Anthony',	'Patton', 'user23@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user23');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('user24', 'user24', 'user24@ss.com', '$2a$12$4f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
   'user24');
INSERT INTO User (firstName, lastName, email, password, role, description)
VALUES
  ('Ellis',	'Burke', 'user25@ss.com', '$2a$12$7f.T2qhKX9a4EUaN5otL4uAVIPYpO9yHu1nXM7CHf71sJnicp2oxy', 'ROLE_USER',
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

INSERT INTO Label (`title`) VALUES ('Java');
INSERT INTO Label (`title`) VALUES ('C#');
INSERT INTO Label (`title`) VALUES ('Python');
INSERT INTO Label (`title`) VALUES ('HTML');
INSERT INTO Label (`title`) VALUES ('CSS');

INSERT INTO Label_Issue (issueId, labelId) VALUES ('1','1');
INSERT INTO Label_Issue (issueId, labelId) VALUES ('1','3');
INSERT INTO Label_Issue (issueId, labelId) VALUES ('1','5');
INSERT INTO Label_Issue (issueId, labelId) VALUES ('2','4');
INSERT INTO Label_Issue (issueId, labelId) VALUES ('2','1');

INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '1', '3', '2', CURRENT_TIMESTAMP, '0', '0');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '3', '5', '2', CURRENT_TIMESTAMP, '2', '0');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '4', '6', '2', CURRENT_TIMESTAMP, '3', '1');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '2', '2', '6', CURRENT_TIMESTAMP, '1', '0');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '6', '4', '2', CURRENT_TIMESTAMP, '0', '1');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '8', '7', '2', CURRENT_TIMESTAMP, '0', '1');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '4', '4', '2', CURRENT_TIMESTAMP, '1', '1');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '2', '2', '2', CURRENT_TIMESTAMP, '0', '0');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '3', '6', '2', CURRENT_TIMESTAMP, '2', '0');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '4', '3', '2', CURRENT_TIMESTAMP, '3', '1');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '6', '5', '7', CURRENT_TIMESTAMP, '1', '0');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '6', '7', '2', CURRENT_TIMESTAMP, '0', '1');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '5', '3', '2', CURRENT_TIMESTAMP, '0', '1');
INSERT INTO `History` (`id`, `issueId`, `assignedToUserId`, `changedByUserId`, `createTime`, `issueStatus`, `action`) VALUES (NULL, '1', '4', '2', CURRENT_TIMESTAMP, '1', '1');