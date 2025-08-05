const BASE_URL = "http://localhost:8080/back_end/api/v1/users";

export async function fetchUsers() {
  try {
    const response = await fetch(BASE_URL, {
      method: "GET",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to fetch users. Please try again later.");
  }
}

export async function addUser(userData) {
  try {
    const response = await fetch(BASE_URL + "/", {
      method: "POST",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(userData),
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to add user. Please try again later.");
  }
}

export async function updateUser(id, userData) {
  try {
    const response = await fetch(BASE_URL + "/" + id, {
      method: "PUT",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(userData),
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to update user. Please try again later.");
  }
}

export async function deleteUser(id) {
  try {
    const response = await fetch(BASE_URL + "/" + id, {
      method: "DELETE",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to delete user. Please try again later.");
  }
}
