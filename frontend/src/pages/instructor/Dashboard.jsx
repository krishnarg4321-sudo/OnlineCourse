import { useEffect, useState } from "react";
import { instructorDashboard } from "../../api/dashboardApi";
export default function Dashboard() {
  const [d, setD] = useState();
  useEffect(() => { instructorDashboard().then(setD).catch(()=>setD({})); }, []);
  return <div className="p-6 grid gap-4 md:grid-cols-2"><div className="bg-white p-4 rounded shadow">Owned Courses: {d?.ownedCourses ?? 0}</div><div className="bg-white p-4 rounded shadow">Total Enrollments: {d?.totalEnrollments ?? 0}</div></div>;
}
