export async function loginUserAPI(username, password) {
  const res = await fetch("http://localhost:8080/back_end/api/v1/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include", // 🔥 important for session/cookies
    body: JSON.stringify({ username, password }),
  });

  if (res.ok) {
    const data = await res.json();
    return data;
  }

  const err = await res.text(); // get error from backend
  throw new Error(err); // so it goes to catch block
}
