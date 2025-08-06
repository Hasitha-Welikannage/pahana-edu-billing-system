import { useState, useEffect } from "react";
import { FiEye, FiEyeOff } from "react-icons/fi";
import { addUser, updateUser } from "../../services/user";
import ErrorMessage from "../../components/ErrorMessage";

const UserFormModal = ({ isOpen, onClose, onSave, initialData }) => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    userName: "",
    password: "",
    role: "USER",
  });

  const [error, setError] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false); // Added for submission loading

  useEffect(() => {
    if (initialData) {
      setFormData({
        firstName: initialData.firstName || "",
        lastName: initialData.lastName || "",
        userName: initialData.userName || "",
        password: "", // Don't pre-fill password for security
        role: initialData.role || "USER",
      });
    } else {
      setFormData({
        firstName: "",
        lastName: "",
        userName: "",
        password: "",
        role: "USER",
      });
    }
    setError(""); // Clear error when modal opens or initialData changes
  }, [initialData, isOpen]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((fd) => ({ ...fd, [name]: value }));
  };

  const saveUser = async (data) => {
    // Renamed formData to data for clarity
    try {
      setIsSubmitting(true);
      const res = await addUser(data);
      if (!res.success) {
        setError(res.message);
      } else {
        onSave(); // Call parent's onSave to close modal and refresh data
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  const editUser = async (id, data) => {
    // Renamed formData to data for clarity
    const dataToSend = { ...data };
    // Only send password if it's not empty, otherwise backend keeps current
    if (dataToSend.password === "") {
      delete dataToSend.password;
    }

    try {
      setIsSubmitting(true);
      const res = await updateUser(id, dataToSend);
      if (!res.success) {
        setError(res.message);
      } else {
        onSave(); // Call parent's onSave to close modal and refresh data
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError(""); // Clear previous errors

    // Simple validation
    if (
      !formData.firstName ||
      !formData.lastName ||
      !formData.userName ||
      (!initialData && !formData.password) // Password required only for new users
    ) {
      setError("All fields are required.");
      return; // Stop submission if validation fails
    }

    if (initialData) {
      // Edit mode
      editUser(initialData.id, formData);
    } else {
      // Add mode
      saveUser(formData);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black/60 flex justify-center items-center z-50 p-4">
      <div className="bg-white rounded-lg w-full max-w-md shadow-xl border border-gray-200">
        {/* Header */}
        <div className="px-6 py-4 border-b border-gray-200">
          <div className="flex items-center justify-between">
            <h2 className="text-xl font-semibold text-gray-900">
              {initialData ? "Edit User" : "Add New User"}
            </h2>
          </div>
        </div>

        {/* Content */}
        <div className="px-6 py-4">
          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label
                  htmlFor="firstName"
                  className="block text-sm font-medium text-gray-700 mb-1"
                >
                  First Name
                </label>
                <input
                  type="text"
                  id="firstName"
                  name="firstName"
                  value={formData.firstName}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent"
                  placeholder="Enter first name"
                  required
                />
              </div>

              <div>
                <label
                  htmlFor="lastName"
                  className="block text-sm font-medium text-gray-700 mb-1"
                >
                  Last Name
                </label>
                <input
                  type="text"
                  id="lastName"
                  name="lastName"
                  value={formData.lastName}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent"
                  placeholder="Enter last name"
                  required
                />
              </div>
            </div>

            <div>
              <label
                htmlFor="userName"
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                Username
              </label>
              <input
                type="text"
                id="userName"
                name="userName"
                value={formData.userName}
                onChange={handleChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent"
                placeholder="Enter username"
                required
              />
            </div>

            <div>
              <label
                htmlFor="password"
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                Password{" "}
                {initialData && (
                  <span className="text-gray-500 text-xs">
                    (leave blank to keep current)
                  </span>
                )}
              </label>
              <div className="relative">
                <input
                  type={showPassword ? "text" : "password"}
                  id="password"
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                  className="w-full px-3 py-2 pr-10 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent"
                  placeholder={
                    initialData ? "Enter new password" : "Enter password"
                  }
                  required={!initialData} // Password is required only for new users
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-400 hover:text-gray-600"
                >
                  {showPassword ? (
                    <FiEyeOff className="w-5 h-5" />
                  ) : (
                    <FiEye className="w-5 h-5" />
                  )}
                </button>
              </div>
            </div>

            <div>
              <label
                htmlFor="role"
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                Role
              </label>
              <select
                id="role"
                name="role"
                value={formData.role}
                onChange={handleChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent bg-white cursor-pointer"
              >
                <option value="ADMIN">Administrator</option>
                <option value="USER">User</option>
              </select>
            </div>

            {/* Preview Section - Added */}
            {(formData.firstName || formData.lastName || formData.userName) && (
              <div className="mt-4 p-3 bg-gray-50 rounded-lg border">
                <h4 className="text-sm font-medium text-gray-700 mb-2">
                  Preview
                </h4>
                <div className="text-sm text-gray-600 space-y-1">
                  <div>
                    <span className="font-medium">Name:</span>{" "}
                    {formData.firstName || "First"}{" "}
                    {formData.lastName || "Last"}
                  </div>
                  <div>
                    <span className="font-medium">Username:</span>{" "}
                    {formData.userName || "username"}
                  </div>
                  <div>
                    <span className="font-medium">Role:</span>{" "}
                    {formData.role || "User"}
                  </div>
                </div>
              </div>
            )}
          </form>
          {error && (
            <div className="mt-4">
              <ErrorMessage error={error} />
            </div>
          )}
        </div>

        {/* Footer */}
        <div className="px-6 py-4 border-t border-gray-200 bg-gray-50 rounded-b-lg">
          <div className="flex items-center justify-end space-x-3">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-gray-900 focus:ring-offset-2 transition-colors cursor-pointer"
              disabled={isSubmitting} // Disable cancel button during submission
            >
              Cancel
            </button>
            <button
              type="submit"
              onClick={handleSubmit}
              className="px-4 py-2 bg-gray-900 text-white rounded-lg hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-gray-900 focus:ring-offset-2 transition-colors cursor-pointer flex items-center space-x-2"
              disabled={isSubmitting} // Disable submit button during submission
            >
              {isSubmitting ? (
                <>
                  <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-white"></div>
                  <span>Saving...</span>
                </>
              ) : (
                <>
                  <span>{initialData ? "Update User" : "Add User"}</span>
                </>
              )}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserFormModal;
