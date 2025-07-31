import { useEffect, useState } from "react";
import {
  fetchCustomers,
  addCustomer,
  updateCustomer,
  deleteCustomer,
} from "../../services/customer";
import CustomerForm from "./CustomerForm";
import DeleteConfirm from "../user/DeleteConfirm";

const CustomerManagement = () => {
  const [customers, setCustomers] = useState([]);
  const [formOpen, setFormOpen] = useState(false);
  const [selectedCustomer, setSelectedCustomer] = useState(null);
  const [confirmOpen, setConfirmOpen] = useState(false);
  const [deleteTarget, setDeleteTarget] = useState(null);

  const loadCustomers = async () => {
    try {
      const result = await fetchCustomers();
      setCustomers(result.data);
    } catch (error) {
      alert(error.message);
    }
  };

  useEffect(() => {
    loadCustomers();
  }, []);

  const handleSave = async (data) => {
    try {
      if (selectedCustomer) {
        await updateCustomer(selectedCustomer.id, data);
      } else {
        await addCustomer(data);
      }
      setFormOpen(false);
      setSelectedCustomer(null);
      loadCustomers();
    } catch (error) {
      alert(error.message);
    }
  };

  const handleDelete = async () => {
    try {
      await deleteCustomer(deleteTarget.id);
      setConfirmOpen(false);
      loadCustomers();
    } catch (error) {
      alert(error.message);
    }
  };

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-2xl font-semibold">Customer Management</h2>
        <button
          onClick={() => {
            setSelectedCustomer(null);
            setFormOpen(true);
          }}
          className="btn-primary"
        >
          Add Customer
        </button>
      </div>

      <div className="overflow-x-auto">
        <table className="min-w-full bg-white border">
          <thead className="bg-gray-100">
            <tr>
              <th className="th">ID</th>
              <th className="th">First Name</th>
              <th className="th">Last Name</th>
              <th className="th">Address</th>
              <th className="th">Phone</th>
              <th className="th">Actions</th>
            </tr>
          </thead>
          <tbody>
            {customers.map((cust) => (
              <tr key={cust.id} className="border-b">
                <td className="td">{cust.id}</td>
                <td className="td">{cust.firstName}</td>
                <td className="td">{cust.lastName}</td>
                <td className="td">{cust.address}</td>
                <td className="td">{cust.phoneNumber}</td>
                <td className="td">
                  <button
                    className="btn-secondary mr-2"
                    onClick={() => {
                      setSelectedCustomer(cust);
                      setFormOpen(true);
                    }}
                  >
                    Edit
                  </button>
                  <button
                    className="btn-danger"
                    onClick={() => {
                      setDeleteTarget(cust);
                      setConfirmOpen(true);
                    }}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <CustomerForm
        isOpen={formOpen}
        onClose={() => {
          setFormOpen(false);
          setSelectedCustomer(null);
        }}
        onSave={handleSave}
        initialData={selectedCustomer}
      />

      <DeleteConfirm
        isOpen={confirmOpen}
        onClose={() => setConfirmOpen(false)}
        onConfirm={handleDelete}
        userName={`${deleteTarget?.firstName} ${deleteTarget?.lastName}`}
      />
    </div>
  );
};

export default CustomerManagement;
