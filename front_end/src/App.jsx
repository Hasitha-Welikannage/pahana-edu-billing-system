import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/login/Login";
import Home from "./pages/dashboard/Home";
import UserManagement from "./pages/user/UserManagement";
import ItemManagement from "./pages/item/ItemManagement";
import CustomerManagement from "./pages/customer/CustomerManagement";
import BillCreate from "./pages/bill/BillCreate";
import BillHistory from "./pages/bill/BillHistory";
import BillDetailsPage from "./pages/bill/BillDetailsPage";
import Help from "./pages/help/Help";
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
          
          {/* Standardized bill routes */}
          <Route path="/bills" element={<BillHistory />} />
          <Route path="/bills/new" element={<BillCreate />} />
          <Route path="/bills/:id" element={<BillDetailsPage />} />
          <Route path="/help" element={<Help />} />
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
