const BASE_URL = "http://localhost:8080/back_end/api/v1/bills";

export async function createBill(billData) {
  try {
    const response = await fetch(BASE_URL, {
      method: "POST",
      credentials: "include",

      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(billData),
    });

    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error("Failed to create bill. Please try again later.");
  }
}

export async function getAllBills() {
  try {
    const response = await fetch(BASE_URL);
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching bills:", error);
    throw new Error("Failed to fetch bills. Please try again later.");
  }
}
