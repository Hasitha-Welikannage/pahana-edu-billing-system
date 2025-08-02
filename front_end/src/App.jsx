import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/login/Login";
import Home from "./pages/dashboard/Home";
import UserManagement from "./pages/user/UserManagement";
import ItemManagement from "./pages/item/ItemManagement";
import CustomerManagement from "./pages/customer/CustomerManagement";
import BillCreate from "./pages/bill/BillCreate";
import BillHistory from "./pages/bill/BillHistory";
import BillDetailsPage from "./pages/bill/BillDetailsPage";
import ProtectedRoute from "./components/ProtectedRoute";


function App() {
  return (
    <main className="main-container">
      <Routes>
        <Route path="/" element={<Login />} />

        {/* All protected routes go here */}
        <Route element={<ProtectedRoute />}>
          <Route path="/home" element={<Home />} />
          <Route path="/customers" element={<CustomerManagement />} />
          <Route path="/create-bill" element={<BillCreate />} />
          <Route path="/bill-history" element={<BillHistory />} />
          <Route path="/bills/:id" element={<BillDetailsPage />} />
        </Route>

        {/* Admin-only protected routes */}
        <Route element={<ProtectedRoute allowedRoles={["ADMIN"]} />}>
          <Route path="/users" element={<UserManagement />} />
          <Route path="/items" element={<ItemManagement />} />
        </Route>
      </Routes>
    </main>
  );
}

export default App;
