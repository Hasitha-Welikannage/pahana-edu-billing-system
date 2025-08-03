const BASE_URL = "http://localhost:8080/back_end/api/v1/auth";

export async function login(credentials) {
  try {
    const response = await fetch(BASE_URL + "/login", {
      method: "POST",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(credentials),
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to login. Please try again later." + error.message);
  }
}

export async function logout() {
  try {
    const response = await fetch(BASE_URL + "/logout", {
      method: "POST",
    });
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error(
      "Failed to logout. Please try again later." + error.message
    );
  }
}
