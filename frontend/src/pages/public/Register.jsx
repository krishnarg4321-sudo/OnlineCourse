import { useState } from "react";
import { register } from "../../api/authApi";
import useAuth from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";

export default function Register() {
  const [form, setForm] = useState({ name: "", email: "", password: "", role: "STUDENT" });
  const [error, setError] = useState("");
  const { saveAuth } = useAuth();
  const navigate = useNavigate();

  const submit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const data = await register(form);
      saveAuth(data);
      navigate(data.role === "INSTRUCTOR" ? "/instructor/dashboard" : "/student/dashboard");
    } catch (err) {
      setError(err.response?.data?.message || "Registration failed");
    }
  };

  return <form onSubmit={submit} className="max-w-md mx-auto bg-white p-6 rounded shadow mt-6 space-y-3">
    <h1 className="text-xl font-semibold">Register</h1>
    <input className="w-full border p-2" placeholder="Name" value={form.name} onChange={e=>setForm({...form,name:e.target.value})} />
    <input className="w-full border p-2" placeholder="Email" value={form.email} onChange={e=>setForm({...form,email:e.target.value})} />
    <input type="password" className="w-full border p-2" placeholder="Password" value={form.password} onChange={e=>setForm({...form,password:e.target.value})} />
    <select className="w-full border p-2" value={form.role} onChange={e=>setForm({...form,role:e.target.value})}><option>STUDENT</option><option>INSTRUCTOR</option></select>
    {error && <p className="text-red-600 text-sm">{error}</p>}
    <button className="bg-blue-600 text-white px-4 py-2 rounded">Create Account</button>
  </form>
}
