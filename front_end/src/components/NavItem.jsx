import { Link } from "react-router-dom";
import "../css/NavItem.css"; // Assuming you have a CSS file for styling

function NavItem({ navItem }) {
  return (
    <Link to={navItem.link} className="nav-link">
      <div className="nav-item">
        <h3>{navItem.title}</h3>

        <span className="nav-icon">{navItem.icon}</span>
      </div>
    </Link>
  );
}

export default NavItem;
