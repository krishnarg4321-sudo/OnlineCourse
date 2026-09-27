import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { enrollCourse, fetchCourseDetail } from "../../api/courseApi";
import useAuth from "../../hooks/useAuth";

export default function CourseDetails() {
  const { id } = useParams();
  const [course, setCourse] = useState();
  const [msg, setMsg] = useState("");
  const { user } = useAuth();

  useEffect(() => { fetchCourseDetail(id).then(setCourse); }, [id]);
  if (!course) return <div className="p-6">Loading...</div>;

  const enroll = async () => {
    try { await enrollCourse(course.id); setMsg("Enrolled successfully"); }
    catch (e) { setMsg(e.response?.data?.message || "Enrollment failed"); }
  };

  return <div className="p-6 space-y-4">
    <div className="bg-white p-6 rounded shadow"><h1 className="text-2xl font-bold">{course.title}</h1><p>{course.description}</p>
      {user?.role === "STUDENT" && <button onClick={enroll} className="mt-3 bg-green-600 text-white px-3 py-1 rounded">Enroll</button>}
      {msg && <p className="text-sm mt-2">{msg}</p>}
    </div>
    <div className="space-y-2">{course.modules?.map(m=><div key={m.id} className="bg-white p-4 rounded shadow"><h3 className="font-semibold">{m.orderIndex}. {m.title}</h3>
      <ul className="list-disc ml-5">{m.lessons?.map(l=><li key={l.id}>{l.title} {l.locked ? '(Locked)' : ''}</li>)}</ul></div>)}</div>
  </div>;
}
