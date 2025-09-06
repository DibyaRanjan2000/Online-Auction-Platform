import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import apiService from "../apiservices/apiUrls";
import { Pencil, Trash2 } from "lucide-react";
import "./AdminDashboard.css"; // external css

const AdminDashboard = () => {
  const [items, setItems] = useState([]);
  const { register, handleSubmit, reset } = useForm();

  // ✅ Fetch items
  useEffect(() => {
    const fetchItems = async () => {
      try {
        const response = await apiService.get("/items");
        setItems(response.data || response);
      } catch (error) {
        console.error("❌ Error fetching items:", error);
      }
    };
    fetchItems();
  }, []);

  // ✅ Add item
  //   const onSubmit = async (data) => {
  //     try {

  //      debugger;
  //       // Convert datetime-local → ISO Instant (backend expects full format)
  //       const formattedAuctionEnd = new Date(data.auctionEnd).toISOString();
  //      console.log("Insert data    :::" , JSON.stringify(data));
  //       const res = await apiService.post("/items", {
  //         ...data,
  //         startingPrice: Number(data.startingPrice),
  //         auctionEnd: formattedAuctionEnd,
  //       });

  //       setItems([...items, res.data || res]);
  //       reset();
  //     } catch (error) {
  //       console.error("❌ Error adding item:", error);
  //     }
  //   };
  // ✅ Add item
  const onSubmit = async (data) => {
    debugger;
    try {
      // Prepare the object before API call
      const preparedData = {
        ...data,
        startingPrice: Number(data.startingPrice),
        auctionEnd: new Date(data.auctionEnd)
          .toISOString()
          .replace(/\.\d{3}Z$/, "Z"), // 👈 clean
      };

      const res = await apiService.post("/items", preparedData);

      setItems([...items, res.data || res]);
      reset();
    } catch (error) {
      console.error("❌ Error adding item:", error);
    }
  };

  // ✅ Delete item
  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this item?")) return;

    try {
      await apiService.delete(`/items/${id}`);
      setItems(items.filter((item) => item.id !== id));
    } catch (error) {
      console.error("❌ Error deleting item:", error);
    }
  };

  return (
    <div className="dashboard">
      <h1 className="dashboard-title">📊 Admin Dashboard</h1>

      {/* Form */}
      <div className="form-card">
        <h2 className="form-title">Add New Auction Item</h2>
        <form onSubmit={handleSubmit(onSubmit)} className="form-grid">
          <input
            placeholder="Item Name"
            {...register("name", { required: true })}
          />
          <input
            type="number"
            step="0.01"
            placeholder="Starting Price"
            {...register("startingPrice", { required: true })}
          />
          <input
            placeholder="Image URL"
            {...register("imageUrl", { required: true })}
          />
          <textarea placeholder="Description" {...register("description")} />
          <input
            type="datetime-local"
            {...register("auctionEnd", { required: true })}
          />

          <button type="submit" className="submit-btn">
            ➕ Add Item
          </button>
        </form>
      </div>

      {/* Items */}
      <h2 className="section-title">📦 Auction Items</h2>
      <div className="item-grid">
        {items.map((item) => (
          <div className="item-card" key={item.id}>
            <img
              src={item.imageUrl}
              alt={item.name}
              className="item-img"
              onError={(e) =>
                (e.target.src = "https://via.placeholder.com/200?text=No+Image")
              }
            />
            <h3>{item.name}</h3>
            <p className="item-desc">{item.description}</p>
            <p>
              <strong>Price:</strong> ₹{item.startingPrice}
            </p>
            <p>
              <strong>Ends:</strong>{" "}
              {new Date(item.auctionEnd).toLocaleString()}
            </p>
            <div className="item-actions">
              <button className="edit-btn">
                <Pencil size={16} /> Edit
              </button>
              <button
                className="delete-btn"
                onClick={() => handleDelete(item.id)}
              >
                <Trash2 size={16} /> Delete
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default AdminDashboard;
