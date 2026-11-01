
function EmployeeTable({ employees }) {
  if (!employees || employees.length === 0) {
    return <p className="empty-message">No employees found.</p>;
  }

  return (
    <div className="table-container">
      <table>
        <thead>
          <tr>
            <th>Employee</th>
            <th>Department</th>
            <th>Country</th>
            <th>Annual Salary</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          {employees.map((employee) => (
            <tr key={employee.id}>
              <td>
                <strong>
                  {employee.firstName} {employee.lastName}
                </strong>
                <small className="table-subtitle">
                  {employee.employeeCode}
                </small>
              </td>

              <td>
                {employee.department?.departmentName || "-"}
              </td>

              <td>{employee.country}</td>

              <td>
                {employee.currency}{" "}
                {Number(employee.annualSalary).toLocaleString()}
              </td>

              <td>
                <span
                  className={`status ${
                    employee.employmentStatus?.toLowerCase()
                  }`}
                >
                  {employee.employmentStatus}
                </span>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default EmployeeTable;
