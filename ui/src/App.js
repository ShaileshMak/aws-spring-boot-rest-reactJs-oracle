import React from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import AddProduct from "./components/AddProduct";
import ProductList from "./components/ProductList";

export default function App() {
  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/">Add Product</Link>
            </li>
            <li>
              <Link to="/productList">Products</Link>
            </li>
          </ul>
        </nav>

        {/* A <Switch> looks through its children <Route>s and
            renders the first one that matches the current URL. */}
        <Switch>
          <Route path="/productList">
            <ProductList />
          </Route>
          <Route path="/">
            <AddProduct />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}
