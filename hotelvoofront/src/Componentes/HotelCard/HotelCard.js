import React, { Component } from 'react'
import './card.css';
import { Card, Col, Button } from 'antd';
import { EditOutlined } from '@ant-design/icons';

const { Meta } = Card;

export default class HotelCard extends Component {

    constructor(props) {
        super(props);
        this.handleCardClick = this.handleCardClick.bind(this);
    }

    handleCardClick() {
        this.props.handleHotelCardClick(this.props.hotel);
    }

    render() {
        const imageUrl = "https://picsum.photos/200/300?" + Math.random();
        return (
            <Col span={4}>
                <Card className={"cardComponent"} cover={<img src={imageUrl} alt={"Hotel Card"}/>} 
                        actions={[<Button onClick={this.handleCardClick} shape={"circle"} type={"dashed"} icon={<EditOutlined />} />]}   >
                    <Meta title={this.props.hotel.name} description="A Great Hotel !" />
                </Card>
            </Col>
        )
    }
}
 