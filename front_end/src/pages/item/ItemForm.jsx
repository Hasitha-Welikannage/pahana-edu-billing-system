import { useState, useEffect } from "react";
import ErrorMessage from "../../components/ErrorMessage";
import { addItem, updateItem } from "../../services/item";

const ItemForm = ({ isOpen, onClose, onSaveSuccess, initialData }) => {
  const [formData, setFormData] = useState({
    name: "",
    stock: "",
    price: "",
  });
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    if (initialData) {
      setFormData({
        name: initialData.name || "",
        stock: initialData.stock || "",
        price: initialData.price || "",
      });
    } else {
      setFormData({
        name: "",
        stock: "",
        price: "",
      });
    }
    setError("");
  }, [initialData, isOpen]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    const newValue =
      name === "stock" || name === "price" ? Number(value) : value;
    setFormData((prevData) => ({ ...prevData, [name]: newValue }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!formData.name || formData.stock === "" || formData.price === "") {
      setError("All fields are required.");
      return;
    }
    if (formData.stock <= 0 || formData.price <= 0) {
      setError("Stock and Unit Price must be positive.");
      return;
    }

    setIsSubmitting(true);
    try {
      const data = initialData
        ? await updateItem(initialData.id, formData)
        : await addItem(formData);

      if (data.success) {
        onSaveSuccess(); // Notify parent on success
      } else {
        setError(data.message || "An error occurred while saving.");
      }
    } catch (err) {
      setError("Failed to save item. Please try again.");
      console.error("ItemForm submission error:", err);
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
              {initialData ? "Edit Item" : "Add New Item"}
            </h2>
          </div>
        </div>

        {/* Content */}
        <div className="px-6 py-4">
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label
                htmlFor="name"
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                Item Name
              </label>
              <input
                type="text"
                id="name"
                name="name"
                placeholder="Enter item name"
                value={formData.name}
                onChange={handleChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent"
                required
              />
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label
                  htmlFor="stock"
                  className="block text-sm font-medium text-gray-700 mb-1"
                >
                  Stock Quantity
                </label>
                <input
                  type="number"
                  id="stock"
                  name="stock"
                  placeholder="0"
                  value={formData.stock}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent"
                  min="0"
                  required
                />
              </div>

              <div>
                <label
                  htmlFor="price"
                  className="block text-sm font-medium text-gray-700 mb-1"
                >
                  Unit Price (Rs.)
                </label>
                <input
                  type="number"
                  id="price"
                  name="price"
                  step="0.01"
                  placeholder="0.00"
                  value={formData.price}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent"
                  min="0"
                  required
                />
              </div>
            </div>

            {/* Preview Section */}
            {(formData.name || formData.stock || formData.price) && (
              <div className="mt-4 p-3 bg-gray-50 rounded-lg border">
                <h4 className="text-sm font-medium text-gray-700 mb-2">
                  Preview
                </h4>
                <div className="text-sm text-gray-600 space-y-1">
                  <div>
                    <span className="font-medium">Item:</span>{" "}
                    {formData.name || "Item name"}
                  </div>
                  <div>
                    <span className="font-medium">Stock:</span>{" "}
                    {formData.stock === "" ? "0" : formData.stock} units
                  </div>
                  <div>
                    <span className="font-medium">Price:</span> Rs.
                    {formData.price === ""
                      ? "0.00"
                      : Number(formData.price).toFixed(2)}
                  </div>
                  {formData.stock !== "" && formData.price !== "" && (
                    <div className="pt-1 border-t border-gray-200">
                      <span className="font-medium">Total Value:</span> Rs.
                      {(
                        Number(formData.stock) * Number(formData.price)
                      ).toFixed(2)}
                    </div>
                  )}
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
              disabled={isSubmitting}
            >
              Cancel
            </button>
            <button
              type="submit"
              onClick={handleSubmit}
              className="px-4 py-2 bg-gray-900 text-white rounded-lg hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-gray-900 focus:ring-offset-2 transition-colors flex items-center space-x-2 cursor-pointer"
              disabled={isSubmitting}
            >
              {isSubmitting ? (
                <>
                  <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-white"></div>
                  <span>Saving...</span>
                </>
              ) : (
                <>
                  <span>{initialData ? "Update Item" : "Add Item"}</span>
                </>
              )}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ItemForm;
