// src/services/api.js

const BASE_URL = "http://localhost:8080/back_end/api/v1/users";

export async function fetchUsers() {
  const res = await fetch(BASE_URL);
  if (!res.ok) throw new Error("Failed to fetch users");
  return res.json();
}

export async function addUser(userData) {
  const res = await fetch(BASE_URL + "/", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData),
  });
  if (!res.ok) {
    const err = await res.json();
    throw new Error(err.errorCode || "Failed to add user");
  }
  return res.json();
}

export async function updateUser(id, userData) {
  const res = await fetch(BASE_URL + "/" + id, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData),
  });
  if (!res.ok) {
    const err = await res.json();
    throw new Error(err.errorCode || "Failed to update user");
  }
  return res.json();
}

export async function deleteUser(id) {
  const res = await fetch(BASE_URL + "/" + id, {
    method: "DELETE",
  });
  if (!res.ok) {
    const err = await res.json();
    throw new Error(err.errorCode || "Failed to delete user");
  }
  return res.json();
}
