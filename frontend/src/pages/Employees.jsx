
import { useEffect, useState } from "react";
import api from "../services/api";
import EmployeeTable from "../components/EmployeeTable";

function Employees() {
  const [employees, setEmployees] = useState([]);
  const [search, setSearch] = useState("");
  const [country, setCountry] = useState("");
  const [status, setStatus] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const timer = setTimeout(() => {
      fetchEmployees();
    }, 300);

    return () => clearTimeout(timer);
  }, [search, country, status]);

  const fetchEmployees = async () => {
    setLoading(true);
    setError("");

    try {
      const response = await api.get("/employees", {
        params: {
          search: search || undefined,
          country: country || undefined,
          status: status || undefined,
          page: 0,
          size: 100,
        },
      });

      setEmployees(response.data.content || []);
    } catch (error) {
      setError("Failed to load employees.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <div className="page-heading">
        <div>
          <h1>Employees</h1>
          <p>View and manage employee records.</p>
        </div>
      </div>

      <section className="content-card">
        <div className="filters">
          <input
            type="text"
            placeholder="Search employee..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />

          <input
            type="text"
            placeholder="Filter by country"
            value={country}
            onChange={(e) => setCountry(e.target.value)}
          />

          <select
            value={status}
            onChange={(e) => setStatus(e.target.value)}
          >
            <option value="">All statuses</option>
            <option value="ACTIVE">Active</option>
            <option value="INACTIVE">Inactive</option>
          </select>
        </div>

        {error && <p className="error-message">{error}</p>}

        {loading ? (
          <p>Loading employees...</p>
        ) : (
          <EmployeeTable employees={employees} />
        )}
      </section>
    </div>
  );
}

export default Employees;
