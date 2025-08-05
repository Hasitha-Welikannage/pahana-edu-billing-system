import { useEffect, useState } from "react";
import { FiUsers, FiUserCheck, FiActivity } from "react-icons/fi";
import { fetchUsers, deleteUser } from "../../services/user";

import UserForm from "./UserForm";
import DeleteConfirm from "../../components/DeleteConfirm";
import StatsCard from "../../components/StatsCard";
import ErrorMessage from "../../components/ErrorMessage";
import Header from "../../components/Header";

function UserManagement() {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  const [showForm, setShowForm] = useState(false);
  const [editingUser, setEditingUser] = useState(null);

  const [showDelete, setShowDelete] = useState(false);
  const [deletingUser, setDeletingUser] = useState(null);

  const loadUsers = async () => {
    try {
      setLoading(true);
      setError("");
      const res = await fetchUsers();
      if (!res.success) {
        setError(res.message);
      } else {
        setUsers(res.data);
      }
    } catch (err) {
      setError("Error loading users");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadUsers();
  }, []);

  // Add button clicked
  const handleAdd = () => {
    setEditingUser(null);
    setShowForm(true);
  };

  // Edit button clicked
  const handleEdit = (user) => {
    setEditingUser(user);
    setShowForm(true);
  };

  // Delete button clicked
  const handleDelete = (user) => {
    setDeletingUser(user);
    setShowDelete(true);
  };

  // Confirm deletion
  const handleConfirmDelete = async () => {
    try {
      const res = await deleteUser(deletingUser.id);
      if (!res.success) {
        setError(res.message);
      } else {
        setShowDelete(false);
        loadUsers();
      }
    } catch (err) {
      setError("Failed to delete user");
    }
  };

  // Helper functions for stats
  const getUsersByRole = () => {
    const roles = {};
    users.forEach((user) => {
      roles[user.role] = (roles[user.role] || 0) + 1;
    });
    return Object.keys(roles).length;
  };

  const getActiveUsers = () => {
    // Assuming users with recent activity (you can modify this logic)
    return users.filter((user) => user.id > users.length - 3).length;
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}

        <Header
          action={handleAdd}
          content={{
            title: "User Management",
            description: "Manage team members and permissions",
            buttonText: "Add User",
          }}
        />

        {/* Stats Cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <StatsCard
            data={{
              icon: (
                <div className="p-2 bg-blue-50 rounded-lg">
                  <FiUsers className="w-5 h-5 text-blue-600" />
                </div>
              ),
              title: "Total Users",
              value: users.length,
            }}
          />
          <StatsCard
            data={{
              icon: (
                <div className="p-2 bg-green-50 rounded-lg">
                  <FiUserCheck className="w-5 h-5 text-green-600" />
                </div>
              ),
              title: "User Roles",
              value: getUsersByRole(),
            }}
          />

          <StatsCard
            data={{
              icon: (
                <div className="p-2 bg-purple-50 rounded-lg">
                  <FiActivity className="w-5 h-5 text-purple-600" />
                </div>
              ),
              title: "Active Users",
              value: getActiveUsers(),
            }}
          />
        </div>

        {/* Error Message */}
        {error && (
          <div className="mb-4">
            <ErrorMessage error={error} />
          </div>
        )}

        {/* Users Table */}
        <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
          <div className="px-6 py-4 border-b border-gray-200">
            <div className="flex items-center justify-between">
              <h3 className="text-lg font-semibold text-gray-900">
                Team Directory
              </h3>
              <span className="bg-blue-100 text-blue-800 text-sm font-medium px-3 py-1 rounded-full">
                {users.length} {users.length === 1 ? "User" : "Users"}
              </span>
            </div>
          </div>

          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    User
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Username
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Role
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    ID
                  </th>
                  <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {loading ? (
                  <tr>
                    <td colSpan="5" className="px-6 py-12 text-center">
                      <div className="flex items-center justify-center">
                        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900"></div>
                        <span className="ml-2 text-gray-600">
                          Loading users...
                        </span>
                      </div>
                    </td>
                  </tr>
                ) : users.length === 0 ? (
                  <tr>
                    <td colSpan="5" className="px-6 py-12 text-center">
                      <div className="text-gray-500">
                        <FiUsers className="mx-auto h-12 w-12 text-gray-400 mb-4" />

                        <p className="text-lg font-medium text-gray-900 mb-2">
                          No users found
                        </p>
                        <p className="text-gray-500">
                          Get started by adding your first team member.
                        </p>
                      </div>
                    </td>
                  </tr>
                ) : (
                  users.map((user) => (
                    <tr
                      key={user.id}
                      className="hover:bg-gray-50 transition-colors"
                    >
                      <td className="px-6 py-4 whitespace-nowrap">
                        <div className="flex items-center">
                          <div className="flex-shrink-0 h-10 w-10">
                            <div className="h-10 w-10 rounded-full bg-gray-200 flex items-center justify-center text-gray-600 font-medium text-sm">
                              {user.firstName?.charAt(0).toUpperCase()}
                              {user.lastName?.charAt(0).toUpperCase()}
                            </div>
                          </div>
                          <div className="ml-4">
                            <div className="text-sm font-medium text-gray-900">
                              {user.firstName} {user.lastName}
                            </div>
                          </div>
                        </div>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <div className="text-sm text-gray-900">
                          {user.userName}
                        </div>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className="inline-flex px-2 py-1 text-xs font-medium bg-gray-100 text-gray-800 rounded-full">
                          {user.role}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        #{user.id}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <div className="flex items-center justify-end space-x-2">
                          <button
                            onClick={() => handleEdit(user)}
                            className="px-3 py-1 text-sm text-gray-700 hover:text-gray-900 hover:bg-gray-100 rounded transition-colors cursor-pointer"
                          >
                            Edit
                          </button>
                          <button
                            onClick={() => handleDelete(user)}
                            className="px-3 py-1 text-sm text-red-600 hover:text-red-700 hover:bg-red-50 rounded transition-colors cursor-pointer"
                          >
                            Delete
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>

        {/* User Form Modal */}
        {showForm && (
          <UserForm
            isOpen={showForm}
            onClose={() => {
              setShowForm(false);
              setEditingUser(null);
            }}
            onSave={() => {
              setShowForm(false);
              setEditingUser(null);
              loadUsers();
            }}
            initialData={editingUser}
          />
        )}

        {/* Delete Confirmation Modal */}
        {showDelete && (
          <DeleteConfirm
            isOpen={showDelete}
            onClose={() => setShowDelete(false)}
            onConfirm={handleConfirmDelete}
            itemType="User"
            itemName={`${deletingUser?.firstName} ${deletingUser?.lastName}`}
          />
        )}
      </div>
    </div>
  );
}

export default UserManagement;
