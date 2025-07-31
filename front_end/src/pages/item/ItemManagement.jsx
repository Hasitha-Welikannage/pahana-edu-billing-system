// src/pages/ItemPage.jsx

import { useEffect, useState } from "react";
import {
  fetchItems,
  addItem,
  updateItem,
  deleteItem,
} from "../../services/item";
import ItemModal from "./ItemForm";
import DeleteConfirm from "../user/DeleteConfirm";

const ItemManagement = () => {
  const [items, setItems] = useState([]);
  const [selectedItem, setSelectedItem] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [showDelete, setShowDelete] = useState(false);
  const [itemToDelete, setItemToDelete] = useState(null);

  useEffect(() => {
    loadItems();
  }, []);

  const loadItems = async () => {
    const response = await fetchItems();
    if (response.success) setItems(response.data);
  };

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

  const handleSubmit = async (data) => {
    if (selectedItem) {
      await updateItem(selectedItem.id, data);
    } else {
      await addItem(data);
    }
    setShowModal(false);
    loadItems();
  };

  const confirmDelete = async () => {
    if (itemToDelete) {
      await deleteItem(itemToDelete.id);
      setShowDelete(false);
      loadItems();
    }
  };

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Item Management</h1>
        <button
          onClick={handleAdd}
          className="bg-blue-600 text-white px-4 py-2 rounded"
        >
          Add Item
        </button>
      </div>

      <table className="min-w-full bg-white rounded shadow">
        <thead>
          <tr className="bg-gray-100 text-left">
            <th className="py-3 px-4">Name</th>
            <th className="py-3 px-4">Stock</th>
            <th className="py-3 px-4">Unit Price</th>
            <th className="py-3 px-4">Actions</th>
          </tr>
        </thead>
        <tbody>
          {items.map((item) => (
            <tr key={item.id} className="border-b">
              <td className="py-2 px-4">{item.name}</td>
              <td className="py-2 px-4">{item.stock}</td>
              <td className="py-2 px-4">Rs. {item.unitPrice.toFixed(2)}</td>
              <td className="py-2 px-4 space-x-2">
                <button
                  onClick={() => handleEdit(item)}
                  className="text-blue-600"
                >
                  Edit
                </button>
                <button
                  onClick={() => handleDelete(item)}
                  className="text-red-600"
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <ItemModal
        isOpen={showModal}
        onClose={() => setShowModal(false)}
        onSubmit={handleSubmit}
        initialData={selectedItem}
      />

      <DeleteConfirm
        isOpen={showDelete}
        onClose={() => setShowDelete(false)}
        onConfirm={confirmDelete}
        userName={itemToDelete?.name || ""}
      />
    </div>
  );
};

export default ItemManagement;
