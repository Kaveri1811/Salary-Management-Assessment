
import { useEffect, useState } from "react";
import { Users, UserCheck, UserX, Wallet } from "lucide-react";
import api from "../services/api";
import EmployeeTable from "../components/EmployeeTable";

function Dashboard() {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get("/employees?page=0&size=100")
      .then((response) => {
        setEmployees(response.data.content || []);
      })
      .catch(() => {
        setError("Unable to load employees. Check whether the backend is running.");
      })
      .finally(() => setLoading(false));
  }, []);

  const activeCount = employees.filter(
    (e) => e.employmentStatus === "ACTIVE"
  ).length;

  const inactiveCount = employees.length - activeCount;

  const totalSalary = employees.reduce(
    (sum, employee) => sum + Number(employee.annualSalary || 0),
    0
  );

  const stats = [
    { title: "Total Employees", value: employees.length, icon: Users },
    { title: "Active Employees", value: activeCount, icon: UserCheck },
    { title: "Inactive Employees", value: inactiveCount, icon: UserX },
    {
      title: "Salary Total",
      value: totalSalary.toLocaleString("en-IN", {
        maximumFractionDigits: 2,
      }),
      icon: Wallet,
    },
  ];

  return (
    <div>
      <div className="page-heading">
        <div>
          <h1>Dashboard</h1>
          <p>Overview of your employee and salary information.</p>
        </div>
      </div>

      {error && <p className="error-message">{error}</p>}

      <div className="stats-grid">
        {stats.map((stat) => {
          const Icon = stat.icon;

          return (
            <div className="stat-card" key={stat.title}>
              <div className="stat-icon">
                <Icon size={23} />
              </div>
              <p>{stat.title}</p>
              <h2>{stat.value}</h2>
            </div>
          );
        })}
      </div>

      <section className="content-card">
        <div className="section-heading">
          <h2>Employee Overview</h2>
          <span>Recent employee records</span>
        </div>

        {loading ? (
          <p>Loading employees...</p>
        ) : (
          <EmployeeTable employees={employees.slice(0, 5)} />
        )}
      </section>
    </div>
  );
}

export default Dashboard;
