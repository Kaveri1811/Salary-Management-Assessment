
import { NavLink } from "react-router-dom";
import {
  LayoutDashboard,
  Users,
  UserPlus,
  History,
  Wallet,
} from "lucide-react";

function Sidebar() {
  const links = [
    { name: "Dashboard", path: "/", icon: LayoutDashboard },
    { name: "Employees", path: "/employees", icon: Users },
    { name: "Add Employee", path: "/add-employee", icon: UserPlus },
    { name: "Salary History", path: "/salary-history", icon: History },
  ];

  return (
    <aside className="sidebar">
      <div className="brand">
        <Wallet size={27} />
        <span>ACME HR</span>
      </div>

      <p className="menu-label">MAIN MENU</p>

      <nav>
        {links.map((link) => {
          const Icon = link.icon;

          return (
            <NavLink
              key={link.path}
              to={link.path}
              end={link.path === "/"}
              className={({ isActive }) =>
                isActive ? "nav-link active" : "nav-link"
              }
            >
              <Icon size={19} />
              <span>{link.name}</span>
            </NavLink>
          );
        })}
      </nav>

      <div className="sidebar-footer">
        Employee Salary Management
        <br />
        <small>ACME Organization</small>
      </div>
    </aside>
  );
}

export default Sidebar;
