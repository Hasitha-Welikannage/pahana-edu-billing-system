import { useEffect, useState, useMemo } from "react";
import { fetchCustomers, deleteCustomer } from "../../services/customer";

// Import reusable components
import Header from "../../components/Header";
import StatsCard from "../../components/StatsCard";
import ErrorMessage from "../../components/ErrorMessage";
import DeleteConfirm from "../../components/DeleteConfirm";
import CustomerForm from "./CustomerForm";

// Import Feather Icons for consistency
import { FiUsers, FiMapPin, FiClock, FiPlus } from "react-icons/fi";

const CustomerManagement = () => {
  const [customers, setCustomers] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [editingCustomer, setEditingCustomer] = useState(null);
  const [showDelete, setShowDelete] = useState(false);
  const [deletingCustomer, setDeletingCustomer] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadCustomers = async () => {
    try {
      setLoading(true);
      setError("");
      const data = await fetchCustomers();
      if (data.success) {
        setCustomers(data.data);
      } else {
        setError(data.message || "Failed to load customers");
      }
    } catch (error) {
      setError("Error loading customers");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadCustomers();
  }, []);

  const handleAdd = () => {
    setEditingCustomer(null);
    setShowForm(true);
  };

  const handleEdit = (customer) => {
    setEditingCustomer(customer);
    setShowForm(true);
  };

  const handleDelete = (customer) => {
    setDeletingCustomer(customer);
    setShowDelete(true);
  };

  const handleSaveSuccess = () => {
    setShowForm(false);
    setEditingCustomer(null);
    loadCustomers();
  };

  const handleConfirmDelete = async () => {
    try {
      const data = await deleteCustomer(deletingCustomer.id);
      if (!data.success) {
        setError(data.message || "Failed to delete customer");
        return;
      }
      loadCustomers();
    } catch (error) {
      setError("Failed to delete customer");
    } finally {
      setShowDelete(false);
      setDeletingCustomer(null);
    }
  };

  // Helper functions for stats, memoized for performance
  const getCustomersByLocation = useMemo(() => {
    const locations = {};
    customers.forEach((customer) => {
      const city = customer.address.split(",")[0]?.trim() || "Unknown";
      locations[city] = (locations[city] || 0) + 1;
    });
    return Object.keys(locations).length;
  }, [customers]);

  const getRecentCustomers = useMemo(() => {
    return customers.filter((customer) => customer.id > customers.length - 5)
      .length;
  }, [customers]);

  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <Header
          action={handleAdd}
          content={{
            title: "Customer Management",
            description: "Manage your customer database and contacts",
            buttonText: "Add Customer",
            buttonIcon: <FiPlus className="w-5 h-5" />,
          }}
          showActionButton={true}
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
              title: "Total Customers",
              value: customers.length,
            }}
          />
          <StatsCard
            data={{
              icon: (
                <div className="p-2 bg-green-50 rounded-lg">
                  <FiMapPin className="w-5 h-5 text-green-600" />
                </div>
              ),
              title: "Locations",
              value: getCustomersByLocation,
            }}
          />
          <StatsCard
            data={{
              icon: (
                <div className="p-2 bg-purple-50 rounded-lg">
                  <FiClock className="w-5 h-5 text-purple-600" />
                </div>
              ),
              title: "Recent Additions",
              value: getRecentCustomers,
            }}
          />
        </div>

        {/* Error Message */}
        {error && (
          <div className="mb-4">
            <ErrorMessage error={error} />
          </div>
        )}

        {/* Customers Table */}
        <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
          <div className="px-6 py-4 border-b border-gray-200">
            <div className="flex items-center justify-between">
              <h3 className="text-lg font-semibold text-gray-900">
                Customer Directory
              </h3>
              <span className="bg-blue-100 text-blue-800 text-sm font-medium px-3 py-1 rounded-full">
                {customers.length}{" "}
                {customers.length === 1 ? "Customer" : "Customers"}
              </span>
            </div>
          </div>

          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Customer
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Contact
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Address
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
                          Loading customers...
                        </span>
                      </div>
                    </td>
                  </tr>
                ) : customers.length === 0 ? (
                  <tr>
                    <td colSpan="5" className="px-6 py-12 text-center">
                      <div className="text-gray-500">
                        <FiUsers className="mx-auto h-12 w-12 text-gray-400 mb-4" />
                        <p className="text-lg font-medium text-gray-900 mb-2">
                          No customers found
                        </p>
                        <p className="text-gray-500">
                          Get started by adding your first customer.
                        </p>
                      </div>
                    </td>
                  </tr>
                ) : (
                  customers.map((customer) => (
                    <tr
                      key={customer.id}
                      className="hover:bg-gray-50 transition-colors"
                    >
                      <td className="px-6 py-4 whitespace-nowrap">
                        <div className="flex items-center">
                          <div className="flex-shrink-0 h-10 w-10">
                            <div className="h-10 w-10 rounded-full bg-gray-200 flex items-center justify-center text-gray-600 font-medium text-sm">
                              {customer.firstName?.charAt(0)}
                              {customer.lastName?.charAt(0)}
                            </div>
                          </div>
                          <div className="ml-4">
                            <div className="text-sm font-medium text-gray-900">
                              {customer.firstName} {customer.lastName}
                            </div>
                          </div>
                        </div>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <div className="text-sm text-gray-900">
                          {customer.phoneNumber}
                        </div>
                      </td>
                      <td className="px-6 py-4">
                        <div
                          className="text-sm text-gray-900 max-w-xs truncate"
                          title={customer.address}
                        >
                          {customer.address}
                        </div>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        #{customer.id}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <div className="flex items-center justify-end space-x-2">
                          <button
                            onClick={() => handleEdit(customer)}
                            className="px-3 py-1 text-sm text-gray-700 hover:text-gray-900 hover:bg-gray-100 rounded transition-colors cursor-pointer"
                          >
                            Edit
                          </button>
                          <button
                            onClick={() => handleDelete(customer)}
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
      </div>

      <CustomerForm
        isOpen={showForm}
        onClose={() => {
          setShowForm(false);
          setEditingCustomer(null);
        }}
        onSaveSuccess={handleSaveSuccess}
        initialData={editingCustomer}
      />

      <DeleteConfirm
        isOpen={showDelete}
        onClose={() => setShowDelete(false)}
        onConfirm={handleConfirmDelete}
        itemType="customer"
        itemName={
          deletingCustomer
            ? `${deletingCustomer.firstName} ${deletingCustomer.lastName}`
            : ""
        }
      />
    </div>
  );
};

export default CustomerManagement;
