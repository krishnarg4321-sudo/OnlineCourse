import { useEffect, useState } from "react";
import { studentDashboard } from "../../api/dashboardApi";

export default function Dashboard() {
  const [d, setD] = useState();
  useEffect(() => { studentDashboard().then(setD).catch(()=>setD({})); }, []);
  return <div className="p-6 grid gap-4 md:grid-cols-3">
    <div className="bg-white p-4 rounded shadow">Enrolled: {d?.enrolledCourses ?? 0}</div>
    <div className="bg-white p-4 rounded shadow">Avg Progress: {Math.round(d?.avgProgress ?? 0)}%</div>
    <div className="bg-white p-4 rounded shadow">Completed: {d?.completedCourses ?? 0}</div>
  </div>;
}
