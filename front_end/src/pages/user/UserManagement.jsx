// src/pages/UserManagement.jsx
import { useEffect, useState } from "react";
import { fetchUsers } from "../../services/api";

export default function UserManagement() {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadUsers = async () => {
      try {
        const res = await fetchUsers();
        setUsers(res.data);
      } catch (err) {
        setError("Error loading users.");
      }
    };

    loadUsers();
  }, []);

  return (
    <div className="p-6">
      <h2 className="text-xl font-bold mb-4">User Management</h2>
      {error && <div className="text-red-500">{error}</div>}

      <div className="mb-4">
        <button className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
          ➕ Add User
        </button>
      </div>

      <table className="min-w-full border">
        <thead className="bg-gray-100">
          <tr>
            <th className="border px-4 py-2">ID</th>
            <th className="border px-4 py-2">Username</th>
            <th className="border px-4 py-2">First Name</th>
            <th className="border px-4 py-2">Last Name</th>
            <th className="border px-4 py-2">Role</th>
            <th className="border px-4 py-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map(user => (
            <tr key={user.id} className="text-center">
              <td className="border px-4 py-2">{user.id}</td>
              <td className="border px-4 py-2">{user.userName}</td>
              <td className="border px-4 py-2">{user.firstName}</td>
              <td className="border px-4 py-2">{user.lastName}</td>
              <td className="border px-4 py-2">{user.role}</td>
              <td className="border px-4 py-2 space-x-2">
                <button className="bg-yellow-400 px-3 py-1 rounded hover:bg-yellow-500">
                  ✏️ Edit
                </button>
                <button className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600">
                  🗑️ Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
