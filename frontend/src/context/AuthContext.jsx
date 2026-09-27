import { createContext, useEffect, useMemo, useState } from "react";
import { decodeToken } from "../utils/jwt";
import { setUnauthorizedHandler } from "../api/axios";

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(localStorage.getItem("token"));
  const [user, setUser] = useState(() => token ? decodeToken(token) : null);

  const logout = () => {
    localStorage.removeItem("token");
    setToken(null);
    setUser(null);
  };

  useEffect(() => { setUnauthorizedHandler(logout); }, []);

  const saveAuth = (authRes) => {
    localStorage.setItem("token", authRes.token);
    setToken(authRes.token);
    setUser({ role: authRes.role, userId: authRes.userId, email: authRes.email, name: authRes.name });
  };

  const value = useMemo(() => ({ token, user, isAuthenticated: !!token, saveAuth, logout }), [token, user]);
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
