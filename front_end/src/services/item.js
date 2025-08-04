// src/api/itemApi.js

const BASE_URL = "http://localhost:8080/back_end/api/v1/items";

export async function fetchItems() {
  try {
    const response = await fetch(BASE_URL);
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to fetch items. Please try again later.");
  }
}

export async function addItem(itemData) {
  try {
    const response = await fetch(BASE_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(itemData),
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to add item. Please try again later.");
  }
}

export async function updateItem(id, itemData) {
  try {
    const response = await fetch(`${BASE_URL}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(itemData),
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to update item. Please try again later.");
  }
}

export async function deleteItem(id) {
  try {
    const response = await fetch(`${BASE_URL}/${id}`, {
      method: "DELETE",
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to delete item. Please try again later.");
  }
}
