import { useEffect, useState, useMemo } from "react";
import { fetchItems, deleteItem } from "../../services/item";

// Import components from the shared pattern
import Header from "../../components/Header";
import StatsCard from "../../components/StatsCard";
import ErrorMessage from "../../components/ErrorMessage";
import DeleteConfirm from "../../components/DeleteConfirm";
import ItemForm from "./ItemForm";

// Import the Feather icons for consistency
import { FiBox, FiDollarSign, FiAlertTriangle, FiPlus } from "react-icons/fi";

const ItemManagement = () => {
  const [items, setItems] = useState([]);
  const [selectedItem, setSelectedItem] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [showDelete, setShowDelete] = useState(false);
  const [itemToDelete, setItemToDelete] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadItems = async () => {
    try {
      setLoading(true);
      setError("");
      const response = await fetchItems();
      if (response.success) {
        setItems(response.data);
      } else {
        setError("Failed to load items");
      }
    } catch (err) {
      setError("Error loading items");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadItems();
  }, []);

  const handleAdd = () => {
    setSelectedItem(null);
    setShowModal(true);
  };

  const handleEdit = (item) => {
    setSelectedItem(item);
    setShowModal(true);
  };

  const handleDelete = (item) => {
    setItemToDelete(item);
    setShowDelete(true);
  };

  // New success callback to refresh the list and close the modal
  const handleSaveSuccess = () => {
    setShowModal(false);
    setSelectedItem(null);
    loadItems();
  };

  const confirmDelete = async () => {
    try {
      if (itemToDelete) {
        const data = await deleteItem(itemToDelete.id);
        if (!data.success) {
          setError(data.message || "Failed to delete item");
          return;
        }
        loadItems();
      }
    } catch (err) {
      setError("Failed to delete item");
    }finally {
      setShowDelete(false);
      setItemToDelete(null);
    }
  };

  // Helper functions for stats, memoized for performance
  const getTotalValue = useMemo(() => {
    return items.reduce((total, item) => total + item.stock * item.price, 0);
  }, [items]);

  const getLowStockItems = useMemo(() => {
    return items.filter((item) => item.stock <= 5).length;
  }, [items]);

  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header (Refactored) */}
        <Header
          action={handleAdd}
          content={{
            title: "Item Management",
            description: "Manage your inventory and stock levels",
            buttonText: "Add Item",
            buttonIcon: <FiPlus className="w-5 h-5" />,
          }}
          showActionButton={true}
        />

        {/* Stats Cards (Refactored) */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <StatsCard
            data={{
              icon: (
                <div className="p-2 bg-blue-50 rounded-lg">
                  <FiBox className="w-5 h-5 text-blue-600" />
                </div>
              ),
              title: "Total Items",
              value: items.length,
            }}
          />
          <StatsCard
            data={{
              icon: (
                <div className="p-2 bg-green-50 rounded-lg">
                  <FiDollarSign className="w-5 h-5 text-green-600" />
                </div>
              ),
              title: "Total Value",
              value: `Rs. ${getTotalValue.toFixed(2)}`,
            }}
          />
          <StatsCard
            data={{
              icon: (
                <div className="p-2 bg-red-50 rounded-lg">
                  <FiAlertTriangle className="w-5 h-5 text-red-600" />
                </div>
              ),
              title: "Low Stock",
              value: getLowStockItems,
            }}
          />
        </div>

        {/* Error Message (Refactored) */}
        {error && (
          <div className="mb-4">
            <ErrorMessage error={error} />
          </div>
        )}

        {/* Items Table */}
        <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
          <div className="px-6 py-4 border-b border-gray-200">
            <div className="flex items-center justify-between">
              <h3 className="text-lg font-semibold text-gray-900">
                Inventory Items
              </h3>
              <span className="bg-blue-100 text-blue-800 text-sm font-medium px-3 py-1 rounded-full">
                {items.length} {items.length === 1 ? "Item" : "Items"}
              </span>
            </div>
          </div>

          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Item Name
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Stock
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Unit Price
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    Total Value
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
                          Loading items...
                        </span>
                      </div>
                    </td>
                  </tr>
                ) : items.length === 0 ? (
                  <tr>
                    <td colSpan="5" className="px-6 py-12 text-center">
                      <div className="text-gray-500">
                        <FiBox className="mx-auto h-12 w-12 text-gray-400 mb-4" />

                        <p className="text-lg font-medium text-gray-900 mb-2">
                          No items found
                        </p>
                        <p className="text-gray-500">
                          Get started by adding your first inventory item.
                        </p>
                      </div>
                    </td>
                  </tr>
                ) : (
                  items.map((item) => (
                    <tr
                      key={item.id}
                      className="hover:bg-gray-50 transition-colors"
                    >
                      <td className="px-6 py-4 whitespace-nowrap">
                        <div className="text-sm font-medium text-gray-900">
                          {item.name}
                        </div>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span
                          className={`inline-flex px-2 py-1 text-xs font-semibold rounded-full ${
                            item.stock <= 5
                              ? "bg-red-100 text-red-800"
                              : item.stock <= 20
                              ? "bg-yellow-100 text-yellow-800"
                              : "bg-green-100 text-green-800"
                          }`}
                        >
                          {item.stock} units
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        Rs. {item.price.toFixed(2)}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                        Rs. {(item.stock * item.price).toFixed(2)}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <div className="flex items-center justify-end space-x-2">
                          <button
                            onClick={() => handleEdit(item)}
                            className="px-3 py-1 text-sm text-gray-700 hover:text-gray-900 hover:bg-gray-100 rounded transition-colors cursor-pointer"
                          >
                            Edit
                          </button>
                          <button
                            onClick={() => handleDelete(item)}
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

      <ItemForm
        isOpen={showModal}
        onClose={() => setShowModal(false)}
        onSaveSuccess={handleSaveSuccess}
        initialData={selectedItem}
      />

      <DeleteConfirm
        isOpen={showDelete}
        onClose={() => setShowDelete(false)}
        onConfirm={confirmDelete}
        itemType="item"
        itemName={itemToDelete?.name || ""}
      />
    </div>
  );
};

export default ItemManagement;
