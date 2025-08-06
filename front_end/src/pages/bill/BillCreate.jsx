// src/pages/BillCreate.jsx
import { useEffect, useState, useMemo } from "react";
import { fetchCustomers } from "../../services/customer";
import { fetchItems } from "../../services/item";
import { createBill } from "../../services/bill"; // Ensure this import is correct
import { useNavigate } from "react-router-dom";

// Import reusable components
import Header from "../../components/Header";
import StatsCard from "../../components/StatsCard";
import ErrorMessage from "../../components/ErrorMessage";

// Import Feather Icons for consistency
import {
  FiUsers,
  FiBox,
  FiDollarSign,
  FiPlus,
  FiArrowRight,
  FiUser,
} from "react-icons/fi"; // Removed FiPhone as it's not directly used for icon

function BillCreate() {
  const navigate = useNavigate();
  const [customers, setCustomers] = useState([]);
  const [items, setItems] = useState([]);
  const [selectedCustomerId, setSelectedCustomerId] = useState("");
  const [selectedCustomer, setSelectedCustomer] = useState(null);
  const [billItems, setBillItems] = useState([]);
  const [currentItemId, setCurrentItemId] = useState("");
  const [quantity, setQuantity] = useState("");
  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [phoneNumberInput, setPhoneNumberInput] = useState(""); // New state for phone number input

  useEffect(() => {
    loadInitialData();
  }, []);

  const loadInitialData = async () => {
    try {
      setLoading(true);
      setError("");

      const userStr = sessionStorage.getItem("user");
      if (userStr) {
        const user = JSON.parse(userStr);
        setCurrentUser(user);
      }

      const resCustomers = await fetchCustomers();
      if (resCustomers.success) {
        setCustomers(resCustomers.data);
      } else {
        setError(resCustomers.message || "Failed to load customers.");
      }

      const resItems = await fetchItems();
      if (resItems.success) {
        setItems(resItems.data);
      } else {
        setError(resItems.message || "Failed to load items.");
      }
    } catch (err) {
      setError("Error loading initial data.");
    } finally {
      setLoading(false);
    }
  };

  const handleAddItem = () => {
    const item = items.find((i) => i.id === parseInt(currentItemId));
    if (!item || !quantity || parseInt(quantity) <= 0) {
      setError("Please select an item and enter a valid quantity.");
      return;
    }

    // Check if item already exists in billItems to update quantity
    const existingItemIndex = billItems.findIndex(
      (billItem) => billItem.itemId === item.id
    );

    if (existingItemIndex > -1) {
      // Update quantity of existing item
      const updatedBillItems = [...billItems];
      updatedBillItems[existingItemIndex].quantity += parseInt(quantity);
      updatedBillItems[existingItemIndex].subTotal =
        updatedBillItems[existingItemIndex].quantity * item.price;
      setBillItems(updatedBillItems);
    } else {
      // Add new item
      const newItem = {
        itemId: item.id,
        itemName: item.name,
        unitPrice: item.price,
        quantity: parseInt(quantity),
        subTotal: item.price * parseInt(quantity),
      };
      setBillItems([...billItems, newItem]);
    }

    setCurrentItemId("");
    setQuantity("");
    setError(""); // Clear error after successful add
  };

  const handleRemoveItem = (index) => {
    const updatedItems = billItems.filter((_, i) => i !== index);
    setBillItems(updatedItems);
  };

  // New handler for phone number input
  const handlePhoneNumberChange = (e) => {
    const input = e.target.value;
    setPhoneNumberInput(input);
    setError(""); // Clear previous errors

    // Validate input: must be exactly 9 digits
    if (input.length === 9 && /^\d{9}$/.test(input)) {
      const fullPhoneNumber = `+94${input}`;
      const customer = customers.find((c) => c.phoneNumber === fullPhoneNumber);

      if (customer) {
        setSelectedCustomer(customer);
        setSelectedCustomerId(customer.id);
      } else {
        setSelectedCustomer(null);
        setSelectedCustomerId("");
        setError("No customer found with this phone number.");
      }
    } else {
      setSelectedCustomer(null);
      setSelectedCustomerId("");
      if (input.length > 0 && input.length < 9) {
        setError("Phone number must be 9 digits.");
      } else if (input.length > 9) {
        setError("Phone number cannot exceed 9 digits.");
      }
    }
  };

  const handleSubmit = async () => {
    if (!selectedCustomerId) {
      setError("Please select a customer by entering their phone number.");
      return;
    }
    if (billItems.length === 0) {
      setError("Please add at least one item to the bill.");
      return;
    }

    try {
      setIsSubmitting(true);
      setError("");

      const billData = {
        customerId: parseInt(selectedCustomerId),
        items: billItems.map((item) => ({
          itemId: item.itemId,
          quantity: item.quantity,
        })),
      };

      const res = await createBill(billData);
      if (res.success) {
        // Navigate to BillDetails page, only passing the ID
        navigate(`/bills/${res.data.id}`); // <--- Changed here: Removed `{ state: { bill: res.data } }`
      } else {
        setError(res.message || "Failed to create bill.");
      }
    } catch (err) {
      console.error("Error creating bill:", err);
      setError("Error creating bill. Please try again.");
    } finally {
      setIsSubmitting(false);
    }
  };

  const getTotalAmount = useMemo(() => {
    return billItems.reduce((total, item) => total + item.subTotal, 0);
  }, [billItems]);

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50 p-6">
        <div className="max-w-7xl mx-auto">
          <div className="flex items-center justify-center py-12">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900"></div>
            <span className="ml-2 text-gray-600">Loading initial data...</span>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header (using reusable Header component) */}
        <Header
          action={() => navigate("/bills/history")} // Action for the header button
          content={{
            title: "Create Bill",
            description: "Generate new invoice for customer purchases",
            buttonText: "View All Bills",
            buttonIcon: <FiArrowRight className="w-5 h-5" />,
          }}
        />

        {/* Stats Cards (using reusable StatsCard component) */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <StatsCard
            data={{
              icon: (
                <div className="p-2 bg-blue-50 rounded-lg">
                  <FiUsers className="w-5 h-5 text-blue-600" />
                </div>
              ),
              title: "Available Customers",
              value: customers.length,
            }}
          />
          <StatsCard
            data={{
              icon: (
                <div className="p-2 bg-green-50 rounded-lg">
                  <FiBox className="w-5 h-5 text-green-600" />
                </div>
              ),
              title: "Available Items",
              value: items.length,
            }}
          />
          <StatsCard
            data={{
              icon: (
                <div className="p-2 bg-purple-50 rounded-lg">
                  <FiDollarSign className="w-5 h-5 text-purple-600" />
                </div>
              ),
              title: "Bill Total",
              value: `Rs.${getTotalAmount.toFixed(2)}`,
            }}
          />
        </div>

        {/* Current User Info */}
        {currentUser && (
          <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-6">
            <div className="flex items-center">
              <FiUser className="w-5 h-5 text-blue-600 mr-3" />
              <div>
                <p className="text-sm text-blue-600 font-medium">
                  Logged in as
                </p>
                <p className="text-blue-800 font-semibold">
                  {currentUser.firstName} {currentUser.lastName} (
                  {currentUser.role})
                </p>
              </div>
            </div>
          </div>
        )}

        {/* Error Message (using reusable ErrorMessage component) */}
        {error && (
          <div className="mb-4">
            <ErrorMessage error={error} />
          </div>
        )}

        <div className="space-y-6">
          {/* Customer Selection by Phone Number */}
          <div className="bg-white rounded-lg border border-gray-200 p-6">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-semibold text-gray-900">
                Customer Selection
              </h3>
              <button
                onClick={() => navigate("/customers")}
                className="text-sm text-blue-600 hover:text-blue-700 font-medium flex items-center space-x-1"
              >
                <FiPlus className="w-4 h-4" />
                <span>Add New Customer</span>
              </button>
            </div>

            <label
              htmlFor="phoneNumber"
              className="block text-sm font-medium text-gray-700 mb-2"
            >
              Customer Phone Number (e.g., +94712345678)
            </label>
            <div className="flex rounded-lg shadow-sm">
              <span className="inline-flex items-center px-3 rounded-l-md border border-r-0 border-gray-300 bg-gray-50 text-gray-500 text-sm">
                +94
              </span>
              <input
                type="tel" // Use type="tel" for phone numbers
                id="phoneNumber"
                maxLength="9" // Max 9 digits
                pattern="[0-9]{9}" // Pattern for 9 digits
                placeholder="712345678"
                className="flex-1 block w-full rounded-none rounded-r-lg px-3 py-2 border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                value={phoneNumberInput}
                onChange={handlePhoneNumberChange}
              />
            </div>

            {/* Selected Customer Details */}
            {selectedCustomer && (
              <div className="mt-4 p-4 bg-gray-50 rounded-lg">
                <h4 className="text-sm font-semibold text-gray-900 mb-2">
                  Customer Information
                </h4>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-sm">
                  <div>
                    <span className="text-gray-600">Name:</span>
                    <p className="font-medium">
                      {selectedCustomer.firstName} {selectedCustomer.lastName}
                    </p>
                  </div>
                  <div>
                    <span className="text-gray-600">Phone:</span>
                    <p className="font-medium">
                      {selectedCustomer.phoneNumber}
                    </p>
                  </div>
                  <div>
                    <span className="text-gray-600">Address:</span>
                    <p className="font-medium">{selectedCustomer.address}</p>
                  </div>
                </div>
              </div>
            )}
            {!selectedCustomer && phoneNumberInput.length === 9 && !error && (
              <p className="mt-2 text-sm text-red-600">
                No customer found with this phone number.
              </p>
            )}
          </div>

          {/* Item Selection */}
          <div className="bg-white rounded-lg border border-gray-200 p-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">
              Add Items
            </h3>

            <div className="flex flex-col md:flex-row gap-4 mb-4">
              <div className="flex-1">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Select Item
                </label>
                <select
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  value={currentItemId}
                  onChange={(e) => setCurrentItemId(e.target.value)}
                >
                  <option value="">Choose an item</option>
                  {items.map((item) => (
                    <option key={item.id} value={item.id}>
                      {item.name} - Rs.{item.price.toFixed(2)}
                    </option>
                  ))}
                </select>
              </div>

              <div className="w-full md:w-32">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Quantity
                </label>
                <input
                  type="number"
                  min="1"
                  placeholder="Qty"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  value={quantity}
                  onChange={(e) => setQuantity(e.target.value)}
                />
              </div>

              <div className="flex items-end">
                <button
                  onClick={handleAddItem}
                  disabled={!currentItemId || !quantity}
                  className="px-4 py-2 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 disabled:bg-gray-300 disabled:cursor-not-allowed transition-colors flex items-center space-x-2"
                >
                  <FiPlus className="w-5 h-5" />
                  <span>Add Item</span>
                </button>
              </div>
            </div>
          </div>

          {/* Bill Items Table */}
          {billItems.length > 0 && (
            <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
              <div className="px-6 py-4 border-b border-gray-200">
                <div className="flex items-center justify-between">
                  <h3 className="text-lg font-semibold text-gray-900">
                    Bill Items
                  </h3>
                  <span className="bg-blue-100 text-blue-800 text-sm font-medium px-3 py-1 rounded-full">
                    {billItems.length}{" "}
                    {billItems.length === 1 ? "Item" : "Items"}
                  </span>
                </div>
              </div>

              <div className="overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                  <thead className="bg-gray-50">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Item
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Unit Price
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Quantity
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Subtotal
                      </th>
                      <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Actions
                      </th>
                    </tr>
                  </thead>
                  <tbody className="bg-white divide-y divide-gray-200">
                    {billItems.map((item, index) => (
                      <tr key={index} className="hover:bg-gray-50">
                        <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                          {item.itemName}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                          Rs.{item.unitPrice.toFixed(2)}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                          {item.quantity}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                          Rs.{item.subTotal.toFixed(2)}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                          <button
                            onClick={() => handleRemoveItem(index)}
                            className="px-3 py-1 text-sm text-red-600 hover:text-red-700 hover:bg-red-50 rounded transition-colors"
                          >
                            Remove
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>

              {/* Total */}
              <div className="px-6 py-4 bg-gray-50 border-t border-gray-200">
                <div className="flex justify-between items-center">
                  <span className="text-lg font-semibold text-gray-900">
                    Total Amount:
                  </span>
                  <span className="text-2xl font-bold text-gray-900">
                    Rs.{getTotalAmount.toFixed(2)}
                  </span>
                </div>
              </div>
            </div>
          )}

          {/* Submit Button */}
          <div className="flex justify-end">
            <button
              onClick={handleSubmit}
              disabled={
                !selectedCustomerId || billItems.length === 0 || isSubmitting
              }
              className="px-6 py-3 bg-green-600 text-white rounded-lg font-medium hover:bg-green-700 disabled:bg-gray-300 disabled:cursor-not-allowed transition-colors flex items-center space-x-2"
            >
              {isSubmitting ? (
                <>
                  <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-white"></div>
                  <span>Creating Bill...</span>
                </>
              ) : (
                <>
                  <FiPlus className="w-5 h-5" />
                  <span>Create Bill</span>
                </>
              )}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default BillCreate;
