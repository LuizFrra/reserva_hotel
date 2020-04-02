import React, { Component } from 'react'
import './card.css';
import { Card, Col, Button } from 'antd';
import { EditOutlined } from '@ant-design/icons';

const { Meta } = Card;

export default class FlyCompanyCard extends Component {

    constructor(props) {
        super(props);
        this.handleCompanyClick = this.handleCompanyClick.bind(this);
    }

    handleCompanyClick() {
        this.props.onCompanySel(this.props.companyName);
    }

    render() {
        const imageUrl = this.props.companyName.toLowerCase() + ".png";
        return (
            <Col span={4}>
                <Card className={"cardComponent"} cover={<img className={"imgFly"} src={imageUrl} alt={"Hotel Card"}/>} 
                        actions={[<Button onClick={this.handleCompanyClick} shape={"circle"} type={"dashed"} icon={<EditOutlined />} />]}   >
                    <Meta title={this.props.companyName} description="Venha Voar Conosco !" />
                </Card>
            </Col>
        )
    }
}
 