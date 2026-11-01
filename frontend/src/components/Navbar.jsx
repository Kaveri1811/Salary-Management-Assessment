
import { Bell, Search } from "lucide-react";

function Navbar() {
  return (
    <header className="navbar">
      <div>
        <h2>Salary Management</h2>
        <p>Manage your organization's workforce and salaries</p>
      </div>

      <div className="navbar-right">
        <button className="icon-button" aria-label="Search">
          <Search size={20} />
        </button>

        <button className="icon-button" aria-label="Notifications">
          <Bell size={20} />
        </button>

        <div className="profile">
          <div className="avatar">HR</div>
          <div>
            <strong>HR Admin</strong>
            <small>Administrator</small>
          </div>
        </div>
      </div>
    </header>
  );
}

export default Navbar;
