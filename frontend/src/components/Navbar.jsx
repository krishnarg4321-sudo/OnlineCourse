import { Link } from "react-router-dom";
import useAuth from "../hooks/useAuth";

export default function Navbar() {
  const { isAuthenticated, user, logout } = useAuth();
  return (
    <nav className="bg-white border-b border-slate-200 p-4 flex gap-4 items-center justify-between">
      <div className="flex gap-3">
        <Link to="/" className="font-semibold">OnlineCourse</Link>
        <Link to="/catalogue">Catalogue</Link>
        <Link to="/about">About</Link>
        <Link to="/contact">Contact</Link>
      </div>
      <div className="flex gap-2 items-center">
        {isAuthenticated ? (
          <>
            <span className="text-sm">{user?.name} ({user?.role})</span>
            <button onClick={logout} className="bg-slate-800 text-white px-3 py-1 rounded">Logout</button>
          </>
        ) : (
          <>
            <Link to="/login">Login</Link>
            <Link to="/register">Register</Link>
          </>
        )}
      </div>
    </nav>
  );
}
