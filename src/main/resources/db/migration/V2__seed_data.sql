INSERT INTO users (id, name, email, password_hash, role, account_status, created_at, updated_at) VALUES
(1, 'Admin User', 'admin@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'ADMIN', 'ACTIVE', now(), now()),
(2, 'Instructor One', 'instructor1@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'INSTRUCTOR', 'ACTIVE', now(), now()),
(3, 'Instructor Two', 'instructor2@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'INSTRUCTOR', 'ACTIVE', now(), now()),
(4, 'Instructor Three', 'instructor3@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'INSTRUCTOR', 'ACTIVE', now(), now()),
(5, 'Student One', 'student1@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'STUDENT', 'ACTIVE', now(), now()),
(6, 'Student Two', 'student2@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'STUDENT', 'ACTIVE', now(), now()),
(7, 'Student Three', 'student3@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'STUDENT', 'ACTIVE', now(), now()),
(8, 'Student Four', 'student4@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'STUDENT', 'ACTIVE', now(), now()),
(9, 'Student Five', 'student5@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'STUDENT', 'ACTIVE', now(), now()),
(10, 'Student Six', 'student6@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'STUDENT', 'ACTIVE', now(), now()),
(11, 'Student Seven', 'student7@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'STUDENT', 'ACTIVE', now(), now()),
(12, 'Student Eight', 'student8@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'STUDENT', 'ACTIVE', now(), now()),
(13, 'Student Nine', 'student9@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'STUDENT', 'ACTIVE', now(), now()),
(14, 'Student Ten', 'student10@portal.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.jR5u5MzdON3NofM8JrIoYNewc5hXt6', 'STUDENT', 'ACTIVE', now(), now());

INSERT INTO categories (id,name,description,created_at,updated_at) VALUES
(1,'Web Development','Frontend and backend web courses',now(),now()),
(2,'Data Science','Data analysis and ML fundamentals',now(),now()),
(3,'DevOps','CI/CD, Docker and cloud operations',now(),now()),
(4,'Mobile Development','Android and cross-platform development',now(),now());

INSERT INTO courses (id,title,description,thumbnail_url,category_id,instructor_id,level,duration,status,created_at,updated_at) VALUES
(1,'Spring Boot Masterclass','Build enterprise apps with Spring Boot','https://picsum.photos/200/120?1',1,2,'INTERMEDIATE',40,'PUBLISHED',now(),now()),
(2,'React from Zero','Complete React fundamentals','https://picsum.photos/200/120?2',1,2,'BEGINNER',30,'PUBLISHED',now(),now()),
(3,'PostgreSQL for Developers','SQL and optimization techniques','https://picsum.photos/200/120?3',2,2,'BEGINNER',24,'PUBLISHED',now(),now()),
(4,'Python for Data Science','Pandas, NumPy, visualization','https://picsum.photos/200/120?4',2,3,'BEGINNER',35,'PUBLISHED',now(),now()),
(5,'Machine Learning Essentials','Supervised and unsupervised models','https://picsum.photos/200/120?5',2,3,'INTERMEDIATE',45,'PUBLISHED',now(),now()),
(6,'Docker and Kubernetes','Containerization from scratch','https://picsum.photos/200/120?6',3,3,'INTERMEDIATE',38,'PUBLISHED',now(),now()),
(7,'CI/CD Pipeline Design','Automated build and deploy','https://picsum.photos/200/120?7',3,4,'ADVANCED',32,'PUBLISHED',now(),now()),
(8,'AWS Cloud Basics','Core AWS services and IAM','https://picsum.photos/200/120?8',3,4,'BEGINNER',28,'PUBLISHED',now(),now()),
(9,'Android App Development','Build Android apps using Kotlin','https://picsum.photos/200/120?9',4,4,'INTERMEDIATE',50,'PUBLISHED',now(),now()),
(10,'Flutter Bootcamp','Cross-platform app development','https://picsum.photos/200/120?10',4,4,'BEGINNER',36,'PUBLISHED',now(),now()),
(11,'Advanced React Patterns','Hooks, architecture and performance','https://picsum.photos/200/120?11',1,2,'ADVANCED',27,'PUBLISHED',now(),now()),
(12,'Data Engineering Foundations','Pipelines and warehousing','https://picsum.photos/200/120?12',2,3,'INTERMEDIATE',33,'PUBLISHED',now(),now());

INSERT INTO course_modules (id,course_id,title,order_index,created_at,updated_at) VALUES
(1,1,'Spring Core',1,now(),now()),(2,1,'REST APIs',2,now(),now()),(3,1,'Security',3,now(),now()),
(4,2,'React Basics',1,now(),now()),(5,2,'State Management',2,now(),now()),(6,2,'Routing and APIs',3,now(),now()),
(7,4,'Python Basics',1,now(),now()),(8,4,'Data Analysis',2,now(),now()),(9,4,'Visualization',3,now(),now());

INSERT INTO lessons (id,module_id,title,order_index,content_type,created_at,updated_at) VALUES
(1,1,'IoC and Dependency Injection',1,'ARTICLE',now(),now()),
(2,1,'Spring Boot Starters',2,'VIDEO',now(),now()),
(3,2,'Building REST Controllers',1,'VIDEO',now(),now()),
(4,2,'Validation and DTO Mapping',2,'PDF',now(),now()),
(5,3,'JWT Authentication',1,'VIDEO',now(),now()),
(6,3,'Role Based Authorization',2,'ARTICLE',now(),now()),
(7,4,'JSX and Components',1,'VIDEO',now(),now()),
(8,4,'Props and State',2,'ARTICLE',now(),now()),
(9,5,'Context API',1,'VIDEO',now(),now()),
(10,5,'Redux Toolkit Intro',2,'PDF',now(),now()),
(11,6,'React Router v6',1,'VIDEO',now(),now()),
(12,6,'Axios and Interceptors',2,'ARTICLE',now(),now()),
(13,7,'Python Syntax',1,'VIDEO',now(),now()),
(14,7,'Working with DataFrames',2,'ARTICLE',now(),now()),
(15,8,'Data Cleaning',1,'VIDEO',now(),now()),
(16,8,'Feature Engineering',2,'PDF',now(),now()),
(17,9,'Matplotlib Charts',1,'VIDEO',now(),now()),
(18,9,'Dashboard Storytelling',2,'ARTICLE',now(),now());

INSERT INTO materials (lesson_id,type,url_or_reference,created_at,updated_at) VALUES
(1,'ARTICLE','https://example.com/spring-ioc',now(),now()),
(2,'VIDEO','https://example.com/spring-starters',now(),now()),
(3,'VIDEO','https://example.com/rest-controllers',now(),now()),
(4,'PDF','https://example.com/dto-validation.pdf',now(),now()),
(5,'VIDEO','https://example.com/jwt-auth',now(),now()),
(7,'VIDEO','https://example.com/jsx-components',now(),now()),
(11,'VIDEO','https://example.com/react-router',now(),now()),
(13,'VIDEO','https://example.com/python-syntax',now(),now()),
(15,'VIDEO','https://example.com/data-cleaning',now(),now()),
(17,'VIDEO','https://example.com/matplotlib',now(),now());

INSERT INTO quizzes (id,lesson_id,course_id,title,created_at,updated_at) VALUES
(1,5,1,'Spring Security Quiz',now(),now()),
(2,11,2,'React Routing Quiz',now(),now());

INSERT INTO questions (id,quiz_id,question_text,created_at,updated_at) VALUES
(1,1,'Which filter validates JWT in Spring Security?',now(),now()),
(2,1,'Where should authorization checks be added?',now(),now()),
(3,2,'What component enables nested routing in React Router?',now(),now()),
(4,2,'Which axios feature can append auth tokens?',now(),now());

INSERT INTO answers (question_id,answer_text,is_correct,created_at,updated_at) VALUES
(1,'OncePerRequestFilter',true,now(),now()),(1,'JdbcTemplate',false,now(),now()),(1,'ObjectMapper',false,now(),now()),
(2,'Service layer and method security',true,now(),now()),(2,'Only in UI',false,now(),now()),(2,'Only in DB trigger',false,now(),now()),
(3,'Outlet',true,now(),now()),(3,'Provider',false,now(),now()),(3,'Reducer',false,now(),now()),
(4,'Request interceptor',true,now(),now()),(4,'LocalStorage event',false,now(),now()),(4,'CSS module',false,now(),now());

INSERT INTO enrollments (student_id,course_id,enrolled_at,status,progress_percentage,created_at,updated_at) VALUES
(5,1,now(),'ACTIVE',33.33,now(),now()),
(5,2,now(),'ACTIVE',50.0,now(),now()),
(6,1,now(),'ACTIVE',100.0,now(),now()),
(7,4,now(),'ACTIVE',25.0,now(),now()),
(8,2,now(),'CANCELLED',10.0,now(),now()),
(9,6,now(),'ACTIVE',0.0,now(),now());

INSERT INTO progress (student_id,lesson_id,completed,completed_at,created_at,updated_at) VALUES
(5,1,true,now(),now(),now()),
(5,2,true,now(),now(),now()),
(5,3,false,null,now(),now()),
(5,7,true,now(),now(),now()),
(5,8,true,now(),now(),now()),
(6,1,true,now(),now(),now()),
(6,2,true,now(),now(),now()),
(6,3,true,now(),now(),now()),
(6,4,true,now(),now(),now()),
(6,5,true,now(),now(),now()),
(6,6,true,now(),now(),now()),
(7,13,true,now(),now(),now());

INSERT INTO quiz_results (student_id,quiz_id,score,submitted_at,answers_json,created_at,updated_at) VALUES
(5,1,50.0,now(),'{"1":1,"2":5}',now(),now()),
(6,1,100.0,now(),'{"1":1,"2":4}',now(),now()),
(5,2,100.0,now(),'{"3":7,"4":10}',now(),now());

SELECT setval('users_id_seq', 14, true);
SELECT setval('categories_id_seq', 4, true);
SELECT setval('courses_id_seq', 12, true);
SELECT setval('course_modules_id_seq', 9, true);
SELECT setval('lessons_id_seq', 18, true);
SELECT setval('quizzes_id_seq', 2, true);
SELECT setval('questions_id_seq', 4, true);
