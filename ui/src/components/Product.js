import React from "react";
import { Button, Card, CardBody, Container, Col, Row } from "reactstrap";

export default function Product(props) {
  const {
    id,
    name,
    description,
    company,
    category,
    price,
    handleAddProduct,
  } = props;
  return (
    <Container className="themed-container">
      <Card>
        <CardBody>
          <Row>
            <Col>
              <div>
                <h1>{name}</h1>
              </div>
              <div>
                <b>Description :</b>
                {description}
              </div>
              <div>
                <b>company :</b>
                {company}
              </div>
              <div>
                <b>category :</b>
                {category}
              </div>
              <div>
                <b>price :</b>
                {price}
              </div>
            </Col>
            <Col>
              <img
                alt={name}
                src={`http://localhost:8081/api/v1/product/${id}/image/download`}
                width="300"
              />
            </Col>
          </Row>
          <Row>
            <Col>
              <Button onClick={handleAddProduct}>Add Product</Button>
            </Col>
          </Row>
        </CardBody>
      </Card>
    </Container>
  );
}
