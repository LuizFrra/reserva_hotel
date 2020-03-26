import React, { Component } from 'react'
import { Card, Button } from 'antd'
import Axios from 'axios'
import { BookOutlined } from '@ant-design/icons';


const gridStyle = {
    width: '100%',
    textAlign: 'center',
};

const gridCenter = {
    textAlign: 'center'
}

export default class HotelRoom extends Component {
    
    constructor(props){
        super(props);
        this.state =  { rooms: [], loading: true };
        this.handleHotelRoomClick = this.handleHotelRoomClick.bind(this);
    }

    handleHotelRoomClick(e) {
        this.props.onClick(e.target.dataset.index);
    }

    componentDidMount() {
        const getHotelRoomsUrl = "http://localhost:8080/hotel/rooms/" + this.props.hotelId;
        Axios.get(getHotelRoomsUrl).then(res => {
            
            const rooms = res.data;
            const roomsGrid = [];
            for(let key in Object.entries(rooms)){
                roomsGrid.push(
                    <Card.Grid style={gridCenter} 
                        key={key}>{"QUARTO " + key}
                        <Button onClick={this.handleHotelRoomClick} type="dashed" shape="circle" data-index={rooms[key].id}
                            icon={<BookOutlined/>} /> 
                    </Card.Grid>
                );
            }
            
            if(roomsGrid.length === 0){
                roomsGrid.push(
                    <Card.Grid key={-1} style={gridStyle}>Nenhum Quarto Disponível.</Card.Grid>
                );
            }

            this.setState({rooms: roomsGrid, loading: false});
        });
    }

    render() {
        return (
            <Card title={"Quartos"} loading={this.state.loading}>
                {this.state.rooms}
            </Card>
        )
    }
}
