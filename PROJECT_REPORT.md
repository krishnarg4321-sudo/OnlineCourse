# PROJECT_REPORT

## FR-01 Authentication & User Management
Implemented `/api/auth/register`, `/api/auth/login`, `/api/users/{id}`, `/api/users` with BCrypt + JWT + role checks.

## FR-02 Public Catalogue
Implemented `/api/courses` (search/filter/pagination), `/api/courses/{id}`, `/api/categories`.

## FR-03 Course Management
Implemented instructor/admin course CRUD + status workflow + module/lesson/material CRUD.

## FR-04 Enrollment
Implemented enrollment create, student list, cancel, duplicate active enrollment conflict (409).

## FR-05 Learning Access Control
Enrolled/owner/admin checks enforced before full module/lesson content access.

## FR-06 Progress Tracking
Implemented lesson completion tracking + enrollment progress percentage recalculation.

## FR-07 Quiz & Assessment
Implemented quiz/question/answer authoring, student quiz view without correctness exposure, server-side scoring, result storage/retrieval.

## FR-08 Dashboards
Implemented role dashboards for student, instructor, admin summary statistics.

## FR-09 Administration
Implemented admin reports, user listing, category management, and course status approvals.
