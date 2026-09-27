import { BrowserRouter } from "react-router-dom";
import Navbar from "./components/Navbar";
import AppRoutes from "./routes/AppRoutes";
import { AuthProvider } from "./context/AuthContext";

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navbar />
        <main className="max-w-7xl mx-auto py-4 px-2"><AppRoutes /></main>
      </BrowserRouter>
    </AuthProvider>
  );
}
