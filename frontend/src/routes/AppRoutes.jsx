import { Navigate, Route, Routes } from "react-router-dom";
import ProtectedRoute from "../components/ProtectedRoute";
import Home from "../pages/public/Home";
import About from "../pages/public/About";
import Contact from "../pages/public/Contact";
import Login from "../pages/public/Login";
import Register from "../pages/public/Register";
import CourseCatalogue from "../pages/public/CourseCatalogue";
import CourseDetails from "../pages/public/CourseDetails";
import StudentDashboard from "../pages/student/Dashboard";
import MyCourses from "../pages/student/MyCourses";
import LearningPage from "../pages/student/LearningPage";
import ProgressPage from "../pages/student/ProgressPage";
import QuizPage from "../pages/student/QuizPage";
import ResultsPage from "../pages/student/ResultsPage";
import ProfilePage from "../pages/student/ProfilePage";
import SettingsPage from "../pages/student/SettingsPage";
import InstructorDashboard from "../pages/instructor/Dashboard";
import InstructorCourses from "../pages/instructor/MyCoursesPage";
import CreateEditCoursePage from "../pages/instructor/CreateEditCoursePage";
import ModulesLessonsPage from "../pages/instructor/ModulesLessonsPage";
import StudentsPage from "../pages/instructor/StudentsPage";
import AnalyticsPage from "../pages/instructor/AnalyticsPage";
import AdminDashboard from "../pages/admin/Dashboard";
import UsersPage from "../pages/admin/UsersPage";
import InstructorsPage from "../pages/admin/InstructorsPage";
import StudentsAdminPage from "../pages/admin/StudentsPage";
import CoursesPage from "../pages/admin/CoursesPage";
import CategoriesPage from "../pages/admin/CategoriesPage";
import EnrollmentsPage from "../pages/admin/EnrollmentsPage";
import ReportsPage from "../pages/admin/ReportsPage";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/catalogue" element={<CourseCatalogue />} />
      <Route path="/courses/:id" element={<CourseDetails />} />
      <Route path="/about" element={<About />} />
      <Route path="/contact" element={<Contact />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

      <Route element={<ProtectedRoute roles={["STUDENT"]} />}>
        <Route path="/student/dashboard" element={<StudentDashboard />} />
        <Route path="/student/my-courses" element={<MyCourses />} />
        <Route path="/student/learning" element={<LearningPage />} />
        <Route path="/student/progress" element={<ProgressPage />} />
        <Route path="/student/quiz" element={<QuizPage />} />
        <Route path="/student/results" element={<ResultsPage />} />
        <Route path="/student/profile" element={<ProfilePage />} />
        <Route path="/student/settings" element={<SettingsPage />} />
      </Route>

      <Route element={<ProtectedRoute roles={["INSTRUCTOR"]} />}>
        <Route path="/instructor/dashboard" element={<InstructorDashboard />} />
        <Route path="/instructor/courses" element={<InstructorCourses />} />
        <Route path="/instructor/course-editor" element={<CreateEditCoursePage />} />
        <Route path="/instructor/modules" element={<ModulesLessonsPage />} />
        <Route path="/instructor/students" element={<StudentsPage />} />
        <Route path="/instructor/analytics" element={<AnalyticsPage />} />
      </Route>

      <Route element={<ProtectedRoute roles={["ADMIN"]} />}>
        <Route path="/admin/dashboard" element={<AdminDashboard />} />
        <Route path="/admin/users" element={<UsersPage />} />
        <Route path="/admin/instructors" element={<InstructorsPage />} />
        <Route path="/admin/students" element={<StudentsAdminPage />} />
        <Route path="/admin/courses" element={<CoursesPage />} />
        <Route path="/admin/categories" element={<CategoriesPage />} />
        <Route path="/admin/enrollments" element={<EnrollmentsPage />} />
        <Route path="/admin/reports" element={<ReportsPage />} />
      </Route>

      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
