// import React, { useEffect, useState } from "react";
// import { Link } from "react-router-dom";
// import "./landing.css";
// import {
//   Button,
//   Card,
//   CardContent,
//   CardMedia,
//   Typography,
//   Chip,
//   Box,
//   Grid,
// } from "@mui/material";
// import apiService from "../apiservices/apiUrls";

// export default function Home() {
//   const [alldata, setalldata] = useState([]);

//   const featured = [
//     {
//       id: 3,
//       name: "Louis Vuitton Neverfull MM",
//       price: 800,
//       image:
//         "https://in.louisvuitton.com/images/is/image/lv/1/PP_VP_L/louis-vuitton-discovery-backpack-pm--M11590_PM2_Front%20view.png?wid=4096&hei=4096",
//     },
//     {
//       id: 4,
//       name: "Pokémon Charizard 1st Edition Holo",
//       price: 1200,
//       image:
//         "https://5.imimg.com/data5/ANDROID/Default/2024/8/442119843/ML/YH/OC/217662125/prod-20240810-2039426024860851093540343-jpg.jpg",
//     },
//     {
//       id: 5,
//       name: "American Gold Eagle 1 oz Coin",
//       price: 450,
//       image:
//         "https://www.kundanrefinery.com/pub/media/catalog/product/cache/a6819a77997e6f5ec84977f1af72369f/k/r/kr337-a_1.jpg",
//     },
//   ];

//   async function getdata() {
//     try {
//       let response = await apiService.get("items/live");
//       setalldata(response?.content);
//     } catch (e) {
//       console.log("unable to fetch data ", e);
//     }
//   }

//   useEffect(() => {
//     getdata();
//   }, []);

//   return (
//     <div>
//       {/* Hero Section */}
//       <section className="hero">
//         <h1>Welcome to AuctionX</h1>
//         <p>
//           Bid. Win. Own. Discover rare collectibles and unique treasures in
//           real-time auctions.
//         </p>
//         <Box mt={3}>
//           <Button
//             variant="contained"
//             color="primary"
//             component={Link}
//             to="/live-items"
//             sx={{ mr: 2 }}
//           >
//             Browse Auctions
//           </Button>
//           <Button
//             variant="contained"
//             color="warning"
//             component={Link}
//             to="/admin"
//           >
//             Add an Item
//           </Button>
//         </Box>
//       </section>

//       {/* Featured Items */}
//       <section className="featured-section">
//         <Typography variant="h5" align="center" fontWeight="bold" gutterBottom>
//           🔥 Featured Auctions
//         </Typography>
//         <Grid container spacing={3} justifyContent="center">
//           {featured.map((item) => (
//             <Grid item key={item.id}>
//               <Card className="auction-card">
//                 <CardMedia
//                   component="img"
//                   image={item?.image}
//                   alt={item.name}
//                   className="auction-img"
//                 />

//                 <CardContent className="auction-content">
//                   <div>
//                     <Typography variant="h6" noWrap>
//                       {item.name}
//                     </Typography>
//                     <Typography variant="body2" color="text.secondary">
//                       Starting at ${item.price}
//                     </Typography>
//                   </div>

//                   <Box mt={2}>
//                     <Button
//                       variant="contained"
//                       color="primary"
//                       size="small"
//                       component={Link}
//                       to={`/item/${item.id}`}
//                     >
//                       Place a Bid
//                     </Button>
//                   </Box>
//                 </CardContent>
//               </Card>
//             </Grid>
//           ))}
//         </Grid>
//       </section>

//       {/* Categories */}
//       <section className="categories">
//         <Typography
//           variant="h5"
//           fontWeight="bold"
//           gutterBottom
//           align="center"
//           sx={{
//             fontSize: "2rem",
//             fontWeight: 800,
//             background: "linear-gradient(90deg, #ff6a00, #ee0979)",
//             WebkitBackgroundClip: "text",
//             WebkitTextFillColor: "transparent",
//             position: "relative",
//             display: "inline-block",
//             mb: 3,
//           }}
//         >
//           Browse by Category
//         </Typography>

//         <Box display="flex" flexWrap="wrap" justifyContent="center" gap={2}>
//           {["Collectibles", "Furniture", "Electronics", "Art", "Jewelry"].map(
//             (cat) => (
//               <Chip
//                 key={cat}
//                 label={cat}
//                 clickable
//                 variant="outlined"
//                 color="primary"
//                 sx={{ fontSize: "1rem", padding: "6px 12px" }}
//               />
//             )
//           )}
//         </Box>
//       </section>

//       {/* CTA Section */}
//       <section className="cta">
//         <Typography variant="h4" fontWeight="bold" gutterBottom>
//           Ready to start your auction journey?
//         </Typography>
//         <Typography variant="body1" sx={{ mb: 3 }}>
//           Sign up today and join thousands of bidders and sellers.
//         </Typography>
//         <Button
//           variant="contained"
//           color="warning"
//           component={Link}
//           to="/register"
//         >
//           Get Started
//         </Button>
//       </section>
//     </div>
//   );
// }


import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./landing.css";
import {
  Button,
  Card,
  CardContent,
  CardMedia,
  Typography,
  Chip,
  Box,
  Grid,
} from "@mui/material";
import apiService from "../apiservices/apiUrls";

export default function Home() {
  const [alldata, setalldata] = useState([]);
  const [categoryItems, setCategoryItems] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);

  const featured = [
    {
      id: 3,
      name: "Louis Vuitton Neverfull MM",
      price: 800,
      image:
        "https://in.louisvuitton.com/images/is/image/lv/1/PP_VP_L/louis-vuitton-discovery-backpack-pm--M11590_PM2_Front%20view.png?wid=4096&hei=4096",
    },
    {
      id: 4,
      name: "Pokémon Charizard 1st Edition Holo",
      price: 1200,
      image:
        "https://5.imimg.com/data5/ANDROID/Default/2024/8/442119843/ML/YH/OC/217662125/prod-20240810-2039426024860851093540343-jpg.jpg",
    },
    {
      id: 5,
      name: "American Gold Eagle 1 oz Coin",
      price: 450,
      image:
        "https://www.kundanrefinery.com/pub/media/catalog/product/cache/a6819a77997e6f5ec84977f1af72369f/k/r/kr337-a_1.jpg",
    },
  ];

  async function getdata() {
    try {
      let response = await apiService.get("items/live");
      setalldata(response?.content);
    } catch (e) {
      console.log("unable to fetch data ", e);
    }
  }

  async function fetchCategoryItems(category) {
    try {
      setSelectedCategory(category);
      let response = await apiService.get(`items/by-type?type=${category}`);
      setCategoryItems(response || []);
    } catch (e) {
      console.log("Unable to fetch category items ", e);
    }
  }

  useEffect(() => {
    getdata();
  }, []);

  return (
    <div>
      {/* Hero Section */}
      <section className="hero">
        <h1>Welcome to AuctionX</h1>
        <p>
          Bid. Win. Own. Discover rare collectibles and unique treasures in
          real-time auctions.
        </p>
        <Box mt={3}>
          <Button
            variant="contained"
            color="primary"
            component={Link}
            to="/live-items"
            sx={{ mr: 2 }}
          >
            Browse Auctions
          </Button>
          <Button
            variant="contained"
            color="warning"
            component={Link}
            to="/admin"
          >
            Add an Item
          </Button>
        </Box>
      </section>

      {/* Featured Items */}
      <section className="featured-section">
        <Typography variant="h5" align="center" fontWeight="bold" gutterBottom>
          🔥 Featured Auctions
        </Typography>
        <Grid container spacing={3} justifyContent="center">
          {featured.map((item) => (
            <Grid item key={item.id}>
              <Card className="auction-card">
                <CardMedia
                  component="img"
                  image={item?.image}
                  alt={item.name}
                  className="auction-img"
                />

                <CardContent className="auction-content">
                  <div>
                    <Typography variant="h6" noWrap>
                      {item.name}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      Starting at ${item.price}
                    </Typography>
                  </div>

                  <Box mt={2}>
                    <Button
                      variant="contained"
                      color="primary"
                      size="small"
                      component={Link}
                      to={`/item/${item.id}`}
                    >
                      Place a Bid
                    </Button>
                  </Box>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      </section>

      {/* Categories */}
      <section className="categories">
        <Typography
          variant="h5"
          fontWeight="bold"
          gutterBottom
          align="center"
          sx={{
            fontSize: "2rem",
            fontWeight: 800,
            background: "linear-gradient(90deg, #ff6a00, #ee0979)",
            WebkitBackgroundClip: "text",
            WebkitTextFillColor: "transparent",
            position: "relative",
            display: "inline-block",
            mb: 3,
          }}
        >
          Browse by Category
        </Typography>

        <Box display="flex" flexWrap="wrap" justifyContent="center" gap={2}>
          {["Collectibles", "Furniture", "Electronics", "Art", "Jewelry"].map(
            (cat) => (
              <Chip
                key={cat}
                label={cat}
                clickable
                variant={selectedCategory === cat ? "filled" : "outlined"}
                color="primary"
                sx={{ fontSize: "1rem", padding: "6px 12px" }}
                onClick={() => fetchCategoryItems(cat)}
              />
            )
          )}
        </Box>
      </section>

      {/* Show Category Results */}
      {selectedCategory && (
        <section className="category-results">
          <Typography
            variant="h6"
            align="center"
            fontWeight="bold"
            gutterBottom
            sx={{ mt: 4 }}
          >
            Showing results for: {selectedCategory}
          </Typography>

          {categoryItems.length > 0 ? (
            <Grid container spacing={3} justifyContent="center">
              {categoryItems.map((item) => (
                <Grid item key={item.id}>
                  <Card className="auction-card">
                    <CardMedia
                      component="img"
                      image={item?.imageUrl}
                      alt={item.name}
                      className="auction-img"
                    />
                    <CardContent className="auction-content">
                      <Typography variant="h6" noWrap>
                        {item.name}
                      </Typography>
                      <Typography variant="body2" color="text.secondary">
                        Starting at ${item.startingPrice}
                      </Typography>
                      <Box mt={2}>
                        <Button
                          variant="contained"
                          color="primary"
                          size="small"
                          component={Link}
                          to={`/item/${item.id}`}
                        >
                          Place a Bid
                        </Button>
                      </Box>
                    </CardContent>
                  </Card>
                </Grid>
              ))}
            </Grid>
          ) : (
            <Typography align="center" sx={{ mt: 2 }}>
              No items found in this category.
            </Typography>
          )}
        </section>
      )}

      {/* CTA Section */}
      <section className="cta">
        <Typography variant="h4" fontWeight="bold" gutterBottom>
          Ready to start your auction journey?
        </Typography>
        <Typography variant="body1" sx={{ mb: 3 }}>
          Sign up today and join thousands of bidders and sellers.
        </Typography>
        <Button
          variant="contained"
          color="warning"
          component={Link}
          to="/register"
        >
          Get Started
        </Button>
      </section>
    </div>
  );
}


