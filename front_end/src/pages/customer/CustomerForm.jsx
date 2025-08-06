import { useState, useEffect } from "react";
import ErrorMessage from "../../components/ErrorMessage";
import { addCustomer, updateCustomer } from "../../services/customer";

// Helper function to format the phone number
const formatPhoneNumber = (number) => {
  if (!number || number.length < 10) return number;
  // Assumes the number is already prefixed with +94
  const part1 = number.substring(0, 3);
  const part2 = number.substring(3, 5);
  const part3 = number.substring(5, 8);
  const part4 = number.substring(8, 12);
  return `${part1} ${part2} ${part3} ${part4}`;
};

const CustomerForm = ({ isOpen, onClose, onSaveSuccess, initialData }) => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    address: "",
    phoneNumber: "",
  });

  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    if (initialData) {
      setFormData({
        ...initialData,
        phoneNumber: initialData.phoneNumber.startsWith("+94")
          ? initialData.phoneNumber
          : `+94${initialData.phoneNumber}`,
      });
    } else {
      setFormData({
        firstName: "",
        lastName: "",
        address: "",
        phoneNumber: "",
      });
    }
    setError("");
  }, [initialData, isOpen]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handlePhoneNumberBlur = () => {
    let number = formData.phoneNumber;
    const phoneRegex = /^\d{9}$/;
    if (phoneRegex.test(number)) {
      setFormData((prev) => ({ ...prev, phoneNumber: `+94${number}` }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (
      !formData.firstName ||
      !formData.lastName ||
      !formData.address ||
      !formData.phoneNumber
    ) {
      setError("All fields are required.");
      return;
    }

    const fullPhoneRegex = /^\+94\d{9}$/;
    if (!fullPhoneRegex.test(formData.phoneNumber)) {
      setError("Please enter a valid phone number, e.g., +94712345678.");
      return;
    }

    setIsSubmitting(true);
    try {
      const data = initialData
        ? await updateCustomer(initialData.id, formData)
        : await addCustomer(formData);

      if (data.success) {
        onSaveSuccess();
      } else {
        setError(data.message || "An error occurred while saving.");
      }
    } catch (err) {
      setError("Failed to save customer. Please try again.");
      console.error("CustomerForm submission error:", err);
    } finally {
      setIsSubmitting(false);
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
              {initialData ? "Edit Customer" : "Add New Customer"}
            </h2>
          </div>
        </div>

        {/* Content */}
        <div className="px-6 py-4">
          {error && (
            <div className="mb-4">
              <ErrorMessage error={error} />
            </div>
          )}

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
                  placeholder="Enter first name"
                  value={formData.firstName}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent"
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
                  placeholder="Enter last name"
                  value={formData.lastName}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent"
                  required
                />
              </div>
            </div>

            <div>
              <label
                htmlFor="address"
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                Address
              </label>
              <textarea
                id="address"
                name="address"
                placeholder="Enter full address"
                value={formData.address}
                onChange={handleChange}
                rows={3}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent resize-none"
                required
              />
            </div>

            <div>
              <label
                htmlFor="phoneNumber"
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                Phone Number
              </label>
              <input
                type="tel"
                id="phoneNumber"
                name="phoneNumber"
                placeholder="712345678"
                value={formData.phoneNumber}
                onChange={handleChange}
                onBlur={handlePhoneNumberBlur}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent"
                required
              />
            </div>

            {/* Preview Section */}
            {(formData.firstName ||
              formData.lastName ||
              formData.address ||
              formData.phoneNumber) && (
              <div className="mt-4 p-3 bg-gray-50 rounded-lg border">
                <h4 className="text-sm font-medium text-gray-700 mb-2">
                  Customer Preview
                </h4>
                <div className="text-sm text-gray-600 space-y-1">
                  <div>
                    <span className="font-medium">Name:</span>{" "}
                    {formData.firstName || "First"}{" "}
                    {formData.lastName || "Last"}
                  </div>
                  {formData.address && (
                    <div>
                      <span className="font-medium">Address:</span>{" "}
                      {formData.address}
                    </div>
                  )}
                  {formData.phoneNumber && (
                    <div>
                      <span className="font-medium">Phone:</span>{" "}
                      {formatPhoneNumber(formData.phoneNumber)}
                    </div>
                  )}
                </div>
              </div>
            )}
          </form>
        </div>

        {/* Footer */}
        <div className="px-6 py-4 border-t border-gray-200 bg-gray-50 rounded-b-lg">
          <div className="flex items-center justify-end space-x-3">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-gray-900 focus:ring-offset-2 transition-colors"
              disabled={isSubmitting}
            >
              Cancel
            </button>
            <button
              type="submit"
              onClick={handleSubmit}
              className="px-4 py-2 bg-gray-900 text-white rounded-lg hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-gray-900 focus:ring-offset-2 transition-colors flex items-center space-x-2"
              disabled={isSubmitting}
            >
              {isSubmitting ? (
                <>
                  <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-white"></div>
                  <span>Saving...</span>
                </>
              ) : (
                <>
                  <span>
                    {initialData ? "Update Customer" : "Add Customer"}
                  </span>
                </>
              )}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CustomerForm;
