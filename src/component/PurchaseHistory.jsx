import { useEffect, useState } from "react";
import apiService from "../apiservices/apiUrls";

// ✅ MUI imports
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography,
  Avatar,
  Box,
  Rating,
} from "@mui/material";

export default function PurchaseHistory() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    fetchdata();
  }, []);

  async function fetchdata() {
    try {
      let response = await apiService.get("bids/history");

      // 🎲 Add random rating (1–5, half star allowed)
      const withRatings = response.map((order) => ({
        ...order,
        rating: (Math.random() * 4 + 1).toFixed(1), // 1.0 - 5.0
      }));

      console.log("response with ratings ::", withRatings);
      setOrders(withRatings);
    } catch (error) {
      console.error("Error fetching purchase history:", error);
    }
  }

  return (
    <Box
      sx={{
        minHeight: "100vh",
        background: "linear-gradient(135deg, #eef2ff 0%, #e0f7fa 100%)",
        py: 6,
        px: { xs: 2, md: 6 },
      }}
    >
      {/* Title */}
      <Typography
        variant="h4"
        align="center"
        gutterBottom
        sx={{
          fontWeight: "bold",
          color: "#0f172a",
          mb: 4,
          textShadow: "1px 1px 2px rgba(0,0,0,0.15)",
        }}
      >
        
      </Typography>

      {/* Table */}
      <TableContainer
        component={Paper}
        sx={{
          borderRadius: "20px",
          boxShadow: "0 12px 30px rgba(0,0,0,0.12)",
          overflow: "hidden",
        }}
      >
        <Table>
          <TableHead>
            <TableRow
              sx={{
                background: "linear-gradient(90deg, #2563eb, #4f46e5)",
              }}
            >
              <TableCell sx={{ color: "#fff", fontWeight: "bold", fontSize: "1rem" }}>
                Image
              </TableCell>
              <TableCell sx={{ color: "#fff", fontWeight: "bold", fontSize: "1rem" }}>
                Item Name
              </TableCell>
              <TableCell sx={{ color: "#fff", fontWeight: "bold", fontSize: "1rem" }}>
                Item ID
              </TableCell>
              <TableCell sx={{ color: "#fff", fontWeight: "bold", fontSize: "1rem" }}>
                Bidder
              </TableCell>
              <TableCell sx={{ color: "#fff", fontWeight: "bold", fontSize: "1rem" }}>
                Date
              </TableCell>
              <TableCell sx={{ color: "#fff", fontWeight: "bold", fontSize: "1rem" }}>
                Price
              </TableCell>
              <TableCell sx={{ color: "#fff", fontWeight: "bold", fontSize: "1rem" }}>
                Rating
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {orders.map((order, index) => (
              <TableRow
                key={`${order.itemId}-${index}`}
                sx={{
                  backgroundColor: index % 2 === 0 ? "#f8fafc" : "#fff",
                  transition: "all 0.3s ease",
                  "&:hover": {
                    backgroundColor: "#e0f2fe",
                    transform: "scale(1.01)",
                    boxShadow: "0 4px 20px rgba(0,0,0,0.08)",
                  },
                }}
              >
                <TableCell>
                  <Avatar
                    src={order.imageUrl}
                    alt={order.itemName}
                    variant="rounded"
                    sx={{
                      width: 90,
                      height: 100,
                      borderRadius: "12px",
                      boxShadow: "0 4px 10px rgba(0,0,0,0.1)",
                      objectFit: "cover",
                    }}
                  />
                </TableCell>

                <TableCell sx={{ fontWeight: 600, color: "#1e293b" }}>
                  {order.itemName}
                </TableCell>
                <TableCell sx={{ color: "#475569" }}>{order.itemId}</TableCell>
                <TableCell sx={{ color: "#475569" }}>{order.highestBidder}</TableCell>
                <TableCell sx={{ color: "#475569" }}>
                  {new Date(order.at).toLocaleString()}
                </TableCell>
                <TableCell
                  sx={{
                    fontWeight: "bold",
                    color: "#2563eb",
                    fontSize: "1.1rem",
                  }}
                >
                  ₹{order.highest.toLocaleString()}
                </TableCell>
                <TableCell>
                  <Rating
                    name="read-only"
                    value={parseFloat(order.rating)} // 🎲 random stars
                    precision={0.5}
                    readOnly
                  />
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}
