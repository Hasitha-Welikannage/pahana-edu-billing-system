import NavItem from "../components/NavItem";
import "../css/Dashboard.css"; // Assuming you have a CSS file for styling

function Dashboard() {

  const iconsvg = (<svg xmlns='http://www.w3.org/2000/svg' height='24px' viewBox='0 -960 960 960' width='24px' fill='#1f1f1f'><path d='M504-480 320-664l56-56 240 240-240 240-56-56 184-184Z'/></svg>);

  const navItems = [
    { id: 1, title: "Create a Bill", icon: iconsvg, link: "/createBill" },
    { id: 3, title: "View Bill History", icon: iconsvg, link: "/billHistory" },
    { id: 2, title: "Manage Customers", icon: iconsvg, link: "/customers" },
    { id: 4, title: "Manage Users", icon: iconsvg, link: "/users" },
    { id: 5, title: "Manage Items", icon: iconsvg, link: "/items" },
  ];

  return (
    <div className="dashboard-page">
      <h1>Dashboard</h1>
      <p>Welcome to the dashboard!</p>
      <div className="nav-items">
        {navItems.map((item) => (
          <NavItem key={item.id} navItem={item} />
        ))}
      </div>

    </div>
  );
}

export default Dashboard;