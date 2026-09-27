import { useEffect, useState } from "react";
import { adminDashboard } from "../../api/dashboardApi";
export default function Dashboard() {
  const [d, setD] = useState();
  useEffect(() => { adminDashboard().then(setD).catch(()=>setD({})); }, []);
  return <div className="p-6 grid gap-4 md:grid-cols-3"><div className="bg-white p-4 rounded shadow">Students: {d?.students ?? 0}</div><div className="bg-white p-4 rounded shadow">Instructors: {d?.instructors ?? 0}</div><div className="bg-white p-4 rounded shadow">Enrollments: {d?.totalEnrollments ?? 0}</div></div>;
}
