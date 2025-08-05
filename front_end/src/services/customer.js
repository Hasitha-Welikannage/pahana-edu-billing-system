const BASE_URL = "http://localhost:8080/back_end/api/v1/customers";

export async function fetchCustomers() {
  try {
    const response = await fetch(BASE_URL, {
      method: "GET",
      credentials: "include",
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to fetch customers. Please try again later.");
  }
}

export async function addCustomer(customerData) {
  try {
    const response = await fetch(BASE_URL, {
      method: "POST",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(customerData),
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to add customer. Please try again later.");
  }
}

export async function updateCustomer(id, customerData) {
  try {
    const response = await fetch(`${BASE_URL}/${id}`, {
      method: "PUT",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(customerData),
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to update customer. Please try again later.");
  }
}

export async function deleteCustomer(id) {
  try {
    const response = await fetch(`${BASE_URL}/${id}`, {
      method: "DELETE",
      credentials: "include",
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to delete customer. Please try again later.");
  }
}
