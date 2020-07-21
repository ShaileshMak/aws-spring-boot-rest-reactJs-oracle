import React, { Component } from "react";
import { Card, CardBody, Container, Table } from "reactstrap";

export default class ProductList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      products: [],
    };
  }
  componentDidMount() {
    fetch("/api/v1/products", {
      method: "GET",
    })
      .then((response) => response.json())
      .then((data) => {
        if (data) {
          this.setState({ products: data });
        }
      })
      .catch((error) => console.log(error));
  }
  render() {
    const { products } = this.state;
    return (
      <Container className="themed-container">
        <Card>
          <CardBody>
            <Table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Company</th>
                  <th>Category</th>
                  <th>Price</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {products.map((product) => (
                  <tr>
                    <td>{product.name}</td>
                    <td>{product.company}</td>
                    <td>{product.category}</td>
                    <td>{product.price}</td>
                    <td>
                      <img
                        alt={product.name}
                        src={`http://localhost:8081/api/v1/product/${product.id}/image/download`}
                        height="100"
                      />
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </CardBody>
        </Card>
      </Container>
    );
  }
}
