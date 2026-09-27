import api from "./axios";
export const fetchCourses = (params) => api.get("/courses", { params }).then((r) => r.data);
export const fetchCourseDetail = (id) => api.get(`/courses/${id}`).then((r) => r.data);
export const enrollCourse = (courseId) => api.post('/enrollments', { courseId }).then(r=>r.data);
