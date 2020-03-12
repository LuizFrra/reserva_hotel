import React from 'react';
import 'antd/dist/antd.css';
import { Row, Col } from 'antd';
import './NavBar.css'

class NavBar extends React.Component {
    render() {
        return (
            <Row className={"navbar"}>
                <Col xs={7} sm={10} md={8}></Col>
                <Col xs={10} sm={4} md={8}>
                    SynchroBook
                </Col>
                <Col xs={7} sm={10} md={8}></Col>
            </Row>
        );
    }
}

export default NavBar;