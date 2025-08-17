import { useState } from "react";
import Header from "../../components/Header";
import {
  FiUsers,
  FiUserCheck,
  FiPackage,
  FiFileText,
  FiHelpCircle,
  FiChevronDown,
  FiChevronRight,
} from "react-icons/fi";

function Help() {
  const [expandedSections, setExpandedSections] = useState({});

  const toggleSection = (id) => {
    setExpandedSections((prev) => ({
      ...prev,
      [id]: !prev[id],
    }));
  };

  const Section = ({ id, title, icon: Icon, children }) => {
    const isExpanded = expandedSections[id];

    return (
      <div className="border border-gray-200 rounded-lg mb-4 overflow-hidden">
        <button
          onClick={() => toggleSection(id)}
          className="w-full px-6 py-4 bg-gray-50 hover:bg-gray-100 flex items-center justify-between transition-colors"
        >
          <div className="flex items-center space-x-3">
            <Icon className="w-5 h-5 text-blue-600" />
            <h3 className="text-lg font-semibold text-gray-900">{title}</h3>
          </div>
          {isExpanded ? (
            <FiChevronDown className="w-5 h-5 text-gray-500" />
          ) : (
            <FiChevronRight className="w-5 h-5 text-gray-500" />
          )}
        </button>
        {isExpanded && <div className="px-6 py-4 bg-white">{children}</div>}
      </div>
    );
  };

  const SubSection = ({ title, children }) => (
    <div className="mb-6">
      <h4 className="text-md font-medium text-gray-800 mb-3 border-b pb-1">
        {title}
      </h4>
      {children}
    </div>
  );

  const Step = ({ number, title, description }) => (
    <div className="flex items-start space-x-3 mb-4">
      <div className="flex-shrink-0 w-6 h-6 bg-blue-600 text-white rounded-full flex items-center justify-center text-sm font-medium">
        {number}
      </div>
      <div>
        <h5 className="font-medium text-gray-900">{title}</h5>
        <p className="text-gray-600 text-sm mt-1">{description}</p>
      </div>
    </div>
  );

  {
    /* Page Content */
  }
  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <Header
          content={{
            title: "Help",
            description: "Get assistance with using the system",
            buttonText: "Contact Support",
            buttonIcon: <FiHelpCircle className="w-5 h-5" />,
          }}
          showActionButton={false}
        />

        {/* Main Content */}
        <div className="lg:col-span-3">
          {/* Getting Started */}
          <div className="bg-blue-50 border border-blue-200 rounded-lg p-6 mb-8">
            <h2 className="text-xl font-semibold text-blue-900 mb-3">
              Getting Started
            </h2>
            <p className="text-blue-800 mb-4">
              Welcome to Pahana Edu billing system! This comprehensive guide
              will help you manage your bookstore efficiently.
            </p>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 ">
              <div className="bg-white p-4 rounded-lg shadow-sm border border-gray-200 hover:shadow-md transition-shadow">
                <FiUserCheck className="w-6 h-6 text-blue-600 mb-2" />
                <h3 className="font-medium mb-1">Manage Users</h3>
                <p className="text-sm text-gray-600">
                  Add, edit, and organize your user accounts
                </p>
              </div>

              <div className="bg-white p-4 rounded-lg shadow-sm border border-gray-200 hover:shadow-md transition-shadow">
                <FiUsers className="w-6 h-6 text-purple-600 mb-2" />
                <h3 className="font-medium mb-1">Manage Customers</h3>
                <p className="text-sm text-gray-600">
                  Add, edit, and organize your customer accounts
                </p>
              </div>

              <div className="bg-white p-4 rounded-lg shadow-sm border border-gray-200 hover:shadow-md transition-shadow">
                <FiPackage className="w-6 h-6 text-orange-600 mb-2" />
                <h3 className="font-medium mb-1">Manage Items</h3>
                <p className="text-sm text-gray-600">
                  Add, edit, and organize your item inventory
                </p>
              </div>

              <div className="bg-white p-4 rounded-lg shadow-sm border border-gray-200 hover:shadow-md transition-shadow">
                <FiFileText className="w-6 h-6 text-green-600 mb-2" />
                <h3 className="font-medium mb-1">Process Bills</h3>
                <p className="text-sm text-gray-600">
                  Create bills and view bill history
                </p>
              </div>
            </div>
          </div>
          {/* Sections */}

          {/* User Management */}
          <Section
            id="user-management"
            title="User Management"
            icon={FiUserCheck}
          >
            <SubSection title="Adding New Users">
              <Step
                number="1"
                title="Navigate to User Management"
                description="Click on the 'User Management' section in the main navigation menu."
              />
              <Step
                number="2"
                title="Add New User"
                description="Click the 'Add New User' button (usually marked with a + icon)."
              />
              <Step
                number="3"
                title="Fill User Details"
                description="Enter user information."
              />
              <Step
                number="4"
                title="Save User"
                description="Click 'Save' to add the user to your database. The system will generate a unique user ID."
              />
            </SubSection>

            <SubSection title="Managing Existing Users">
              <div className="bg-gray-50 p-4 rounded-lg mb-4">
                <h5 className="font-medium mb-2 flex items-center">
                  {/* <Edit className="w-4 h-4 mr-2" /> */}
                  Editing User Information
                </h5>
                <p className="text-sm text-gray-600">
                  Click the edit button next to any user to modify their
                  details. You can update name, username and other information.
                </p>
              </div>

              <div className="bg-gray-50 p-4 rounded-lg">
                <h5 className="font-medium mb-2 flex items-center">
                  {/* <Trash2 className="w-4 h-4 mr-2" /> */}
                  Delete User
                </h5>
                <p className="text-sm text-gray-600">
                  To remove a user, click the delete button next to them. You
                  will be prompted to confirm the deletion. (Can not delete if
                  the user has bills)
                </p>
              </div>
            </SubSection>
          </Section>

          {/* Customer Management */}
          <Section
            id="customer-management"
            title="Customer Management"
            icon={FiUsers}
          >
            <SubSection title="Adding New Customers">
              <Step
                number="1"
                title="Navigate to Customer Management"
                description="Click on the 'Customer Management' section in the main navigation menu."
              />
              <Step
                number="2"
                title="Add New Customer"
                description="Click the 'Add New Customer' button (usually marked with a + icon)."
              />
              <Step
                number="3"
                title="Fill Customer Details"
                description="Enter customer information."
              />
              <Step
                number="4"
                title="Save Customer"
                description="Click 'Save' to add the customer to your database. The system will generate a unique customer ID."
              />
            </SubSection>

            <SubSection title="Managing Existing Customers">
              <div className="bg-gray-50 p-4 rounded-lg mb-4">
                <h5 className="font-medium mb-2 flex items-center">
                  {/* <Edit className="w-4 h-4 mr-2" /> */}
                  Editing Customer Information
                </h5>
                <p className="text-sm text-gray-600">
                  Click the edit button next to any customer to modify their
                  details. You can update name, phone number and other
                  information.
                </p>
              </div>

              <div className="bg-gray-50 p-4 rounded-lg">
                <h5 className="font-medium mb-2 flex items-center">
                  {/* <Trash2 className="w-4 h-4 mr-2" /> */}
                  Delete Customer
                </h5>
                <p className="text-sm text-gray-600">
                  To remove a customer, click the delete button next to them.
                  You will be prompted to confirm the deletion. (Can not delete
                  if the customer has bills)
                </p>
              </div>
            </SubSection>
          </Section>

          {/* Item Management */}
          <Section
            id="item-management"
            title="Item Management"
            icon={FiPackage}
          >
            <SubSection title="Adding New Items">
              <Step
                number="1"
                title="Navigate to Item Management"
                description="Click on the 'Item Management' section in the main navigation menu."
              />
              <Step
                number="2"
                title="Add New Item"
                description="Click the 'Add New Item' button (usually marked with a + icon)."
              />
              <Step
                number="3"
                title="Fill Item Details"
                description="Enter item information."
              />
              <Step
                number="4"
                title="Save Item"
                description="Click 'Save' to add the item to your database. The system will generate a unique item ID."
              />
            </SubSection>

            <SubSection title="Managing Existing Items">
              <div className="bg-gray-50 p-4 rounded-lg mb-4">
                <h5 className="font-medium mb-2 flex items-center">
                  {/* <Edit className="w-4 h-4 mr-2" /> */}
                  Editing Item Information
                </h5>
                <p className="text-sm text-gray-600">
                  Click the edit button next to any item to modify its details.
                  You can update price, quantity and other information.
                </p>
              </div>

              <div className="bg-gray-50 p-4 rounded-lg">
                <h5 className="font-medium mb-2 flex items-center">
                  {/* <Trash2 className="w-4 h-4 mr-2" /> */}
                  Delete Item
                </h5>
                <p className="text-sm text-gray-600">
                  To remove an item, click the delete button next to it. You
                  will be prompted to confirm the deletion. (Can not delete if
                  the item is used in bills)
                </p>
              </div>
            </SubSection>
          </Section>

          {/* Bill Processing */}
          <Section
            id="bill-processing"
            title="Bill Management"
            icon={FiFileText}
          >
            <SubSection title="Creating a New Bill">
              <Step
                number="1"
                title="Navigate to Bill Creation"
                description="Click on the 'Bill Creation' section in the main navigation menu."
              />
              <Step
                number="2"
                title="Select Customer Using Phone Number"
                description="Provide the customer's phone number to retrieve their details."
              />
              <Step
                number="3"
                title="Add Items to Bill"
                description="Select items from the inventory to include in the bill and specify quantities."
              />
              <Step
                number="4"
                title="Review and Confirm Bill"
                description="Check the bill details and click 'Create Bill' to finalize."
              />
            </SubSection>

            <SubSection title="Managing Existing Bills">
              <div className="bg-gray-50 p-4 rounded-lg mb-4">
                <h5 className="font-medium mb-2 flex items-center">
                  {/* <Edit className="w-4 h-4 mr-2" /> */}
                  View Bill History
                </h5>
                <p className="text-sm text-gray-600">
                  Navigate to 'Bill History' in main menu. Click the view button
                  next to any bill to see its details. You can review items,
                  quantities, and other information.
                </p>
              </div>

              <div className="bg-gray-50 p-4 rounded-lg">
                <h5 className="font-medium mb-2 flex items-center">
                  {/* <Trash2 className="w-4 h-4 mr-2" /> */}
                  Print Bill
                </h5>
                <p className="text-sm text-gray-600">
                  Navigate to 'Bill History' in main menu. Click the view button
                  next to any bill to see its details. Select the print option
                  to generate a printable version of the bill.
                </p>
              </div>
            </SubSection>
          </Section>
        </div>
      </div>
    </div>
  );
}

export default Help;
