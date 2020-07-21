import React, { Component } from "react";
import Product from "./Product";
import {
  Button,
  Form,
  FormGroup,
  Label,
  Input,
  Card,
  CardBody,
  CardTitle,
  Container,
} from "reactstrap";

export default class AddProduct extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: "",
      name: "",
      description: "",
      company: "",
      category: "",
      price: "",
      imageUrl: "",
      productImageFile: "",
      showForm: true,
    };
    this.handleOnSubmit = this.handleOnSubmit.bind(this);
    this.handleProductImageChange = this.handleProductImageChange.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleAddProduct = this.handleAddProduct.bind(this);
  }
  handleAddProduct() {
    this.setState({ showForm: true });
  }

  handleOnSubmit(event) {
    event.preventDefault();
    this.addProduct();
  }

  handleProductImageChange(event) {
    const file = event.target.files[0];
    if (file) {
      this.setState({
        imageUrl: file.name,
        productImageFile: file,
      });
    }
  }

  handleInputChange(event) {
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  addProduct() {
    var formData = new FormData();
    formData.append("name", this.state.name);
    formData.append("description", this.state.description);
    formData.append("company", this.state.company);
    formData.append("category", this.state.category);
    formData.append("price", this.state.price);
    formData.append("imageUrl", this.state.imageUrl);
    formData.append("file", this.state.productImageFile);
    fetch("/api/v1/product", {
      method: "POST",
      body: formData,
    })
      .then((response) => response.json())
      .then((success) => {
        if (success) {
          this.setState({ id: success.id, showForm: false });
        }
      })
      .catch((error) => console.log(error));
  }

  render() {
    const {
      id,
      name,
      description,
      company,
      category,
      price,
      showForm,
    } = this.state;
    const productProps = {
      id,
      name,
      description,
      company,
      category,
      price,
    };
    return showForm ? (
      <Container className="themed-container">
        <Card>
          <CardBody>
            <CardTitle>Add Product</CardTitle>
            <Form onSubmit={this.handleOnSubmit}>
              <FormGroup>
                <Label for="name">Name</Label>
                <Input
                  type="name"
                  name="name"
                  id="name"
                  value={name}
                  onChange={this.handleInputChange}
                  placeholder="Product Name"
                />
              </FormGroup>
              <FormGroup>
                <Label for="description">Description</Label>
                <Input
                  type="textarea"
                  name="description"
                  id="description"
                  onChange={this.handleInputChange}
                  value={description}
                />
              </FormGroup>
              <FormGroup>
                <Label for="company">Company</Label>
                <Input
                  type="company"
                  name="company"
                  id="company"
                  onChange={this.handleInputChange}
                  placeholder="Product Company"
                  value={company}
                />
              </FormGroup>
              <FormGroup>
                <Label for="category">Category</Label>
                <Input
                  type="category"
                  name="category"
                  id="category"
                  onChange={this.handleInputChange}
                  placeholder="Product Category"
                  value={category}
                />
              </FormGroup>
              <FormGroup>
                <Label for="price">Price</Label>
                <Input
                  type="price"
                  name="price"
                  id="price"
                  onChange={this.handleInputChange}
                  placeholder="Product Price"
                  value={price}
                />
              </FormGroup>
              <FormGroup>
                <Label for="exampleFile">Product Image</Label>
                <Input
                  type="file"
                  name="productImage"
                  id="productImage"
                  onChange={this.handleProductImageChange}
                />
              </FormGroup>
              <Button>Submit</Button>
            </Form>
          </CardBody>
        </Card>
      </Container>
    ) : (
      <Product {...productProps} handleAddProduct={this.handleAddProduct} />
    );
  }
}
