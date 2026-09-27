import api from "./axios";
export const studentDashboard = () => api.get('/dashboard/student').then(r=>r.data);
export const instructorDashboard = () => api.get('/dashboard/instructor').then(r=>r.data);
export const adminDashboard = () => api.get('/dashboard/admin').then(r=>r.data);
