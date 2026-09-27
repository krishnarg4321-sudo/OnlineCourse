import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { fetchCourses } from "../../api/courseApi";
import Loader from "../../components/Loader";

export default function CourseCatalogue() {
  const [data, setData] = useState();
  const [query, setQuery] = useState("");
  const [loading, setLoading] = useState(true);

  const load = async () => {
    setLoading(true);
    try { setData(await fetchCourses({ search: query })); }
    finally { setLoading(false); }
  };

  useEffect(() => { load(); }, []);

  if (loading) return <Loader />;
  const courses = data?.content || [];
  return <div className="p-6 space-y-4">
    <div className="flex gap-2"><input value={query} onChange={e=>setQuery(e.target.value)} className="border p-2 flex-1" placeholder="Search courses" /><button onClick={load} className="bg-blue-600 text-white px-3 rounded">Search</button></div>
    {courses.length === 0 ? <div className="p-4 bg-white rounded">No courses found.</div> : (
      <div className="grid md:grid-cols-3 gap-4">{courses.map(c => <Link key={c.id} to={`/courses/${c.id}`} className="bg-white rounded shadow p-4">
        <h3 className="font-semibold">{c.title}</h3><p className="text-sm text-slate-600">{c.description}</p><p className="text-xs mt-2">{c.level} • {c.duration}h</p>
      </Link>)}</div>
    )}
  </div>;
}
