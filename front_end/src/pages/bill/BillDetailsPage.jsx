// src/pages/BillDetails.jsx
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { FiCheckCircle, FiPlus, FiPrinter } from "react-icons/fi"; // Feather icons

import Header from "../../components/Header"; // Assuming Header is in components
import ErrorMessage from "../../components/ErrorMessage"; // Assuming ErrorMessage is in components

function BillDetailsPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const [bill, setBill] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    // Check if bill data was passed via navigation state
    if (location.state && location.state.bill) {
      setBill(location.state.bill);
      setError("");
    } else {
      // If no bill data, show an error or redirect
      setError("No bill details found. Please create a bill first.");
      // Optionally, navigate back to the bill creation page after a delay
      // const timer = setTimeout(() => navigate('/bills/create'), 3000);
      // return () => clearTimeout(timer);
    }
  }, [location.state, navigate]);

  const handleCreateNewBill = () => {
    navigate("/bills/create"); // Navigate back to the bill creation page
  };

  const handlePrintBill = () => {
    // In a real application, you'd trigger a print functionality here.
    // For browser printing, you might use window.print() or a dedicated library.
    alert("Print functionality not implemented yet!");
    console.log("Printing bill:", bill);
  };

  if (error) {
    return (
      <div className="min-h-screen bg-gray-50 p-6">
        <div className="max-w-7xl mx-auto">
          <div className="mb-4">
            <ErrorMessage error={error} />
          </div>
          <div className="flex justify-center mt-8">
            <button
              onClick={handleCreateNewBill}
              className="px-6 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 transition-colors flex items-center space-x-2"
            >
              <FiPlus className="w-5 h-5" />
              <span>Create New Bill</span>
            </button>
          </div>
        </div>
      </div>
    );
  }

  if (!bill) {
    // Show a loading state or a message while waiting for bill data
    return (
      <div className="min-h-screen bg-gray-50 p-6">
        <div className="max-w-7xl mx-auto">
          <div className="flex items-center justify-center py-12">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900"></div>
            <span className="ml-2 text-gray-600">Loading bill details...</span>
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
          action={handleCreateNewBill}
          content={{
            title: "Bill Details",
            description: `Details for Bill ID: #${bill.id}`,
            buttonText: "Create New Bill",
            buttonIcon: <FiPlus className="w-5 h-5" />,
          }}
        />

        {/* Bill Summary Card */}
        <div className="bg-white rounded-lg border border-gray-200 p-6 mb-6">
          <div className="flex items-center justify-between mb-6">
            <div className="flex items-center space-x-3">
              <div className="p-2 bg-green-50 rounded-lg">
                <FiCheckCircle className="w-6 h-6 text-green-600" />
              </div>
              <div>
                <h3 className="text-lg font-semibold text-gray-900">Bill Created Successfully!</h3>
                <p className="text-gray-600">Bill ID: #{bill.id}</p>
              </div>
            </div>

            <button
              onClick={handlePrintBill}
              className="px-4 py-2 bg-gray-900 text-white rounded-lg font-medium hover:bg-gray-800 transition-colors flex items-center space-x-2"
            >
              <FiPrinter className="w-5 h-5" />
              <span>Print Bill</span>
            </button>
          </div>

          {/* Bill Details */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
            <div className="space-y-4">
              <div>
                <h4 className="text-sm font-semibold text-gray-900 mb-2">Bill Information</h4>
                <div className="space-y-2 text-sm">
                  <div className="flex justify-between">
                    <span className="text-gray-600">Date:</span>
                    <span className="font-medium">{bill.date}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-gray-600">Created by:</span>
                    <span className="font-medium">
                      {bill.user?.firstName} {bill.user?.lastName}
                    </span>
                  </div>
                </div>
              </div>
            </div>

            <div className="space-y-4">
              <div>
                <h4 className="text-sm font-semibold text-gray-900 mb-2">Customer Information</h4>
                <div className="space-y-2 text-sm">
                  <div className="flex justify-between">
                    <span className="text-gray-600">Name:</span>
                    <span className="font-medium">
                      {bill.customer?.firstName} {bill.customer?.lastName}
                    </span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-gray-600">Phone:</span>
                    <span className="font-medium">{bill.customer?.phoneNumber}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-gray-600">Address:</span>
                    <span className="font-medium">{bill.customer?.address}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* Bill Items */}
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Item
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Quantity
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Unit Price
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Subtotal
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {bill.billItems.map((item) => (
                  <tr key={item.id}>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                      {item.itemName}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {item.quantity}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      Rs.{item.unitPrice.toFixed(2)}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                      Rs.{item.subTotal.toFixed(2)}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Total */}
          <div className="mt-6 pt-6 border-t border-gray-200">
            <div className="flex justify-between items-center">
              <span className="text-xl font-semibold text-gray-900">Total Amount:</span>
              <span className="text-3xl font-bold text-green-600">Rs.{bill.total.toFixed(2)}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default BillDetailsPage;
