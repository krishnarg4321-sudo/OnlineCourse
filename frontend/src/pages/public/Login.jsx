import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../api/authApi";
import useAuth from "../../hooks/useAuth";

export default function Login() {
  const [form, setForm] = useState({ email: "", password: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const { saveAuth } = useAuth();

  const submit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const data = await login(form);
      saveAuth(data);
      if (data.role === "STUDENT") navigate("/student/dashboard");
      else if (data.role === "INSTRUCTOR") navigate("/instructor/dashboard");
      else navigate("/admin/dashboard");
    } catch (err) {
      setError(err.response?.data?.message || "Login failed");
    }
  };

  return <form onSubmit={submit} className="max-w-md mx-auto bg-white p-6 rounded shadow mt-6 space-y-3">
    <h1 className="text-xl font-semibold">Login</h1>
    <input className="w-full border p-2" placeholder="Email" value={form.email} onChange={e=>setForm({...form,email:e.target.value})} />
    <input type="password" className="w-full border p-2" placeholder="Password" value={form.password} onChange={e=>setForm({...form,password:e.target.value})} />
    {error && <p className="text-red-600 text-sm">{error}</p>}
    <button className="bg-blue-600 text-white px-4 py-2 rounded">Login</button>
  </form>
}
