// src/pages/UserManagement.jsx
import { useEffect, useState } from "react";
import { fetchUsers } from "../../services/user";
import UserForm from "./UserForm";

export default function UserManagement() {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");

  const [showForm, setShowForm] = useState(false);
  const [editingUser, setEditingUser] = useState(null);

  useEffect(() => {
    const loadUsers = async () => {
      try {
        const res = await fetchUsers();
        if (!res.success) {
          setError(res.message);
        } else {
          setUsers(res.data);
        }
      } catch (err) {
        setError(err.message);
      }
    };

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

  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="flex items-center justify-between mb-8">
          <div>
            <h1 className="text-2xl font-semibold text-gray-900">
              User Management
            </h1>
            <p className="text-gray-600 mt-1">
              Manage team members and permissions
            </p>
          </div>
          <button
            className="bg-gray-900 text-white px-4 py-2 rounded-lg font-medium hover:bg-gray-800 transition-colors cursor-pointer"
            onClick={handleAdd}
          >
            Add User
          </button>
        </div>
        {/* Error Message */}
        {error && (
          <div className="mb-6 p-4 bg-red-50 border border-red-200 rounded-lg text-red-700">
            {error}
          </div>
        )}
        {/* Table Container */}
        <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
          <div className="overflow-x-auto">
            <table className="min-w-full">
              <thead className="bg-gray-50 border-b border-gray-200">
                <tr>
                  <th className="px-6 py-3 text-left text-sm font-medium text-gray-500">
                    ID
                  </th>
                  <th className="px-6 py-3 text-left text-sm font-medium text-gray-500">
                    Avatar
                  </th>

                  <th className="px-6 py-3 text-left text-sm font-medium text-gray-500">
                    Name
                  </th>
                  <th className="px-6 py-3 text-left text-sm font-medium text-gray-500">
                    Username
                  </th>
                  <th className="px-6 py-3 text-left text-sm font-medium text-gray-500">
                    Role
                  </th>

                  <th className="px-6 py-3 text-left text-sm font-medium text-gray-500"></th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200">
                {users.map((user) => (
                  <tr key={user.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 text-sm text-gray-500 w-[60px]">
                      {user.id}
                    </td>
                    <td className="px-6 py-4 text-sm text-gray-500 w-[50px]">
                      <div className="h-8 w-8 bg-gray-200 rounded-full flex items-center justify-center text-gray-600 font-medium text-xs m-auto">
                        {user.firstName?.charAt(0)}
                        {user.lastName?.charAt(0)}
                      </div>
                    </td>

                    <td className="px-6 py-4 text-sm font-medium text-gray-900">
                      {user.firstName} {user.lastName}
                    </td>
                    <td className="px-6 py-4 text-sm text-gray-500">
                      {user.userName}
                    </td>
                    <td className="px-6 py-4 w-[90px]">
                      <span className="inline-block px-2 py-1 text-xs font-medium bg-gray-100 text-gray-800 rounded">
                        {user.role}
                      </span>
                    </td>

                    <td className="px-6 py-4 text-right w-[170px]">
                      <div className="flex items-center justify-between ">
                        <button
                          className="px-3 py-1 text-sm text-gray-700 hover:text-gray-900 hover:bg-gray-100 rounded transition-colors cursor-pointer"
                          onClick={() => handleEdit(user)}
                        >
                          Edit
                        </button>
                        <button className="px-3 py-1 text-sm text-red-600 hover:text-red-700 hover:bg-red-50 rounded transition-colors cursor-pointer">
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Empty State */}
          {users.length === 0 && !error && (
            <div className="text-center py-12">
              <div className="text-gray-500 mb-2">No users found</div>
              <button className="text-sm text-gray-900 hover:text-gray-700">
                Add your first user
              </button>
            </div>
          )}
        </div>
        {/* User Form Modal */}
        {showForm && (
          <UserForm
            isOpen={showForm}
            onClose={() => setShowForm(false)}
            onSave={() => {
              setShowForm(false);
              setEditingUser(null);
              // Reload users after save
            }}
            initialData={editingUser}
          />
        )}
      </div>
    </div>
  );
}
