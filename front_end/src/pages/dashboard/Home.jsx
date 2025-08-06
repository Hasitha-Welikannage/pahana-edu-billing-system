import { useEffect } from "react";
import { logout } from "../../services/auth";
import { useNavigate } from "react-router-dom";
import MenuCard from "./MenuCard";

import { FiUsers, FiBox, FiUser, FiFileText, FiClock } from "react-icons/fi";

function Home() {
  const navigate = useNavigate();
  const user = JSON.parse(sessionStorage.getItem("user"));

  const menuItems = [
    {
      title: "User Management",
      description: "Manage system users and their roles",
      icon: (
        <div className="p-2 bg-blue-50 rounded-lg group-hover:bg-blue-100 transition-colors">
          <FiUser className="w-5 h-5 text-blue-600" />
        </div>
      ),
      path: "/users",
      roles: ["ADMIN"],
    },
    {
      title: "Item Management",
      description: "Add or update bookshop items",
      icon: (
        <div className="p-2 bg-green-50 rounded-lg group-hover:bg-green-100 transition-colors">
          <FiBox className="w-5 h-5 text-green-600" />
        </div>
      ),
      path: "/items",
      roles: ["ADMIN"],
    },
    {
      title: "Customer Management",
      description: "Manage customer records and details",
      icon: (
        <div className="p-2 bg-purple-50 rounded-lg group-hover:bg-purple-100 transition-colors">
          <FiUsers className="w-5 h-5 text-purple-600" />
        </div>
      ),
      path: "/customers",
      roles: ["USER", "ADMIN"],
    },
    {
      title: "Bill Creation",
      description: "Create and print a new bill",
      icon: (
        <div className="p-2 bg-orange-50 rounded-lg group-hover:bg-orange-100 transition-colors">
          <FiFileText className="w-5 h-5 text-orange-600" />
        </div>
      ),
      path: "/create-bill",
      roles: ["USER", "ADMIN"],
    },
    {
      title: "Bill History",
      description: "View all created bills and details",
      icon: (
        <div className="p-2 bg-gray-50 rounded-lg group-hover:bg-gray-100 transition-colors">
          <FiClock className="w-5 h-5 text-gray-600" />
        </div>
      ),
      path: "/bills/history",
      roles: ["USER", "ADMIN"],
    },
  ];

  useEffect(() => {
    if (!user) {
      navigate("/");
    }
  }, [user, navigate]);

  const handleLogout = async () => {
    try {
      const data = await logout();
      if (data.success) {
        sessionStorage.removeItem("user");
        navigate("/");
      } else {
        console.error("Logout failed:", data.message);
      }
    } catch (error) {
      console.error("Logout failed:", error);
    }
  };

  if (!user) return null;

  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="bg-white rounded-lg border border-gray-200 p-6 mb-8">
          <div className="flex flex-col md:flex-row gap-6 md:gap-0  md:items-center md:justify-between">
            <div>
              <h1 className="text-2xl font-semibold text-gray-900">
                Welcome, {user.firstName} {user.lastName}
              </h1>
              <p className="text-gray-600 mt-1">
                Role:{" "}
                <span className="font-medium text-gray-900">{user.role}</span>
              </p>
            </div>
            <button
              className="w-full md:w-auto px-4 py-2 bg-gray-900 text-white rounded-lg font-medium hover:bg-gray-800 transition-colors"
              onClick={handleLogout}
            >
              Logout
            </button>
          </div>
        </div>

        {/* Dashboard Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {menuItems
            .filter((item) => item.roles.includes(user.role))
            .map((item, index) => (
              <MenuCard key={index} data={item} />
            ))}
        </div>
      </div>
    </div>
  );
};

export default Home;
