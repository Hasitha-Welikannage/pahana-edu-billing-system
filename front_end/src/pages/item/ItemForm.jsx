// src/components/ItemModal.jsx

import { useState, useEffect } from "react";

const ItemForm = ({ isOpen, onClose, onSubmit, initialData }) => {
  const [name, setName] = useState("");
  const [stock, setStock] = useState("");
  const [unitPrice, setUnitPrice] = useState("");

  useEffect(() => {
    if (initialData) {
      setName(initialData.name || "");
      setStock(initialData.stock || "");
      setUnitPrice(initialData.unitPrice || "");
    }
  }, [initialData]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ name, stock: Number(stock), unitPrice: Number(unitPrice) });
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black/50 flex justify-center items-center z-50">
      <div className="bg-white rounded-lg p-6 shadow-lg w-full max-w-md">
        <h2 className="text-xl font-semibold mb-4">
          {initialData ? "Edit Item" : "Add Item"}
        </h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            placeholder="Item Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="w-full border rounded p-2"
            required
          />
          <input
            type="number"
            placeholder="Stock"
            value={stock}
            onChange={(e) => setStock(e.target.value)}
            className="w-full border rounded p-2"
            required
          />
          <input
            type="number"
            step="0.01"
            placeholder="Unit Price"
            value={unitPrice}
            onChange={(e) => setUnitPrice(e.target.value)}
            className="w-full border rounded p-2"
            required
          />
          <div className="flex justify-end space-x-3">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 bg-gray-200 rounded"
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-4 py-2 bg-blue-600 text-white rounded"
            >
              {initialData ? "Update" : "Add"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ItemForm;
